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

import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.ExecutorService;

/**
 * This class is responsible for executing asynchronous background tasks.
 */
@Singleton
public class AsyncExecutor implements AutoCloseable {
    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncExecutor.class);
    private final ExecutorService executorService;

    @Inject
    public AsyncExecutor(ExecutorService executorService) {
        this.executorService = executorService;
    }

    /**
     * Execute an action in some point of time.
     *
     * @param action the action to execute
     */
    public void execute(AsyncAction action) {
        execute(action, null);
    }

    /**
     * Execute an action in some point of time.
     *
     * @param action   the action to execute
     * @param callback this callback is executed if the action completes successfully
     */
    public void execute(AsyncAction action, AsyncCallback callback) {
        execute(action, callback, this::logError);
    }

    /**
     * Execute an action in some point of time.
     *
     * @param action          the action to execute
     * @param callback        this callback is executed if the action completes successfully
     * @param failureCallback this callback is executed if the action fails (throws an exception)
     */
    public void execute(AsyncAction action, AsyncCallback callback, AsyncFailureCallback failureCallback) {
        executorService.execute(() -> doExecute(action, callback, failureCallback));
    }

    /**
     * Execute an action in some point of time.
     * The action and callback run on the JavaFX Application Thread.
     *
     * @param action the action to execute
     */
    public void executeOnPlatform(AsyncAction action) {
        executeOnPlatform(action, null);
    }

    /**
     * Execute an action in some point of time.
     * The action and callback run on the JavaFX Application Thread.
     *
     * @param action   the action to execute
     * @param callback this callback is executed if the action completes successfully
     */
    public void executeOnPlatform(AsyncAction action, AsyncCallback callback) {
        executeOnPlatform(action, callback, this::logError);
    }

    /**
     * Execute an action in some point of time.
     * The action and callbacks run on the JavaFX Application Thread.
     *
     * @param action          the action to execute
     * @param callback        this callback is executed if the action completes successfully
     * @param failureCallback this callback is executed if the action fails (throws an exception)
     */
    public void executeOnPlatform(AsyncAction action, AsyncCallback callback, AsyncFailureCallback failureCallback) {
        Platform.runLater(() -> doExecute(action, callback, failureCallback));
    }

    private void doExecute(AsyncAction action, AsyncCallback callback, AsyncFailureCallback failureCallback) {
        try {
            action.run();

            if (callback != null) {
                callback.onComplete();
            }
        } catch (Exception e) {
            if (failureCallback != null) {
                failureCallback.onFailure(e);
            }
        }
    }

    private void logError(Exception e) {
        LOGGER.error("Uncaught exception in background thread.", e);
    }

    @FunctionalInterface
    public interface AsyncAction {
        void run() throws Exception;
    }

    @FunctionalInterface
    public interface AsyncCallback {
        void onComplete();
    }

    @FunctionalInterface
    public interface AsyncFailureCallback {
        void onFailure(Exception e);
    }

    @Override
    public void close() {

    }
}
