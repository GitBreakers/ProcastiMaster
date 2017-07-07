package nl.xillio.gitbreakers.procrastimaster.client.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Jorn on 07/07/2017.
 */
@Singleton
public class RequestHandlerFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestHandlerFactory.class);
    private static final String BASE_URL = "http://127.0.0.1:8080";

    public RequestHandler request(String method) {
        try {
            return new RequestHandler(new URL(BASE_URL + "/" + method));
        } catch (MalformedURLException e) {
            LOGGER.error("Failed to create request: " + e.getMessage());
        }
        return null;
    }

}
