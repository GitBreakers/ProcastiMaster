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
package nl.xillio.gitbreakers.procrastimaster.server.services;

import nl.xillio.gitbreakers.procrastimaster.server.DateUtils;
import nl.xillio.gitbreakers.procrastimaster.server.model.Today;
import nl.xillio.gitbreakers.procrastimaster.server.model.entity.Planning;
import nl.xillio.gitbreakers.procrastimaster.server.model.entity.User;
import nl.xillio.gitbreakers.procrastimaster.server.repositories.PlanningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;

@Service
public class PlanningService extends AbstractService<Planning, PlanningRepository> {
    private final UserService userService;

    @Autowired
    public PlanningService(UserService userService) {
        this.userService = userService;
    }


    public Today getToday() {
        Today today = new Today();

        for (User user : userService.getAll()) {
            getTodayPlanning(user)
                    .ifPresent(today.getPlannings()::add);
        }

        return today;
    }

    public Optional<Planning> getTodayPlanning(User user) {
        return getRepository().findTopByCreatedOnBetweenAndCreatedBy(
                DateUtils.todayMidnight(),
                DateUtils.tonightMidnight(),
                user
        );
    }
}
