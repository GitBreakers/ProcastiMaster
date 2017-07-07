package nl.xillio.gitbreakers.procrastimaster.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Jorn on 06/07/2017.
 */
public class LoginController implements Initializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

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
