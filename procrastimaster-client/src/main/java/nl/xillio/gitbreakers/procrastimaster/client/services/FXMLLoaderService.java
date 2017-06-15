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
import nl.xillio.gitbreakers.procrastimaster.client.controllers.FutureController;
import nl.xillio.gitbreakers.procrastimaster.client.controllers.HistoryController;
import nl.xillio.gitbreakers.procrastimaster.client.controllers.TodayController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;

/**
 * This service loads .fxml files from the classpath in the 'views' package.
 */
@Singleton
public class FXMLLoaderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FXMLLoaderService.class);
    private final FXMLLoaderFactory fxmlLoaderFactory;

    @Inject
    public FXMLLoaderService(FXMLLoaderFactory fxmlLoaderFactory) {
        this.fxmlLoaderFactory = fxmlLoaderFactory;
    }

    public <T extends Pane> T getView(View view) {
        LOGGER.info("Loading View: {}", view.name());
        FXMLLoader fxmlLoader = fxmlLoaderFactory.build();
        fxmlLoader.setLocation(view.getResource());
        fxmlLoader.setController(view.getController());

        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            throw new IllegalStateException("Loading the new view has failed: " + e.getMessage(), e);
        }
    }

    public enum View {
        OVERVIEW,
        HISTORY("userinfo", HistoryController.class),
        TODAY("userinfo", TodayController.class),
        FUTURE("userinfo", FutureController.class),
        LOG,
        UPDATES,
        PERSONALSPACE;

        private final String view;
        private final Class<?> clazz;
        private final URL resource;

        View() {
            view = name().toLowerCase();
            this.clazz = null;
            resource = getClass().getResource("/views/" + view + ".fxml");
        }

        View(String view, Class<?> clazz) {
            this.view = view;
            this.clazz = clazz;
            resource = getClass().getResource("/views/" + view + ".fxml");
        }

        public URL getResource() {
            if (resource == null) {
                throw new IllegalStateException("No view '" + view + "' was found");
            }
            return resource;
        }

        public Object getController() {
            try {
                return clazz == null ? null : clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new IllegalStateException("Could not instantiate: " + clazz.getSimpleName());
            }
        }
    }
}
