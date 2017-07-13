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
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Jorn on 06/07/2017.
 */
public class LoginController implements Initializable {
    @FXML
    private TextField emailText;
    @FXML
    private PasswordField passwordText;
    @FXML
    private CheckBox rememberCheck;
    @FXML
    private Button resetButton;
    @FXML
    private Button loginButton;
    @FXML
    private Label feedback;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        feedback.setText("");

        loginButton.setOnMousePressed(e -> onLoginButtonPressed(e));
        resetButton.setOnMousePressed(e -> onResetButtonPressed(e));
    }

    private void onLoginButtonPressed(MouseEvent e) {

    }

    private void onResetButtonPressed(MouseEvent e) {

    }
}
