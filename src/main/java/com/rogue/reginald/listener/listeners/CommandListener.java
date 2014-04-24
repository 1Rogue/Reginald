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
package com.rogue.reginald.listener.listeners;

import com.rogue.reginald.Reginald;
import com.rogue.reginald.command.Command;
import com.rogue.reginald.config.ConfigValue;
import com.rogue.reginald.listener.ListenerBase;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.NoticeEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;

/**
 *
 * @since 1.0.0
 * @author 1Rogue
 * @version 1.0.0
 */
public class CommandListener extends ListenerBase {

    public CommandListener(Reginald project) {
        super(project);
    }

    @Override
    public void onMessage(MessageEvent event) {
        String prefix = this.project.getConfig().getString(ConfigValue.COMMAND_PREFIX);
        if (event.getMessage().startsWith(prefix) && event.getMessage().length() != 1) {
            this.project.getCommandHandler().dispatchCommand(
                    new Command(event.getUser(), event.getChannel(), event.getMessage()));
        }
    }

    @Override
    public void onPrivateMessage(PrivateMessageEvent event) {
        String prefix = this.project.getConfig().getString(ConfigValue.COMMAND_PREFIX);
        if (event.getMessage().startsWith(prefix) && event.getMessage().length() != 1) {
            this.project.getCommandHandler().dispatchCommand(
                    new Command(event.getUser(), null, event.getMessage()));
        }
    }

    public void onNotice(NoticeEvent event) {
        String prefix = this.project.getConfig().getString(ConfigValue.COMMAND_PREFIX);
        if (event.getMessage().startsWith(prefix) && event.getMessage().length() != 1) {
            this.project.getCommandHandler().dispatchCommand(
                    new Command(event.getUser(), null, event.getMessage()));
        }
    }
}
