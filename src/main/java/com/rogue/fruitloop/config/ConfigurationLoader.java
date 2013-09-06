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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
                try {
                    this.saveResource("config.txt");
                } catch (IOException e) {
                    this.config.createNewFile();
                }
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
        synchronized (this.values) {
            if (!this.values.containsKey("username")) { this.values.put("username", "Fruitloop"); }
            if (!this.values.containsKey("password")) { this.values.put("password", "password"); }
            if (!this.values.containsKey("hostname")) { this.values.put("hostname", "irc.esper.net"); }
            if (!this.values.containsKey("port")) { this.values.put("port", "6667"); }
            if (!this.values.containsKey("nick")) { this.values.put("nick", "Fruitloop"); }
            if (!this.values.containsKey("defaultChans")) { this.values.put("defaultChans", ""); }
        }
        // add other defaults
    }
    
    private void saveResource(String name) throws IOException, FileNotFoundException {
        File file = new File(name);
        InputStream is = ConfigurationLoader.class.getResourceAsStream("config.txt");
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
    
    public String getValue(String key) {
        synchronized (this.values) {
            return this.values.get(key);
        }
    }
    
    public Map<String, String> getConfigMap() {
        return this.values;
    }
}
