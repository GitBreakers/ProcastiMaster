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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import nl.xillio.gitbreakers.procrastimaster.client.services.TableEntry;

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
    private final ObservableList<TableEntry> data;

    protected UserInfoController(String title) {
        this(title, FXCollections.emptyObservableList());
    }

    protected UserInfoController(String title, ObservableList<TableEntry> data) {
        this.titleString = title;
        this.data = data;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        title.setText(titleString);

        userColumn.setCellValueFactory(new PropertyValueFactory<>("user"));
        infoColumn.setCellValueFactory(new PropertyValueFactory<>("info"));

        // Set the width of the info column to the max available width (subtract 2 to hide the horizontal scrollbar).
        infoColumn.prefWidthProperty().bind(tableView.widthProperty().subtract(userColumn.widthProperty()).subtract(2));

        tableView.setItems(data);
    }
}
