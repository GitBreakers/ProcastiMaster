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
import javafx.scene.Node;
import nl.xillio.gitbreakers.procrastimaster.client.LoadedView;
import nl.xillio.gitbreakers.procrastimaster.client.controllers.AbstractController;
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

    public LoadedView getView(View view) {
        LOGGER.info("Loading View: {}", view.name());
        FXMLLoader fxmlLoader = fxmlLoaderFactory.build();
        fxmlLoader.setLocation(view.getResource());
        fxmlLoader.setController(view.getController());

        // Load the FXML.
        Node node;
        try {
            node = fxmlLoader.load();
        } catch (IOException e) {
            throw new IllegalStateException("Loading the new view has failed: " + e.getMessage(), e);
        }

        Object controller = fxmlLoader.getController();
        if (!(controller instanceof AbstractController)) {
            controller = null;
        }

        return new LoadedView(node, (AbstractController)controller);
    }

    public enum View {
        OVERVIEW,
        HISTORY("userinfo", HistoryController.class),
        TODAY("userinfo", TodayController.class),
        FUTURE("userinfo", FutureController.class),
        STARTLOG,
        UPDATES,
        PERSONALSPACE;

        private final String view;
        private final Class<? extends AbstractController> controller;
        private final URL resource;

        View() {
            view = name().toLowerCase();
            this.controller = null;
            resource = getClass().getResource("/views/" + view + ".fxml");
        }

        View(String view, Class<? extends AbstractController> controller) {
            this.view = view;
            this.controller = controller;
            resource = getClass().getResource("/views/" + view + ".fxml");
        }

        public URL getResource() {
            if (resource == null) {
                throw new IllegalStateException("No view '" + view + "' was found");
            }
            return resource;
        }

        public AbstractController getController() {
            try {
                return controller == null ? null : controller.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new IllegalStateException("Could not instantiate: " + controller.getSimpleName());
            }
        }
    }
}
