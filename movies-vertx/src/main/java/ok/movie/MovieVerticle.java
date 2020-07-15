package ok.movie;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class MovieVerticle extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieVerticle.class.getName());

    @Override
    public void start() {
        new MovieAppServer().run(vertx);
        deployVerticle(MovieRepository.class.getName());
    }

    private void deployVerticle(String className) {
        vertx.deployVerticle(className, res -> {
            if (res.succeeded()) {
                LOGGER.info("Successfully deployed: " + className);
            } else {
                LOGGER.error("Error in deploying: " + className);
            }
        });
    }
}
