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
package ninja.rogue.reginald.listener;

import ninja.rogue.reginald.Reginald;
import ninja.rogue.reginald.listener.listeners.CommandListener;
import ninja.rogue.reginald.listener.listeners.TellListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 *
 * @since 1.0.0
 * @author 1Rogue
 * @version 1.0.0
 */
public class ListenerHandler {
    
    private final Reginald project;
    private final Map<String, ListenerBase> listeners = new HashMap<>();
    
    public ListenerHandler(Reginald project) {
        this.project = project;
        
        ListenerBase[] list = new ListenerBase[] {
            new CommandListener(this.project),
            new TellListener(this.project),
        };
        
        for (ListenerBase l : list) {
            this.listeners.put(l.getName(), l);
        }
    }
    
    public Collection<ListenerBase> getListeners() {
        return Collections.unmodifiableCollection(this.listeners.values());
    }
    
    public ListenerBase getListener(String name) {
        return this.listeners.get(name);
    }

    public <T extends ListenerBase> ListenerBase getListener(Class<T> listener) {
        try {
            Method m = listener.getDeclaredMethod("getName");
            m.setAccessible(true);
            return this.listeners.get((String) m.invoke(listener));
        } catch (IllegalAccessException ex) {
            this.project.getLogger().log(Level.SEVERE, "No allowed access to method!", ex);
        } catch (InvocationTargetException ex) {
            this.project.getLogger().log(Level.SEVERE, "Error invoking method!", ex);
        } catch (NoSuchMethodException ex) {
            this.project.getLogger().log(Level.SEVERE, "No method found!", ex);
        }
        return null;
    }

}
