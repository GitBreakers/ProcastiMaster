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
package nl.xillio.gitbreakers.procrastimaster.client.services;

import javafx.scene.Node;
import nl.xillio.gitbreakers.procrastimaster.client.controllers.AbstractController;

public class LoadedView {
    private final Node node;
    private final AbstractController controller;

    public LoadedView(Node node, AbstractController controller) {
        this.node = node;
        this.controller = controller;
    }

    public Node getNode() {
        return node;
    }

    public AbstractController getController() {
        return controller;
    }
}
