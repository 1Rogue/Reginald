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
package com.rogue.reginald.command.commands;

import com.rogue.reginald.Reginald;
import com.rogue.reginald.command.Command;
import com.rogue.reginald.command.CommandBase;
import com.rogue.reginald.command.CommandInfo;
import com.rogue.reginald.command.CommandStatus;
import com.rogue.reginald.permission.Permission;
import org.pircbotx.Channel;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by Spencer on 4/17/2014.
 */
@CommandInfo(name = "part")
public class PartCommand extends CommandBase {

    private final List<String> chanPrefix = Arrays.asList("#", "&");

    public PartCommand(Reginald project) {
        super(project);
    }

    @Override
    public CommandStatus execute(Command cmd, String[] args) {
        CommandStatus stat = this.verify(cmd.getUser(), Permission.PART);
        if (stat == CommandStatus.SUCCESS) {
            if (args.length == 0) {
                cmd.getChannel().send().part();
            } else if (args.length > 0) {
                Channel target;
                String reason;
                if (this.chanPrefix.contains(args[0].substring(0, 1))) {
                    target = this.findChan(this.project.getBot().getUserBot().getChannels(), args[0]);
                    String[] raw = Arrays.copyOfRange(args, 1, args.length);
                    StringBuilder sb = new StringBuilder();
                    boolean first = true;
                    for (String s : raw) {
                        if (first) {
                            first = false;
                        } else {
                            sb.append(' ');
                        }
                        sb.append(s);
                    }
                    reason = sb.toString();
                } else {
                    target = cmd.getChannel();
                    StringBuilder sb = new StringBuilder();
                    boolean first = true;
                    for (String s : args) {
                        if (first) {
                            first = false;
                        } else {
                            sb.append(' ');
                        }
                        sb.append(s);
                    }
                    reason = sb.toString();
                }
                if (target == null) {
                    cmd.sendMessage("No channel found by that name!");
                    return CommandStatus.FAILED;
                }
                if (reason == null) {
                    target.send().part();
                } else {
                    target.send().part(reason);
                }
            }
        }
        return stat;
    }

    private void findAndPart(Set<Channel> chans, String name, String reason) {
        if (reason == null) {
            this.findChan(chans, name).send().part();
        } else {
            this.findChan(chans, name).send().part(reason);
        }
    }

    private Channel findChan(Set<Channel> chans, String name) {
        for (Channel ch : chans) {
            if (ch.getName().equalsIgnoreCase(name)) {
                return ch;
            }
        }
        return null;
    }

    @Override
    public String getUsage() {
        return super.getUsage() + " [channel]";
    }

    @Override
    public String getName() {
        return "part";
    }

    @Override
    public String info() {
        return "Makes a bot leave a channel";
    }
}
