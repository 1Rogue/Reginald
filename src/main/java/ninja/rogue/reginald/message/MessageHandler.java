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
package ninja.rogue.reginald.message;

import ninja.rogue.reginald.Reginald;
import org.pircbotx.User;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

/**
 * Handles messages to and from recipients
 *
 * @since 1.0.0
 * @author 1Rogue
 * @version 1.0.0
 */
public class MessageHandler {

    private final Reginald project;
    private Map<String, List<Message>> messages = new HashMap<>();

    public MessageHandler(Reginald project) {
        this.project = project;
    }

    public void newMessage(String sender, String target, String message, String channel) {
        this.getMessageList(target.toLowerCase()).add(new Message(sender, target, message, channel));
    }

    private List<Message> getMessageList(String name) {
        List<Message> back = this.messages.get(name.toLowerCase());
        if (back == null) {
            back = new ArrayList<>();
            this.messages.put(name.toLowerCase(), back);
        }
        return back;
    }

    public List<Message> readMessages(User user) {
        List<Message> curr = this.getMessageList(user.getNick());
        if (curr.isEmpty()) {
            user.send().notice("You have no tells at this time");
            return new ArrayList<>();
        }
        List<Message> back = new ArrayList<>(curr.size());
        try { // Temporarily wrapped, seems to have been causing issues
            Collections.copy(back, curr);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        for (Message m : curr) {
            user.send().notice(m.toString());
        }
        curr.clear();
        return back;
    }

    public boolean hasMessages(User user) {
        return !this.getMessageList(user.getNick()).isEmpty();
    }

}
