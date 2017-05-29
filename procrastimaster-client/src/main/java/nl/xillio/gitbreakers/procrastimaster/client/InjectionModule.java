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
package nl.xillio.gitbreakers.procrastimaster.client;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import javafx.stage.Stage;
import nl.xillio.gitbreakers.procrastimaster.client.services.AsyncExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;


public class InjectionModule extends AbstractModule {
    private final Stage primaryStage;

    public InjectionModule(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    protected void configure() {
        bind(Stage.class).toInstance(primaryStage);
    }

    @Provides
    @Singleton
    AsyncExecutor asyncExecutor() {
        ThreadFactory defaultThreadFactory = Executors.defaultThreadFactory();

        ThreadFactory threadFactory = run -> {
            Thread result = defaultThreadFactory.newThread(run);
            result.setDaemon(true);
            return result;
        };

        ExecutorService executorService = Executors.newFixedThreadPool(4, threadFactory);

        return new AsyncExecutor(executorService);
    }
}
