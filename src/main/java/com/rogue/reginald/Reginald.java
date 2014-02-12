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
package com.rogue.reginald;

import com.rogue.reginald.command.CommandHandler;
import com.rogue.reginald.config.ConfigurationLoader;
import com.rogue.reginald.listener.ListenerHandler;
import com.rogue.reginald.permission.PermissionsManager;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;
import org.pircbotx.exception.NickAlreadyInUseException;

/**
 *
 * @since 1.0
 * @author 1Rogue
 * @version 1.0
 */
public class Reginald extends Start {

    private final PircBotX bot;
    private final ConfigurationLoader config;
    private final ListenerHandler listener;
    private final CommandHandler command;
    private final PermissionsManager permissions;

    /**
     * {@link Reginald} constructor
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    public Reginald() {

        //onLoad
        this.config = new ConfigurationLoader(this);

        this.bot = new PircBotX();
        
        //begin
        this.begin();
        
        //onEnable
        this.permissions = new PermissionsManager(this);
        
        this.listener = new ListenerHandler(this);
        
        this.command = new CommandHandler(this);
        
        Runtime.getRuntime().addShutdownHook(new Thread() {
        
            @Override
            public void run() {
                config.save();
            }
            
        });
    }

    private void begin() {
        final Map<String, String> conf;
        synchronized (conf = this.config.getConfigMap()) {
            try {
                this.bot.setName(conf.get("nick"));
                this.bot.setLogin(conf.get("username"));
                this.bot.connect(conf.get("hostname"), Integer.parseInt(conf.get("port")));
                this.bot.sendMessage("NickServ", "identify " + conf.get("password"));
                for (String chan : conf.get("defaultChans").split(",")) {
                    this.bot.joinChannel(chan);
                }
            } catch (IOException | NickAlreadyInUseException | IrcException ex) {
                Logger.getLogger(Reginald.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static Reginald getProject() {
        return instance;
    }

    public PircBotX getBot() {
        return this.bot;
    }
    
    public ConfigurationLoader getConfig() {
        return this.config;
    }
    
    public CommandHandler getCommandHandler() {
        return this.command;
    }
    
    public PermissionsManager getPermissionsManager() {
        return this.permissions;
    }
}
