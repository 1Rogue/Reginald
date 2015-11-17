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
package ninja.rogue.reginald.command;

/**
 *
 * @since
 * @author 1Rogue
 * @version
 */
public enum CommandStatus {
    
    SUCCESS,
    FAILED,
    BAD_ARGS,
    NO_PERMISSION,
    UNVERIFIED;
    
    private String message;
    
    public CommandStatus setMessage(String message) {
        this.message = message;
        return this;
    }
    
    public void handle(Command cmd, CommandBase base) {
        switch (this) {
            case FAILED:
                cmd.sendMessage("Command execution failed :(");
            case BAD_ARGS:
                cmd.sendMessage("Usage: " + base.getUsage());
                cmd.sendMessage(base.info());
            case NO_PERMISSION:
                cmd.sendMessage("You do not have permission for this command!");
            case UNVERIFIED:
                cmd.sendMessage("Please authenticate your user with NickServ before using this command");
            default:
                if (message != null) {
                    cmd.sendMessage("Message from internal class (Report to developer):");
                    cmd.sendMessage(this.message);
                }
                break;
        }
    }
    
}
