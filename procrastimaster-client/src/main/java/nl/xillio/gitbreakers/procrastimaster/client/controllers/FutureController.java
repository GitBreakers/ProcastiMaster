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

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Jorn on 29/05/2017.
 */
public class FutureController implements Initializable {

    @FXML
    Text title;

    @FXML
    TableView<Entry> tableView;

    @FXML
    TableColumn<String, String> userColumn;

    @FXML
    TableColumn<String, String> infoColumn;

    ObservableList<Entry> data =
            FXCollections.observableArrayList(
                    new Entry("Dwight", "Monday @ XHQ"),
                    new Entry("Luca", "Monday @ XHQ"),
                    new Entry("Pieter", "Tuesday @ Home"),
                    new Entry("Thomas", "Next week")
            );

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userColumn.setCellValueFactory(new PropertyValueFactory<>("user"));
        infoColumn.setCellValueFactory(new PropertyValueFactory<>("info"));

        tableView.setItems(data);
        title.setText("Future");

        tableView.widthProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> source, Number oldWidth, Number newWidth)
            {
                //Don't show header
                Pane header = (Pane) tableView.lookup("TableHeaderRow");
                if (header.isVisible()){
                    header.setMaxHeight(0);
                    header.setMinHeight(0);
                    header.setPrefHeight(0);
                    header.setVisible(false);
                }
            }
        });
    }

    public class Entry
    {
        private SimpleStringProperty user;
        private SimpleStringProperty info;

        private Entry(String user, String info)
        {
            this.user = new SimpleStringProperty(user);
            this.info = new SimpleStringProperty(info);
        }

        public String getUser()
        {
            return user.get();
        }

        public void setUser(String user)
        {
            this.user.set(user);
        }

        public String getInfo()
        {
            return info.get();
        }

        public void setInfo(String info)
        {
            this.info.set(info);
        }
    }
}
