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
package ninja.rogue.reginald.listener.listeners;

import ninja.rogue.reginald.Reginald;
import ninja.rogue.reginald.command.Command;
import ninja.rogue.reginald.config.ConfigValue;
import ninja.rogue.reginald.listener.ListenerBase;
import org.pircbotx.Channel;
import org.pircbotx.User;
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

    private static final String PREFIX = ConfigValue.COMMAND_PREFIX.as(String.class);

    public CommandListener(Reginald project) {
        super(project);
    }

    @Override
    public void onMessage(MessageEvent event) {
        this.onCommand(event.getMessage(), event.getUser(), event.getChannel());
    }

    @Override
    public void onPrivateMessage(PrivateMessageEvent event) {
        this.onCommand(event.getMessage(), event.getUser(), null);
    }

    @Override
    public void onNotice(NoticeEvent event) {
        this.onCommand(event.getMessage(), event.getUser(), event.getChannel());
    }

    private void onCommand(String message, User user, Channel chan) {
        if (message.startsWith(PREFIX) && message.length() != 1) {
            this.project.getCommandHandler().dispatchCommand(
                    new Command(user, chan, message));
        }
    }
}
