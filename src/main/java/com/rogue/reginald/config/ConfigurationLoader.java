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
package com.rogue.reginald.config;

import com.rogue.reginald.Reginald;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @since 1.0
 * @author Rogue
 * @version 1.0
 */
public class ConfigurationLoader {

    private final Reginald project;
    private final File config = new File("config.json");
    private JSONObject root;

    public ConfigurationLoader(Reginald project) {
        this.project = project;

        try {
            this.loadConfig();
        } catch (IOException ex) {
            Logger.getLogger(ConfigurationLoader.class.getName()).log(Level.SEVERE, "Error reading JSON file!", ex);
        } catch (ParseException ex) {
            Logger.getLogger(ConfigurationLoader.class.getName()).log(Level.SEVERE, "Error parsing JSON file!", ex);
        }
    }

    private void loadConfig() throws IOException, ParseException {
        if (!this.config.exists()) {
            this.saveResource("config.json");
        }
        System.out.println(this.config.getAbsolutePath());
        JSONParser parser = new JSONParser();
        this.root = (JSONObject) parser.parse(new FileReader(this.config));
        for (ConfigValue val : ConfigValue.values()) {
            if (!this.isSet(val)) {
                this.set(val, val.getDefault());
            }
        }
    }

    public void set(ConfigValue path, Object value) {
        String[] ladder = path.getPath().split("\\.");
        JSONObject container = this.root;
        for (int i = 0; i < ladder.length - 1; i++) {
            JSONObject temp = (JSONObject) container.get(ladder[i]);
            if (temp == null) {
                container.put(ladder[i], new JSONObject());
            }
            container = (JSONObject) container.get(ladder[i]);
        }
        container.put(ladder[ladder.length - 1], value);
    };

    public Object get(ConfigValue path) {
        String[] ladder = path.getPath().split("\\.");
        JSONObject container = this.root;
        for (int i = 0; i < ladder.length - 1; i++) {
            container = (JSONObject) container.get(ladder[i]);
            if (container == null) {
                return null;
            }
        }
        return container.get(ladder[ladder.length - 1]);
    }

    public boolean isSet(ConfigValue path) {
        return this.get(path) != null;
    }

    public String getString(ConfigValue path) {
        return this.getString(path, null);
    }

    public String getString(ConfigValue path, String def) {
        Object val = this.get(path);
        return val == null ? def : val.toString();
    }

    public int getInt(ConfigValue path) {
        return this.getInt(path, -1);
    }

    public int getInt(ConfigValue path, int def) {
        Object val = this.get(path);
        return val == null ? def : Integer.valueOf(val.toString());
    }

    public List<String> getStringList(ConfigValue path) {
        List<?> str = this.getList(path);
        List<String> back = new ArrayList<>();
        str.stream().forEach((s) -> back.add(s.toString()));
        return back;
    }

    public List<?> getList(ConfigValue path) {
        Object obj = this.get(path);
        List<Object> back = new ArrayList<>();
        if (obj != null) {
            back.addAll((JSONArray) obj);
        }
        return back;
    }

    private void saveResource(String name) throws IOException, FileNotFoundException {
        File file = new File(name);
        System.out.println(String.format("Name = '%s', saving...", name));
        InputStream is = ConfigurationLoader.class.getResourceAsStream(name);
        if (is == null) {
            throw new FileNotFoundException();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(file);
        byte[] buffer = new byte[102400];
        int len;
        while ((len = is.read(buffer)) != -1) {
            fos.write(buffer, 0, len);
        }

    }
    
    public void save(){} //TODO: write
}
