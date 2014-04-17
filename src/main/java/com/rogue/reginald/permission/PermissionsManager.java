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
package com.rogue.reginald.permission;

import com.rogue.reginald.Reginald;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.pircbotx.User;

/**
 *
 * @since 1.0.0
 * @author 1Rogue
 * @version 1.0.0
 */
public class PermissionsManager {
    
    private final Reginald project;
    private final Map<String, List<Permission>> permissionMap;
    
    public PermissionsManager(Reginald project) {
        this.project = project;
        this.permissionMap = new HashMap<>();
        //TODO: add users to map
    }
    
    public boolean hasPermission(User user, Permission perm) {
        if (user == null) {
            throw new IllegalArgumentException("Cannot check permission of null user!");
        }
        if (perm == null) {
            return true;
        }
        if (perm.defaultTrue()) {
            return true;
        }
        List<Permission> perms = this.permissionMap.get(user.getNick());
        if (perms != null) {
            if (perms.contains(perm)) {
                return true;
            }
        }
        return false;
    }

}
