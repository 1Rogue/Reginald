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
package ninja.rogue.reginald;

import ninja.rogue.reginald.command.CommandHandler;
import ninja.rogue.reginald.config.ConfigValue;
import ninja.rogue.reginald.listener.ListenerHandler;
import ninja.rogue.reginald.message.MessageHandler;
import ninja.rogue.reginald.permission.PermissionsManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

        //onEnable
        this.permissions = new PermissionsManager(this);

        this.message = new MessageHandler(this);

        this.listener = new ListenerHandler(this);

        this.command = new CommandHandler(this);

        Configuration c;
        Configuration.Builder build = new Configuration.Builder<>()
                .setName(ConfigValue.NICK.as(String.class))
                .setLogin(ConfigValue.USERNAME.as(String.class))
                .setServerHostname(ConfigValue.HOSTNAME.as(String.class))
                .setServerPort(ConfigValue.PORT.as(Integer.class))
                .setNickservPassword(ConfigValue.PASSWORD.as(String.class))
                .setAutoNickChange(true);
        ConfigValue.CHANNELS.as(List.class, String.class).forEach(build::addAutoJoinChannel);
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
                try {
                    ConfigValue.CHANNELS.save();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }7
        });

    }

    public static Reginald getProject() {
        return instance;
    }

    public PircBotX getBot() {
        return this.bot;
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
