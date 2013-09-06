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
package com.rogue.fruitloop.config;

import com.rogue.fruitloop.Fruitloop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @since 1.0
 * @author Rogue
 * @version 1.0
 */
public class ConfigurationLoader {

    private final Fruitloop project;
    private final File config = new File("config.txt");
    private final Map<String, String> values = new ConcurrentHashMap();

    public ConfigurationLoader(Fruitloop project) {
        this.project = project;

        this.loadFile();
        this.verifyValues();
    }

    private void loadFile() {
        try {
            if (!config.exists()) {
                config.createNewFile();
                // config.loadDefault();
            }
            FileInputStream fis = new FileInputStream(config);
            InputStreamReader isr = new InputStreamReader(fis);
            String line;
            String[] val;
            if (isr.ready()) {
                BufferedReader br = new BufferedReader(isr);
                while (br.ready()) {
                    line = br.readLine();
                    val = line.split("=");
                    if (val.length == 2) {
                        if (!val[1].isEmpty()) {
                            synchronized (values) {
                                values.put(val[0], val[1]);
                            }
                        }
                    } else {
                        System.out.println("Invalid configuration line found, skipping...");
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ConfigurationLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void verifyValues() {
        if (!values.containsKey("username")) { values.put("username", "Fruitloop"); }
        // add other defaults
    }
    
    public String getValue(String key) {
        synchronized (values) {
            return values.get(key);
        }
    }
}
