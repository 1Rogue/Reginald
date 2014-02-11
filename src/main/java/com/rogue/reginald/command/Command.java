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
package com.rogue.reginald.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.pircbotx.User;

/**
 *
 * @since
 * @author 1Rogue
 * @version
 */
public class Command {
    
    private final User user;
    private final String name;
    private final String[] arguments;
    
    public Command(User user, String rawCommand) {
        this.user = user;
        String[] rawArgs = this.getSplitArguments(rawCommand);
        this.name = rawArgs[0];
        if (rawArgs.length > 1) {
            this.arguments = Arrays.copyOfRange(rawArgs, 1, rawArgs.length);
        } else {
            this.arguments = new String[0];
        }
    }
    
    private String[] getSplitArguments(String raw) {
        String[] back;
        if (raw.contains("\"")) {
            char[] broken = raw.trim().toCharArray();
            StringBuilder usb = new StringBuilder();
            List<String> newargs = new ArrayList();
            boolean inquotes = false;
            for (int i = 0; i < broken.length; i++) {
                if (broken[i] == '"') {
                    inquotes = !inquotes;
                } else if (broken[i] == ' ' && inquotes == false) {
                    newargs.add(usb.toString());
                    usb = new StringBuilder();
                } else {
                    usb.append(broken[i]);
                }
            }

            return newargs.toArray(new String[newargs.size()]);
        } else {
            return raw.split(" ");
        }
    }
    
    public User getUser() {
        return this.user;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String[] getArgs() {
        return this.arguments;
    }

}
