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


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Control;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import nl.xillio.gitbreakers.procrastimaster.client.services.TableEntry;

import java.net.URL;
import java.util.ResourceBundle;

/**
<<<<<<< Updated upstream
 * Created by Jorn on 29/05/2017.
 */
public class FutureController implements Initializable {

    @FXML
    Text title;

    @FXML
    TableView tableView;

    @FXML
    TableColumn<String, String> userColumn;

    @FXML
    TableColumn<String, String> infoColumn;

    ObservableList<TableEntry> data =
            FXCollections.observableArrayList(
                    new TableEntry("Dwight", "Monday @ XHQ"),
                    new TableEntry("Luca", "Monday @ XHQ"),
                    new TableEntry("Pieter", "Tuesday @ Home"),
                    new TableEntry("Thomas", "Next week")
            );



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userColumn.setCellValueFactory(new PropertyValueFactory<>("user"));
        infoColumn.setCellValueFactory(new PropertyValueFactory<>("info"));

        tableView.setItems(data);
        title.setText("Future");

        tableView.widthProperty().addListener((source, oldWidth, newWidth) -> {
            //Don't show header
            Pane header = (Pane) tableView.lookup("TableHeaderRow");
            if (header.isVisible()){
                header.setMaxHeight(0);
                header.setMinHeight(0);
                header.setPrefHeight(0);
                header.setVisible(false);
            }
        });


        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableView.widthProperty().addListener((source, oldWidth, newWidth) -> {
            //Don't show header
            Pane header1 = (Pane) tableView.lookup("TableHeaderRow");
            if (header1.isVisible()){
                header1.setMaxHeight(0);
                header1.setMinHeight(0);
                header1.setPrefHeight(0);
                header1.setVisible(false);
            }
        });

        infoColumn.setPrefWidth(500);
        infoColumn.setCellFactory(param -> {
            TableCell<String, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(cell.getHeight());
            text.wrappingWidthProperty().bind(infoColumn.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell ;
        });

        userColumn.setMinWidth(Control.USE_COMPUTED_SIZE);
        userColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.20));
        infoColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.79));

        userColumn.setResizable(false);
        infoColumn.setResizable(false);

    }
}
