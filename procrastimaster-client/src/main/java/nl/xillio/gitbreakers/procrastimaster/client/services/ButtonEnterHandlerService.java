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

import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javax.inject.Singleton;

/**
 * This service makes sure a button is fired if the enter key is pressed while the button is focused.
 * By default this only happens if the space bar is pressed.
 */
@Singleton
public class ButtonEnterHandlerService {
    /**
     * Handle the enter key for one or more buttons.
     *
     * @param buttons The buttons to handle the enter key for.
     */
    public void handleEnter(Button... buttons) {
        for (Button button : buttons) {
            button.addEventHandler(KeyEvent.KEY_PRESSED, k -> {
                if (k.getCode() == KeyCode.ENTER) {
                    button.fire();
                    k.consume();
                }
            });
        }
    }
}
