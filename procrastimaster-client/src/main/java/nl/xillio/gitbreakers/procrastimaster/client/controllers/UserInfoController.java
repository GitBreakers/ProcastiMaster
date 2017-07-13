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
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.text.Text;
import javafx.util.Duration;
import nl.xillio.gitbreakers.procrastimaster.client.TableEntry;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class UserInfoController extends AbstractController {
    @FXML
    private TableView tableView;
    @FXML
    private Text title;
    @FXML
    private TableColumn<String, String> userColumn;
    @FXML
    private TableColumn<String, String> infoColumn;

    private final String titleString;
    private final ObservableList<TableEntry> data = FXCollections.observableArrayList();

    protected UserInfoController(String title) {
        this.titleString = title;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        title.setText(titleString);

        userColumn.setCellValueFactory(new PropertyValueFactory<>("user"));
        infoColumn.setCellValueFactory(new PropertyValueFactory<>("info"));

        // Set the width of the info column to the max available width (subtract 2 to hide the horizontal scrollbar).
        infoColumn.prefWidthProperty().bind(tableView.widthProperty().subtract(userColumn.widthProperty()).subtract(2));

        tableView.setRowFactory(tv -> {
            TableRow<TableEntry> row = new TableRow<>();

            data.addListener(new ListChangeListener<TableEntry>() {
                @Override
                public void onChanged(Change<? extends TableEntry> c) {
                    if (c.next() && c.wasAdded() && row.getIndex() > 0 && data.size() > row.getIndex()) {
                        if (data.get(row.getIndex()).equals(c.getAddedSubList().get(0))) {
                            blurOut(row);
                        }
                    }
                }
            });
            return row;
        });
        tableView.setItems(data);
    }

    private static void blurOut(Node node) {
        GaussianBlur blur = new GaussianBlur(30.0);
        node.setEffect(blur);
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(blur.radiusProperty(), 0.0);
        KeyFrame kf = new KeyFrame(Duration.millis(300), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    public void addNewEntry(String userName, String entry) {
        data.add(new TableEntry(userName, entry));
        tableView.setItems(data);
    }

    public void clear() {
        data.clear();
        tableView.setItems(data);
    }
}
