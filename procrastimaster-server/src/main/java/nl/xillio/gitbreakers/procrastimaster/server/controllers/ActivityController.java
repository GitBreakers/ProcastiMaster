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
package nl.xillio.gitbreakers.procrastimaster.server.controllers;

import nl.xillio.gitbreakers.procrastimaster.server.DateUtils;
import nl.xillio.gitbreakers.procrastimaster.server.model.ActivityStatus;
import nl.xillio.gitbreakers.procrastimaster.server.model.entity.Planning;
import nl.xillio.gitbreakers.procrastimaster.server.model.entity.User;
import nl.xillio.gitbreakers.procrastimaster.server.services.PlanningService;
import nl.xillio.gitbreakers.procrastimaster.server.services.UpdateService;
import nl.xillio.gitbreakers.procrastimaster.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("activity")
public class ActivityController {
    private final UpdateService updateService;
    private final PlanningService planningService;
    private final UserService userService;

    @Autowired
    public ActivityController(UpdateService updateService, PlanningService planningService, UserService userService) {
        this.updateService = updateService;
        this.planningService = planningService;
        this.userService = userService;
    }

    @RequestMapping("")
    public ActivityStatus getActivityStatus(Principal principal) {
        User user = userService.getUser(principal);
        ActivityStatus activityStatus = new ActivityStatus();

        updateService.getLastUpdate(user)
                .filter(update -> update.getCreatedOn().after(DateUtils.todayMidnight()))
                .ifPresent(activityStatus::setUpdate);
        activityStatus.setPlanned(
                planningService.getTodayPlanning(user).isPresent());

        return activityStatus;

    }

    @RequestMapping(value = "planning", method = RequestMethod.POST)
    public void setPlanning(Planning planning, Principal principal) {
        User user = userService.getUser(principal);
        planningService.save(planning, user);
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public void setUpdate(Map<String,Object> update, Principal principal) {
        User user = userService.getUser(principal);
        //updateService.save(update, user);
    }

}
