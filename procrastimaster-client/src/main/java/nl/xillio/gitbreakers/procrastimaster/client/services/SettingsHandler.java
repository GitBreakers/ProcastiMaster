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

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Dwight.Peters on 07-Jul-17.
 */
@Singleton
public class SettingsHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(SettingsHandler.class);
    private Map<String, Object> settings;
    private ObjectMapper objectMapper;
    private Path config;

    @Inject
    public SettingsHandler(ObjectMapperService objectMapperService) throws IOException {
        objectMapper = objectMapperService.getMapper();
        Path appHome = Paths.get(System.getProperty("user.home")).resolve(".ProcrastiMaster");
        config = appHome.resolve("config.cfg");

        settings = new HashMap<>();

        if (Files.exists(appHome) && Files.isRegularFile(config)) {
            settings = objectMapper.readValue(config.toFile(), HashMap.class);
        } else {
            settings.put(Setting.HEIGHT.toString(), Setting.HEIGHT.getDefaultValue());
            settings.put(Setting.WIDTH.toString(), Setting.WIDTH.getDefaultValue());
        }
    }

    private Object getSetting(Setting key) {
        settings.computeIfAbsent(key.toString(), e -> key.getDefaultValue());
        return settings.get(key.toString());
    }

    public String getSettingString(Setting key) {
        Object value = getSetting(key);
        return value.toString();
    }

    public boolean getSettingBool(Setting key) {
        Object value = getSetting(key);
        return (boolean) value;
    }

    public Double getSettingDouble(Setting key) {
        try {
            Object value = getSetting(key);
            return (double) value;
        } catch (ClassCastException e) {
            LOGGER.error("The \"" + key + "\" setting is not a double", e);
            return null;
        }
    }

    public Optional<Integer> getSettingInt(Setting key) {
        Object value = getSetting(key);
        return Optional.of((Integer) value);
    }

    public void setSetting(Setting key, Object value) {
        settings.put(key.toString(), value);
    }

    public void saveSettings() throws IOException {
        objectMapper.writeValue(config.toFile(), settings);
    }
}
