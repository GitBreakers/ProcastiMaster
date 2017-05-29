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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * Created by Jorn on 29/05/2017.
 */
public class PastController implements Initializable {

    @FXML
    Text title;

    @FXML
    VBox items;

    public void initialize(URL location, ResourceBundle resources) {
        title.setText("History");

        String text[] = new String[]{"And so am I!", "And dont forget about me!", "I am not a ghost, more like a slime",
                "Im just the ghost of Easter.."};

        for(String t : text)
        {
            Text item = new Text(t);
            items.getChildren().add(item);
        }

    }
}
