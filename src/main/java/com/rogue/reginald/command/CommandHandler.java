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
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @since 1.0.0
 * @author 1Rogue
 * @version 1.0.0
 */
public class CommandHandler {
    
    private final Reginald project;
    private final Map<String, CommandBase> commands = new HashMap<>();
    
    public CommandHandler(Reginald project) {
        this.project = project;
        
        CommandBase[] cmds = new CommandBase[]{};
        
        for (CommandBase cmd : cmds) {
            this.commands.put(cmd.getName().toLowerCase(), cmd);
        }
    }
    
    public boolean dispatchCommand(Command cmd) {
        CommandBase command = this.commands.get(cmd.getName().toLowerCase());
        if (command == null) {
            cmd.sendMessage("No command found for '" + cmd.getName() + "'.");
        } else {
            command.execute(cmd, cmd.getArgs()).handle(cmd, command);
        }
        return false;
    }
    
}
