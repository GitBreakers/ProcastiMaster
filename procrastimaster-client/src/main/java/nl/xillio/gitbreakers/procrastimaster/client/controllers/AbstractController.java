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

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.layout.VBox;
import nl.xillio.gitbreakers.procrastimaster.client.services.ButtonEnterHandlerService;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public abstract class AbstractController implements Initializable {
    private ButtonEnterHandlerService buttonEnterHandlerService;
    private OverviewController overviewController;

    @FXML
    private VBox root;

    public void initialize(URL location, ResourceBundle resources) {
        findButtonsHandleEnter(root.getChildrenUnmodifiable());
    }

    private void findButtonsHandleEnter(ObservableList<Node> nodes) {
        for (Node node : nodes) {
            // Handle enter on the button.
            if (node instanceof Button) {
                buttonEnterHandlerService.handleEnter((Button)node);
            }

            // Go deeper.
            if (node instanceof Parent) {
                findButtonsHandleEnter(((Parent)node).getChildrenUnmodifiable());
            }
            // ButtonBar does not handle children properly, so explicitly check those buttons.
            if (node instanceof ButtonBar) {
                findButtonsHandleEnter(((ButtonBar)node).getButtons());
            }
        }
    }

    public void setOverviewController(OverviewController controller) {
        overviewController = controller;
    }

    public OverviewController getOverviewController() {
        return overviewController;
    }

    /**
     * Set the button enter handler service.
     * Used to inject the service.
     *
     * @param buttonEnterHandlerService The button enter handler service to use.
     */
    @Inject
    public void setButtonEnterHandlerService(ButtonEnterHandlerService buttonEnterHandlerService) {
        this.buttonEnterHandlerService = buttonEnterHandlerService;
    }
}
