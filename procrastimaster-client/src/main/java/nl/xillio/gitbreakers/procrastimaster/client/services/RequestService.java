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

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by Jorn on 10/07/2017.
 */
public class RequestService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestService.class);
    private Request request;
    private final ObjectMapperService mapperService;

    private final String BASE_URL = "http://127.0.0.1:8080";

    public RequestService(ObjectMapperService mapperServer) {
        this.mapperService = mapperServer;
    }

    public Optional<Boolean> execute() {
        try {
            Response response = request.execute();
            if (response.returnResponse().getStatusLine().getStatusCode() == 200)
                return Optional.ofNullable(true);
            else
                return Optional.ofNullable(false);
        } catch (IOException e) {
            LOGGER.error("Failed to execute request: " + e.getMessage());
        }
        return Optional.empty();
    }

    public <T> Optional<T> execute(Class<T> clazz) {
        try {
            Response response = request.execute();

            T result = response.handleResponse(mapperService.getResponseHandler(clazz));
            return Optional.of(result);
        } catch (IOException e) {
            LOGGER.error("Failed to execute request: " + e.getMessage());
        }

        return Optional.empty();
    }

    public RequestService auth() {
        request.addHeader("Authorization", "Basic " + Base64.encodeBase64String("sander@GitBreakers.nl:doed".getBytes()));
        return this;
    }

    public RequestService get(String method) {
        request = Request.Get(BASE_URL + "/" + method);
        return this;
    }

    public RequestService post(String method) {
        request = Request.Post(BASE_URL + "/" + method);
        return this;
    }

    public RequestService body(String body) {
        request.bodyString(body, ContentType.APPLICATION_JSON);
        return this;
    }

    public RequestService body(Object body) {
        try {
            request.bodyString(mapperService.getMapper().writeValueAsString(body), ContentType.APPLICATION_JSON);
        } catch (JsonProcessingException e) {
            LOGGER.error("Failed to map request object to JSON: " + e.getMessage());
        }
        return this;
    }

    public RequestService parameter(String key, String value) {
        request.addHeader(key, value);
        return this;
    }

    /* Simple example of how to use the RequestService
    Update u = new Update();
    u.setTodayIHave("Done Nothing, even less than before");
    u.setTodayIHaveNot("Procrestinated");
    u.setNextDay(new Date());

    requestService.get("overview").auth().execute(Overview.class).ifPresent(overview -> {
        LOGGER.info("Get complete");
    });

    requestService.post("activity/update").auth().body(u).execute().ifPresent(success -> {
        if(success) LOGGER.info("Post complete");
    });
    */
}
