package ok.movie;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.Json;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;

import static java.util.Objects.isNull;

public class MovieAppServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieAppServer.class.getName());

    public void run(Vertx vertx) {
        LOGGER.info(Thread.currentThread().getName() + " configuring router..");
        Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create());
        router.get().handler(StaticHandler.create());

        router
            .get("/movies")
            .handler(context -> getMovies(context, vertx));

        router
            .post("/movies")
            .handler(context -> postMovie(context, vertx));

        LOGGER.info(Thread.currentThread().getName() + " creating HTTP server..");
        HttpServer httpServer = vertx.createHttpServer();
        httpServer
            .requestHandler(router)
            .listen(8080, http -> {
                if (http.succeeded()) {
                    LOGGER.info(Thread.currentThread().getName() + " listening on port 8080..");
                } else {
                    LOGGER.error(Thread.currentThread().getName() + " failed to start server..");
                }
            });
    }

    private void postMovie(RoutingContext context, Vertx vertx) {
        Movie movie = Json.decodeValue(context.getBodyAsString(), Movie.class);

        if (isNull(movie.getName()) || movie.getName().isEmpty() || isNull(movie.getGenre()) || movie.getGenre().isEmpty()) {
            context
                .response()
                .setStatusCode(400)
                .putHeader("Content-Type", "application/json")
                .end("{\"error\": \"Name, Genre are required\"}");
        }

        vertx
            .eventBus()
            .request("service.movie-add", Json.encode(movie), res -> {
                LOGGER.info(Thread.currentThread().getName() + " handling response in event bus..");

                if (res.succeeded()) {
                context
                    .response()
                    .putHeader("Content-Type", "application/json")
                    .end(res.result().body().toString());
            } else {
                context.fail(res.cause());
            }
        });
        LOGGER.info(Thread.currentThread().getName() + " Post event bus..");
    }

    private void getMovies(RoutingContext context, Vertx vertx) {
        vertx
            .eventBus()
            .request("service.movies-get", "", res -> {
                if (res.succeeded()) {
                    context
                        .response()
                        .putHeader("Content-Type", "application/json")
                        .end(res.result().body().toString());
                } else {
                    context.fail(res.cause());
                }
            });
    }
}
