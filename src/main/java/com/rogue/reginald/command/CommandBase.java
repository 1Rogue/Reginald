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

/**
 *
 * @since 1.0.0
 * @author 1Rogue
 * @version 1.0.0
 */
public abstract class CommandBase {

    /**
     * Executes a relevant command grabbed from the CommandHandler.
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @param command The command being executed
     *
     * @return true on success, false if failed
     */
    public abstract boolean execute(Command command);

    /**
     * Returns the name of the command, used for storing a hashmap of the
     * commands
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @return The command's name
     */
    public abstract String getName();

    /**
     * Returns the command usage
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @return Usage for this {@link SubCommand}
     */
    public String getUsage() {
        return "/" + this.getName();
    }

    /**
     * Information about this specific command. Should be kept concise
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @return A small string about the command
     */
    public abstract String info();

}
