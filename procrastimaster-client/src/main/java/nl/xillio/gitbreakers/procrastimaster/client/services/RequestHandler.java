package nl.xillio.gitbreakers.procrastimaster.client.services;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

/**
 * Created by Jorn on 07/07/2017.
 */
public class RequestHandler {
    private static final Logger LOGGER = LogFactory.getLog();

    private HttpURLConnection connection;

    public RequestHandler(URL request) {
        try {
            this.connection = (HttpURLConnection) request.openConnection();

            String basicAuth = "Basic " + Base64.encode("pieter@GitBreakers.nl:root".getBytes());
            this.connection.setRequestProperty("Authorization", basicAuth);
        } catch (IOException e) {
            LOGGER.error("Failed to get request: " + e.getMessage());
        }

    }

    public <T> Optional<T> get() {
        try {
            connection.setRequestMethod("GET");
            handleResponse();

        } catch (IOException e) {
            LOGGER.error("Failed to make GET request: " + e.getMessage());
        }
        return null;
    }

    public <T> T post() {
        return null;
    }

    public <T> T put() {
        return null;
    }

    public RequestHandler body() {
        return this;
    }

    public RequestHandler parameters() {
        return this;
    }

    private void handleResponse() {
        if (connection == null) return;

        try {
            int statusCode = connection.getResponseCode();
            String result = connection.getResponseMessage();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } catch (IOException e) {
            LOGGER.error("Failed to handle response: " + e.getMessage());
        }
    }

}
