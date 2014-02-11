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

import com.rogue.reginald.listener.listeners.*;
import com.rogue.reginald.Reginald;
import java.util.HashMap;
import java.util.Map;
import org.pircbotx.hooks.managers.ListenerManager;

/**
 *
 * @since
 * @author 1Rogue
 * @version
 */
public class ListenerHandler {
    
    private final Reginald project;
    private final Map<String, ListenerBase> listeners = new HashMap<>();
    
    public ListenerHandler(Reginald project) {
        this.project = project;
        
        ListenerBase[] list = new ListenerBase[] {
            new GithubListener(this.project)
        };
        
        ListenerManager lm = this.project.getBot().getListenerManager();
        for (ListenerBase l : list) {
            this.listeners.put(l.getName(), l);
            lm.addListener(l);
        }
    }

}
