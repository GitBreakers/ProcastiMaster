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
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import nl.xillio.gitbreakers.procrastimaster.client.services.ButtonEnterHandlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdatesController implements Initializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdatesController.class);

    @FXML
    private Text feedback;
    @FXML
    private TextArea updateArea;
    @FXML
    private Button helpButton;
    @FXML
    private Button postButton;

    private final ButtonEnterHandlerService enterHandlerService;

    @Inject
    public UpdatesController(ButtonEnterHandlerService enterHandlerService) {
        this.enterHandlerService = enterHandlerService;
    }

    public void initialize(URL location, ResourceBundle resources) {
        enterHandlerService.handleEnter(helpButton, postButton);

        helpButton.setOnAction(e -> askHelp());
        postButton.setOnAction(e -> postUpdate());
    }

    private void askHelp() {
        LOGGER.info("Asking for help");
    }

    private void postUpdate() {
        LOGGER.info("Posting update");
    }
}
