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
import com.rogue.reginald.command.CommandInfo;
import com.rogue.reginald.command.commands.ShowTellsCommand;
import com.rogue.reginald.config.ConfigValue;
import com.rogue.reginald.listener.ListenerBase;
import org.pircbotx.User;
import org.pircbotx.hooks.events.ActionEvent;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Handles messaging notifications
 *
 * @since 1.0.0
 * @author 1Rogue
 * @version 1.0.0
 */
public class TellListener extends ListenerBase {

    private final Map<String, Long> times = new HashMap<>();

    public TellListener(Reginald project) {
        super(project);
    }

    public void onAction(ActionEvent event) {
        if (!event.getMessage().equalsIgnoreCase(this.getShowTellsCommand())) {
            this.handleNotify(event.getUser());
        }
    }

    public void onMessage(MessageEvent event) {
        if (!event.getMessage().equalsIgnoreCase(this.getShowTellsCommand())) {
            this.handleNotify(event.getUser());
        }
    }

    private String getShowTellsCommand() {
        return this.project.getConfig().getString(ConfigValue.COMMAND_PREFIX)
                + ShowTellsCommand.class.getAnnotation(CommandInfo.class).name();
    }

    public void handleNotify(User user) {
        if (this.project.getMessageHandler().hasMessages(user)) {
            Long last = this.times.get(user.getNick());
            if (last == null || TimeUnit.NANOSECONDS.toHours(System.nanoTime() - last) >= 1) {
                user.send().notice("You have messages! Use " + this.getShowTellsCommand() + " to view them");
                this.times.put(user.getNick(), System.nanoTime());
            }
        }
    }

}
