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

import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;

@Singleton
public class FXMLLoaderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FXMLLoaderService.class);
    private final FXMLLoaderFactory fxmlLoaderFactory;

    @Inject
    public FXMLLoaderService(FXMLLoaderFactory fxmlLoaderFactory) {
        this.fxmlLoaderFactory = fxmlLoaderFactory;
    }

    public <T extends Pane> T getView(View view) {
        LOGGER.info("Loading View: {}", view.getViewName());
        FXMLLoader fxmlLoader = fxmlLoaderFactory.build();
        fxmlLoader.setLocation(getViewFile(view.getViewName()));
        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            throw new IllegalStateException("Loading the new view has failed: " + e.getMessage(), e);
        }
    }

    private URL getViewFile(String name) {
        String viewUrl = "/views/" + name + ".fxml";
        URL result = getClass().getResource(viewUrl);

        if (result == null) {
            throw new IllegalArgumentException("No view '" + name + "' was found");
        }

        return result;
    }

    public enum View {
        OVERVIEW;

        public String getViewName() {
            return name().toLowerCase();
        }
    }
}
