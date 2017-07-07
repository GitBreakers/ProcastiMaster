/**
 * Copyright (C) 2017 Xillio GitBreakers (GitBreakers@xillio.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.xillio.gitbreakers.procrastimaster.client.services;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

/**
 * Created by Jorn on 07/07/2017.
 */
public class RequestHandler {
    private static final Logger LOGGER = LogFactory.getLog();

    private final ObjectMapperService mapperService;
    private final HttpURLConnection connection;

    public RequestHandler(URL request, ObjectMapperService mapperService) throws IOException {
        this.mapperService = mapperService;
        this.connection = (HttpURLConnection)request.openConnection();

        this.parameter("Content-Type", "application/json");
        this.parameter("Authorization", "Basic " + new String(Base64.encodeBase64("pieter@GitBreakers.nl:root".getBytes())));
    }

    public <T> Optional<T> get(Class<T> clazz) {
        return handleResponse("GET", clazz);
    }

    public <T> Optional<T> post(Class<T> clazz) {
        return handleResponse("POST", clazz);
    }

    public <T> Optional<T> put(Class<T> clazz) {
        return handleResponse("PUT", clazz);
    }

    public RequestHandler body() {
        return this;
    }

    public RequestHandler parameter(String key, String value) {
        connection.setRequestProperty(key, value);
        return this;
    }

    private <T> Optional<T> handleResponse(String method, Class<T> clazz) {
        try {
            connection.setRequestMethod(method);
            return Optional.of(mapperService.getMapper().readValue(connection.getInputStream(), clazz));
        } catch (IOException e) {
            LOGGER.error("Failed to handle response: " + e.getMessage());
        }
        return Optional.empty();
    }
}
