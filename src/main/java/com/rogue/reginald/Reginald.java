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
import com.rogue.reginald.config.ConfigValue;
import com.rogue.reginald.config.ConfigurationLoader;
import com.rogue.reginald.listener.ListenerHandler;
import com.rogue.reginald.listener.ListenerBase;
import com.rogue.reginald.message.MessageHandler;
import com.rogue.reginald.permission.PermissionsManager;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;

/**
 *
 * @since 1.0
 * @author 1Rogue
 * @version 1.0
 */
public final class Reginald extends Start {

    private final BotProxy bot;
    private final ConfigurationLoader config;
    private final ListenerHandler listener;
    private final CommandHandler command;
    private final PermissionsManager permissions;
    private final MessageHandler message;
    private final Logger log;

    /**
     * {@link Reginald} constructor
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    public Reginald() {

        //onLoad
        this.log = Logger.getLogger(this.getClass().getName());

        this.config = new ConfigurationLoader(this);

        //onEnable
        this.permissions = new PermissionsManager(this);

        this.message = new MessageHandler(this);

        this.listener = new ListenerHandler(this);

        this.command = new CommandHandler(this);

        Configuration c;
        Configuration.Builder build = new Configuration.Builder<>()
                .setName(this.config.getString(ConfigValue.NICK))
                .setLogin(this.config.getString(ConfigValue.USERNAME))
                .setServerHostname(this.config.getString(ConfigValue.HOSTNAME))
                .setServerPort(this.config.getInt(ConfigValue.PORT, 6667))
                .setNickservPassword(this.config.getString(ConfigValue.PASSWORD))
                .setAutoNickChange(true);
        this.config.getStringList(ConfigValue.CHANNELS).forEach(build::addAutoJoinChannel);
        this.getListenerHandler().getListeners().forEach(build::addListener);
        c = build.buildConfiguration();

        this.bot = new BotProxy(c);

        try {
            this.bot.connect();
        } catch (IOException ex) {
            this.getLogger().log(Level.SEVERE, "I/O Error upon connecting!", ex);
        } catch (IrcException ex) {
            this.getLogger().log(Level.SEVERE, "IRC Exception upon connecting!", ex);
        }

        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {
                config.save();
            }

        });

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

    public MessageHandler getMessageHandler() {
        return this.message;
    }

    public ListenerHandler getListenerHandler() {
        return this.listener;
    }

    public Logger getLogger() {
        return this.log;
    }
}
