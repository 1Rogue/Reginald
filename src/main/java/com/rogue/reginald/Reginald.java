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
import com.rogue.reginald.listener.ListenerBase;
import com.rogue.reginald.message.MessageHandler;
import com.rogue.reginald.permission.PermissionsManager;
import java.util.Map;
import java.util.logging.Logger;

import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;

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

        final Map<String, String> conf;
        Configuration c;
        synchronized (conf = this.config.getConfigMap()) {
            Configuration.Builder build = new Configuration.Builder<>()
                    .setName(conf.get("nick"))
                    .setLogin(conf.get("uesrname"))
                    .setServerHostname(conf.get("hostname"))
                    .setServerPort(Integer.parseInt(conf.get("port")))
                    .setNickservPassword(conf.get("password"))
                    .setAutoNickChange(true);
            for (String chan : conf.get("defaultChans").split(",")) {
                build.addAutoJoinChannel(chan);
            }
            for (ListenerBase base : this.getListenerHandler().getListeners()) {
                build.addListener(base);
            }
            c = build.buildConfiguration();
        }

        this.bot = new BotProxy(c);

        this.bot.connect();

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
