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
package nl.xillio.gitbreakers.procrastimaster.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import nl.xillio.gitbreakers.procrastimaster.client.services.AsyncExecutor;
import nl.xillio.gitbreakers.procrastimaster.client.services.FXMLLoaderService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This controller is responsible for the main layout of the application.
 */
@Singleton
public class OverviewController implements Initializable {

    @FXML
    private Text username;

    @FXML
    private Pane overviewLeft;
    @FXML
    private Pane overviewMid;
    @FXML
    private Pane overviewRight;
    @FXML
    private Pane workspaceLeft;
    @FXML
    private Pane workspaceRight;

    private final FXMLLoaderService fxmlLoaderService;
    private final AsyncExecutor asyncExecutor;

    @Inject
    public OverviewController(FXMLLoaderService fxmlLoaderService, AsyncExecutor asyncExecutor) {
        this.fxmlLoaderService = fxmlLoaderService;
        this.asyncExecutor = asyncExecutor;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Setup header
        username.setText("Jorn Verhoeven");

        // Load overview components
        loadInto(FXMLLoaderService.View.HISTORY, overviewLeft);
        loadInto(FXMLLoaderService.View.TODAY, overviewMid);
        loadInto(FXMLLoaderService.View.FUTURE, overviewRight);
        loadInto(FXMLLoaderService.View.LOG, workspaceLeft);
        loadInto(FXMLLoaderService.View.UPDATES, workspaceRight);

        // After the log has been updated
        //loadInto(FXMLLoaderService.View.PERSONALSPACE, workspaceLeft);
    }

    private void loadInto(FXMLLoaderService.View view, Pane parentPane) {
        asyncExecutor.execute(
                () -> {
                    Pane pane = fxmlLoaderService.getView(view);
                    parentPane.getChildren().setAll(pane);
                }
        );
    }
}
