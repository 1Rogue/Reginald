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
package com.rogue.reginald.listener;

import com.rogue.reginald.Reginald;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

/**
 *
 * @since 1.0
 * @author 1Rogue
 * @version 1.0
 */
public class GithubListener extends ListenerAdapter {

    private final Reginald project;

    public GithubListener(Reginald project) {
        this.project = project;
    }

    public void onMessage(MessageEvent event) {
        if (event.getUser().getNick().equals("RogueGithub")) {
            this.project.getBot().sendMessage("#Rogue", event.getMessage());
        }
    }
}