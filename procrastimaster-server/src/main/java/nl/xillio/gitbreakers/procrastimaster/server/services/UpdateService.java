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

import nl.xillio.gitbreakers.procrastimaster.server.model.Future;
import nl.xillio.gitbreakers.procrastimaster.server.model.History;
import nl.xillio.gitbreakers.procrastimaster.server.model.WorkingDay;
import nl.xillio.gitbreakers.procrastimaster.server.model.entity.Update;
import nl.xillio.gitbreakers.procrastimaster.server.model.entity.User;
import nl.xillio.gitbreakers.procrastimaster.server.repositories.UpdateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;

@Service
public class UpdateService extends AbstractService<Update, UpdateRepository> {
    private final UserService userService;

    @Autowired
    public UpdateService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void save(Update entity, User owner) {
        setNextWorkingDayToMidnight(entity);
        super.save(entity, owner);
    }

    public History getHistory() {
        History history = new History();

        for (User user : userService.getAll()) {
            getLastUpdate(user)
                    .ifPresent(history.getUpdates()::add);
        }

        return history;
    }

    public Future getFuture() {
        Future future = new Future();

        for (User user : userService.getAll()) {
            getLastUpdateWithWorkingDayInFuture(user)
                    .map(update -> {
                        WorkingDay workingDay = new WorkingDay();
                        workingDay.setUser(user);
                        workingDay.setWorkingDay(update.getNextDay());
                        return workingDay;
                    }).ifPresent(future.getWorkingDays()::add);
        }

        return future;
    }

    private void setNextWorkingDayToMidnight(Update entity) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(entity.getNextDay());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        entity.setNextDay(calendar.getTime());
    }

    private Optional<Update> getLastUpdateWithWorkingDayInFuture(User user) {
        return getRepository().findTopByCreatedByAndNextDayAfterOrderByCreatedOn(user, new Date());
    }

    public Optional<Update> getLastUpdate(User user) {
        return getRepository().findTopByCreatedByOrderByCreatedOn(user);
    }
}
