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
package nl.xillio.gitbreakers.procrastimaster.server;

import nl.xillio.gitbreakers.procrastimaster.server.model.entity.Planning;
import nl.xillio.gitbreakers.procrastimaster.server.model.entity.Update;
import nl.xillio.gitbreakers.procrastimaster.server.model.entity.User;
import nl.xillio.gitbreakers.procrastimaster.server.services.PlanningService;
import nl.xillio.gitbreakers.procrastimaster.server.services.UpdateService;
import nl.xillio.gitbreakers.procrastimaster.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DemoContentLoader implements CommandLineRunner {
    private static final int DAY = 1000 * 60 * 60 * 24;
    private final UserService userService;
    private final PlanningService planningService;
    private final UpdateService updateService;

    @Autowired
    public DemoContentLoader(UserService userService, PlanningService planningService, UpdateService updateService) {
        this.userService = userService;
        this.planningService = planningService;
        this.updateService = updateService;
    }

    @Override
    public void run(String... strings) throws Exception {

        // Create users
        User pieter = new User();
        pieter.setName("Pieter");
        pieter.setEmail("pieter@GitBreakers.nl");

        userService.save(pieter, null);

        User luca = new User();
        luca.setName("Luca");
        luca.setEmail("luca@GitBreakers.nl");

        userService.save(luca, pieter);

        User sander = new User();
        sander.setName("Sander");
        sander.setEmail("sander@GitBreakers.nl");

        userService.save(sander, pieter);

        User thomas = new User();
        thomas.setName("Thomas");
        thomas.setEmail("thomas@GitBreakers.nl");

        userService.save(thomas, pieter);

        // Create updates/plannings
        Update update = new Update();
        update.setNextDay(new Date());
        update.setTodayIHave("Set up the infrastructure");
        update.setTodayIHaveNot("Finished the frontend");
        updateService.save(update, pieter);
        update.setCreatedOn(new Date(System.currentTimeMillis() - DAY));
        updateService.save(update, pieter);

        Planning planning = new Planning();
        planning.setMyFocus("ProcrastiMaster");
        planning.setNeedHelpWith("");
        planning.setTodayIWill("Ping Pong");
        planningService.save(planning, pieter);

        update = new Update();
        update.setNextDay(new Date(System.currentTimeMillis() + 7 * DAY));
        update.setTodayIHave("Planned a vacation");
        update.setTodayIHaveNot("Done anything productive");
        updateService.save(update, luca);


        // Thomas does bad updates every day
        for (int i = -4; i < 4; i++) {
            if (i <= 0) {
                // This is a past day
                planning = new Planning();
                planning.setTodayIWill("Do stuff");
                planning.setNeedHelpWith("Stuff");
                planning.setMyFocus("My Stuff");
                planningService.save(planning, thomas);
                planning.setCreatedOn(new Date(System.currentTimeMillis() + i * DAY));
                planningService.save(planning, thomas);
            }

            update = new Update();
            update.setTodayIHave("Done Stuff");
            update.setNextDay(new Date(System.currentTimeMillis() + (i + 1) * DAY));
            updateService.save(update, thomas);
            update.setCreatedOn(new Date(System.currentTimeMillis() + i * DAY));
            updateService.save(update, thomas);
        }
    }
}
