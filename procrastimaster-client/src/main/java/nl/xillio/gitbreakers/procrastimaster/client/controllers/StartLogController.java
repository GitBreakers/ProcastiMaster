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

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import nl.xillio.gitbreakers.procrastimaster.client.events.EventDispatcher;
import nl.xillio.gitbreakers.procrastimaster.client.events.StartLogPostedEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class StartLogController extends AbstractController {
    @FXML
    private TextField focusText;
    @FXML
    private TextArea workText;
    @FXML
    private TextArea helpText;
    @FXML
    private Button updateButton;

    private final EventDispatcher<StartLogPostedEvent> startLogPostedEventDispatcher = new EventDispatcher<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        updateButton.setOnAction(e -> startLogPostedEventDispatcher.fire(new StartLogPostedEvent()));
    }

    public void addOnStartLogPosted(EventHandler<StartLogPostedEvent> handler) {
        startLogPostedEventDispatcher.addHandler(handler);
    }
}
