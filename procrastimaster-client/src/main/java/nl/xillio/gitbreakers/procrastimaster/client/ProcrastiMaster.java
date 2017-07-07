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
package nl.xillio.gitbreakers.procrastimaster.client;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nl.xillio.gitbreakers.procrastimaster.client.services.FXMLLoaderService;
import nl.xillio.gitbreakers.procrastimaster.client.services.LogFactory;
import nl.xillio.gitbreakers.procrastimaster.client.services.RequestHandlerFactory;
import org.slf4j.Logger;

public class ProcrastiMaster extends Application {
    private static final Logger LOGGER = LogFactory.getLog();

    public static void main(String[] args) {
        LOGGER.info("Launching {}", ProcrastiMaster.class.getSimpleName());
        launch(ProcrastiMaster.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        LOGGER.info("Setting up injection context");
        Injector injector = Guice.createInjector(new InjectionModule(primaryStage));
        FXMLLoaderService fxmlLoaderService = injector.getInstance(FXMLLoaderService.class);

        Node primaryScene = fxmlLoaderService.getView(FXMLLoaderService.View.OVERVIEW).getNode();
        primaryStage.setScene(new Scene((Parent)primaryScene));
        primaryStage.show();

        primaryStage.setMinWidth(1200);
        primaryStage.setMinHeight(800);

        RequestHandlerFactory handler = new RequestHandlerFactory();
        handler.request("overview").body().get().ifPresent(e -> {
            LOGGER.info("HELLO!!!!!");
        });
    }
}
