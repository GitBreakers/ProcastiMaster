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
import javafx.scene.control.Button;
import nl.xillio.gitbreakers.procrastimaster.client.services.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class PersonalSpaceController extends AbstractController {
    private static final Logger LOGGER = LogFactory.getLog();

    @FXML
    private Button shareButton;
    @FXML
    private Button nextButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        shareButton.setOnAction(e -> sharePost());
        nextButton.setOnAction(e -> nextPost());
    }

    private void sharePost() {
        LOGGER.info("Sharing post");
    }

    private void nextPost() {
        LOGGER.info("Requesting next post");
    }
}
