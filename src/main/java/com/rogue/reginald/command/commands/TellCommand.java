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
import com.rogue.reginald.command.CommandStatus;
import com.rogue.reginald.permission.Permission;

import java.util.Arrays;

/**
 * Creates a new message to deliver to an irc target
 *
 * @since 1.0.0
 * @author 1Rogue
 * @version 1.0.0
 */
public class TellCommand extends CommandBase {

    public TellCommand(Reginald project) {
        super(project);
    }

    @Override
    public CommandStatus execute(Command cmd, String[] args) {
        if (args.length < 2) {
            return CommandStatus.BAD_ARGS;
        }
        CommandStatus stat = this.verify(cmd.getUser(), Permission.TELL);
        if (stat == CommandStatus.SUCCESS) {
            String target = args[0];
            args = Arrays.copyOfRange(args, 1, args.length);
            StringBuilder sb = new StringBuilder();
            boolean first = true;
            for (String s : args) {
                if (first) {
                    first = false;
                } else {
                    sb.append(" ");
                }
                sb.append(s);
            }
            this.project.getMessageHandler().newMessage(cmd.getUser().getNick(), args[0], sb.toString(), cmd.getChannel().getName());
        }
        return stat;
    }

    @Override
    public String getName() {
        return "tell";
    }

    @Override
    public String info() {
        return "Sends a message to an IRC recipient";
    }
}
