/*
 * ModerationUtils - Common.java
 * Copyright (C) 2022 mnewt00
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
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.mnewt00.moderationutils.utils;

import com.mnewt00.moderationutils.ModerationUtils;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

@UtilityClass
public class Common {
    private static final Plugin PLUGIN = ModerationUtils.getInstance();
    private static final CommandSender CONSOLE_SENDER = Bukkit.getServer().getConsoleSender();

    public String legacyColor(final String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public String removeColor(final String message) {
        return message.replaceAll("(" + ChatColor.COLOR_CHAR + "|&)([0-9a-fk-or])", "");
    }

    public void log(final String message) {
        if (Bukkit.getServer() == null) System.out.println(removeColor(message));
        else CONSOLE_SENDER.sendMessage(legacyColor(message));
    }

    public void tell(Player player, String message) {
        player.sendMessage(Common.legacyColor(message));
    }
}
