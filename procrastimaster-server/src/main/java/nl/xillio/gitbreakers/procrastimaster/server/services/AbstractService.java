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

import nl.xillio.gitbreakers.procrastimaster.server.model.BaseEntity;
import nl.xillio.gitbreakers.procrastimaster.server.model.User;
import nl.xillio.gitbreakers.procrastimaster.server.repositories.AbstractRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.NoSuchElementException;

/**
 * This service is responsible for all generic entity operations.
 * All operations which are implemented here, will be available to all entity services.
 *
 * @param <T> The type of entity that is managed by this service
 * @param <R> The type of the repository used by this service
 */
public abstract class AbstractService<T extends BaseEntity, R extends AbstractRepository<T>> {
    @Autowired
    private R repository;

    protected R getRepository() {
        return repository;
    }

    void setRepository(R repository) {
        this.repository = repository;
    }

    public Iterable<T> getAll() {
        return repository.findAll();
    }

    public T get(Integer id) {
        T result = repository.findOne(id);

        if (result == null) {
            throw new NoSuchElementException();
        }

        return result;
    }

    public void delete(Integer id) {
        repository.delete(id);
    }

    public void save(T entity, User owner) {
        if (entity.getId() == null) {
            entity.setCreatedBy(owner);
        }
        repository.save(entity);
    }
}
