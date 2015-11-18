/*
 * Copyright (C) 2013 Spencer Alderman
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ninja.rogue.reginald.config;

import com.codelanx.commons.config.ConfigFile;
import com.codelanx.commons.config.DataHolder;
import com.codelanx.commons.data.types.Json;

import java.util.ArrayList;

/**
 * Created by Spencer on 4/24/2014.
 */
public enum ConfigValue implements ConfigFile {

    USERNAME("info.username", "Reginald"),
    PASSWORD("info.password", "password"),
    HOSTNAME("info.hostname", "irc.esper.net"),
    PORT("info.port", 6667),
    NICK("info.nick", "Reginald"),
    CHANNELS("info.channels", new ArrayList<>()),
    COMMAND_PREFIX("command-prefix", "$"),
    ;

    private static final DataHolder<Json> DATA = new DataHolder<>(Json.class);
    private final String path;
    private final Object def;

    private ConfigValue(String path, Object def) {
        this.path = path;
        this.def = def;
    }

    public String getPath() {
        return this.path;
    }

    public Object getDefault() {
        return this.def;
    }

    @Override
    public DataHolder<Json> getData() {
        return DATA;
    }
}
