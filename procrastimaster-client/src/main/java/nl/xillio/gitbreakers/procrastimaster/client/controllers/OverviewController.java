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

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import nl.xillio.gitbreakers.procrastimaster.client.LoadedView;
import nl.xillio.gitbreakers.procrastimaster.client.services.AsyncExecutor;
import nl.xillio.gitbreakers.procrastimaster.client.services.FXMLLoaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * This controller is responsible for the main layout of the application.
 */
@Singleton
public class OverviewController implements Initializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(OverviewController.class);

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

    private final Map<FXMLLoaderService.View, AbstractController> controllers = new HashMap<>();

    @Inject
    public OverviewController(FXMLLoaderService fxmlLoaderService, AsyncExecutor asyncExecutor) {
        this.fxmlLoaderService = fxmlLoaderService;
        this.asyncExecutor = asyncExecutor;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Setup header
        username.setText("Welcome " + System.getProperty("user.name"));

        // Load overview components
        loadInto(FXMLLoaderService.View.HISTORY, overviewLeft);
        loadInto(FXMLLoaderService.View.TODAY, overviewMid);
        loadInto(FXMLLoaderService.View.FUTURE, overviewRight);
        loadInto(FXMLLoaderService.View.STARTLOG, workspaceLeft);
        loadInto(FXMLLoaderService.View.UPDATES, workspaceRight);

        // Hook into events.
        ((StartLogController)controllers.get(FXMLLoaderService.View.STARTLOG)).addOnStartLogPosted(e -> startLogPosted());
    }

    private void loadIntoWithEffect(FXMLLoaderService.View view, Pane parentPane) {
        LoadedView loadedView = fxmlLoaderService.getView(view);
        StackPane stackPane = ((StackPane)parentPane);
        Timeline timeLine = translateAway(stackPane.getChildren().get(0), parentPane);
        timeLine.setOnFinished(event -> {
            parentPane.getChildren().setAll(loadedView.getNode());
            translateBack(stackPane.getChildren().get(0), parentPane);
        });

        // Save the controller.
        controllers.put(view, loadedView.getController());
    }

    private void loadInto(FXMLLoaderService.View view, Pane parentPane) {
        LoadedView loadedView = fxmlLoaderService.getView(view);
        parentPane.getChildren().setAll(loadedView.getNode());

        // Save the controller.
        controllers.put(view, loadedView.getController());
    }

    private Timeline translateBack(Node node, Node parent) {
        double width = ((Pane)parent).getWidth();
        double height = ((Pane)parent).getHeight();

        PerspectiveTransform perspectiveTransform = new PerspectiveTransform();
        perspectiveTransform.setUlx(width);
        perspectiveTransform.setUly(0);
        perspectiveTransform.setUrx(width);
        perspectiveTransform.setUry(0);
        perspectiveTransform.setLrx(width);
        perspectiveTransform.setLry(height);
        perspectiveTransform.setLlx(width);
        perspectiveTransform.setLly(height);
        node.setEffect(perspectiveTransform);

        Timeline timeline = new Timeline();

        KeyValue kv1 = new KeyValue(perspectiveTransform.llxProperty(), 0);
        KeyFrame kf1 = new KeyFrame(Duration.millis(250), kv1);

        KeyValue kv2 = new KeyValue(perspectiveTransform.ulxProperty(), 0);
        KeyFrame kf2 = new KeyFrame(Duration.millis(250), kv2);

        timeline.getKeyFrames().add(kf1);
        timeline.getKeyFrames().add(kf2);
        timeline.play();

        return timeline;
    }

    private Timeline translateAway(Node node, Node parent) {
        double width = ((Pane)parent).getWidth();
        double height = ((Pane)parent).getHeight();

        PerspectiveTransform perspectiveTrasform = new PerspectiveTransform();
        perspectiveTrasform.setUlx(0);
        perspectiveTrasform.setUly(0);
        perspectiveTrasform.setUrx(width);
        perspectiveTrasform.setUry(0);
        perspectiveTrasform.setLrx(width);
        perspectiveTrasform.setLry(height);
        perspectiveTrasform.setLlx(0);
        perspectiveTrasform.setLly(height);
        node.setEffect(perspectiveTrasform);

        Timeline timeline = new Timeline();

        KeyValue kv1 = new KeyValue(perspectiveTrasform.llxProperty(), width);
        KeyFrame kf1 = new KeyFrame(Duration.millis(250), kv1);

        KeyValue kv2 = new KeyValue(perspectiveTrasform.ulxProperty(), width);
        KeyFrame kf2 = new KeyFrame(Duration.millis(250), kv2);

        timeline.getKeyFrames().add(kf1);
        timeline.getKeyFrames().add(kf2);
        timeline.play();
        return timeline;
    }


    private void startLogPosted() {
        LOGGER.info("Start log posted");
        loadIntoWithEffect(FXMLLoaderService.View.PERSONALSPACE, workspaceLeft);

        ((UpdatesController)controllers.get(FXMLLoaderService.View.UPDATES)).enableUpdates();

        String focus = ((StartLogController)controllers.get(FXMLLoaderService.View.STARTLOG)).getFocus();
        ((TodayController)controllers.get(FXMLLoaderService.View.TODAY)).postLog(System.getProperty("user.name"), focus);
    }
}
