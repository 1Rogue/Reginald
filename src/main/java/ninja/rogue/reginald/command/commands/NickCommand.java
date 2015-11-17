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
package ninja.rogue.reginald.command.commands;

import ninja.rogue.reginald.Reginald;
import ninja.rogue.reginald.command.Command;
import ninja.rogue.reginald.command.CommandBase;
import ninja.rogue.reginald.command.CommandInfo;
import ninja.rogue.reginald.command.CommandStatus;
import ninja.rogue.reginald.permission.Permission;

/**
 *
 * @since 1.0.0
 * @author 1Rogue
 * @version 1.0.0
 */
@CommandInfo(name = "nick")
public class NickCommand extends CommandBase {
    
    public NickCommand(Reginald project) {
        super(project);
    }

    @Override
    public CommandStatus execute(Command cmd, String[] args) {
        if (args.length < 1) {
            return CommandStatus.BAD_ARGS;
        }
        CommandStatus stat = this.verify(cmd.getUser(), Permission.NICK);
        if (stat == CommandStatus.SUCCESS) {
            this.project.getBot().sendIRC().changeNick(args[0]);
        }
        return stat;
    }

    @Override
    public String getName() {
        return "nick";
    }
    
    @Override
    public String getUsage() {
        return super.getUsage() + " <new-name>";
    }

    @Override
    public String info() {
        return "Changes the nick that the bot uses";
    }
    
    

}
