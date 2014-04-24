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

/**
 * Created by Spencer on 4/17/2014.
 */
@CommandInfo(name = "join")
public class JoinCommand extends CommandBase {

    public JoinCommand(Reginald project) {
        super(project);
    }

    @Override
    public CommandStatus execute(Command cmd, String[] args) {
        if (args.length < 1) {
            return CommandStatus.BAD_ARGS;
        }
        CommandStatus stat = this.verify(cmd.getUser(), Permission.JOIN);
        if (stat == CommandStatus.SUCCESS) {
            this.project.getBot().sendIRC().joinChannel(args[0]);
        }
        return stat;
    }

    @Override
    public String getUsage() {
        return super.getUsage() + " <channel>";
    }

    @Override
    public String getName() {
        return "join";
    }

    @Override
    public String info() {
        return "Allows the bot to join a channel";
    }
}
