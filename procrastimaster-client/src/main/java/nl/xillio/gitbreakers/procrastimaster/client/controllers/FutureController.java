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
import nl.xillio.gitbreakers.procrastimaster.client.TableEntry;
import nl.xillio.gitbreakers.procrastimaster.server.model.Future;

public class FutureController extends UserInfoController {
    public FutureController() {
        super("Future", FXCollections.observableArrayList(
                new TableEntry("Dwight", "Monday @ XHQ"),
                new TableEntry("Luca", "Monday @ XHQ"),
                new TableEntry("Pieter", "Tuesday @ Home"),
                new TableEntry("Thomas", "Next week")
        ));
    }

    public void update(Future future) {
        this.clear();
        future.getWorkingDays().forEach(o -> {
            this.addNewEntry(o.getUser().getName(), o.getWorkingDay().toString());
        });
    }
}
