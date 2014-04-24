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

import com.rogue.reginald.Reginald;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.pircbotx.Channel;
import org.pircbotx.User;

/**
 *
 * @since
 * @author 1Rogue
 * @version
 */
public final class Command {
    
    private final User user;
    private final Channel chan;
    private final String name;
    private final String[] arguments;
    private final boolean publish;
    
    public Command(User user, Channel chan, String rawCommand) {
        this.user = user;
        this.chan = chan;
        String[] rawArgs = this.getSplitArguments(rawCommand);
        this.name = rawArgs[0].substring(1, rawArgs[0].length());
        if (rawArgs.length > 1) {
            rawArgs = Arrays.copyOfRange(rawArgs, 1, rawArgs.length);
            List<String> list = Arrays.asList(rawArgs);
            this.publish = list.contains("-p");
            if (this.publish) {
                list = new ArrayList<>(list);
                list.remove("-p");
            }
            this.arguments = list.toArray(new String[list.size()]);
        } else {
            this.publish = false;
            this.arguments = new String[0];
        }
    }
    
    private String[] getSplitArguments(String raw) {
        String[] back;
        if (raw.contains("\"")) {
            char[] broken = raw.trim().toCharArray();
            StringBuilder usb = new StringBuilder();
            List<String> newargs = new ArrayList<>();
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
            newargs.add(usb.toString());

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
    
    public Channel getChannel() {
        return this.chan;
    }
    
    public boolean isPublic() {
        return this.publish;
    }
    
    public void sendMessage(String message) {
        if (this.isPublic() && this.getChannel() != null) {
            this.getChannel().send().message(this.getUser().getNick() + ": " + message);
        } else {
            this.getUser().send().notice(message);
        }
    }

}
