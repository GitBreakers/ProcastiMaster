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

import nl.xillio.gitbreakers.procrastimaster.server.model.Future;
import nl.xillio.gitbreakers.procrastimaster.server.model.History;
import nl.xillio.gitbreakers.procrastimaster.server.model.Overview;
import nl.xillio.gitbreakers.procrastimaster.server.model.Today;
import nl.xillio.gitbreakers.procrastimaster.server.services.PlanningService;
import nl.xillio.gitbreakers.procrastimaster.server.services.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("overview")
public class OverviewController {
    private final UpdateService updateService;
    private final PlanningService planningService;

    @Autowired
    public OverviewController(UpdateService updateService, PlanningService planningService) {
        this.updateService = updateService;
        this.planningService = planningService;
    }

    @RequestMapping("")
    public Overview getOverview() {
        Overview overview = new Overview();
        overview.setHistory(getHistory());
        overview.setFuture(getFuture());
        overview.setToday(getToday());
        return overview;
    }

    @RequestMapping("history")
    public History getHistory() {
        return updateService.getHistory();
    }

    @RequestMapping("today")
    public Today getToday() {
        return planningService.getToday();
    }

    @RequestMapping("future")
    public Future getFuture() {
        return updateService.getFuture();
    }
}
