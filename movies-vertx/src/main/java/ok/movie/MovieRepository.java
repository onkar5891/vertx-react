package ok.movie;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.Json;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MovieRepository extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieRepository.class.getName());
    private static final List<Movie> MOVIES = new ArrayList<>();

    @Override
    public void start() {
        vertx.eventBus().consumer("service.movies-get", msg -> msg.reply(Json.encode(MOVIES)));

        vertx.eventBus().consumer("service.movie-add", msg -> {
            Movie movie = Json.decodeValue(msg.body().toString(), Movie.class);
            movie.setId(UUID.randomUUID().toString());
            MOVIES.add(movie);
            msg.reply(Json.encode(movie));

            LOGGER.info(Thread.currentThread().getName() + " stored movie: " + movie);
        });
    }
}
