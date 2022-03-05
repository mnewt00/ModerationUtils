/*
 * ModerationUtils - PAPIExtension.java
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

package com.mnewt00.moderationutils;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class PAPIExtension extends PlaceholderExpansion {
    private static PluginDescriptionFile PLUGIN_DESC = ModerationUtils.getInstance().getDescription();

    @Override
    public @NotNull
    String getIdentifier() {
        return PLUGIN_DESC.getName().toLowerCase(Locale.ROOT);
    }

    @Override
    public @NotNull String getAuthor() {
        return String.join(", ", PLUGIN_DESC.getAuthors());
    }

    @Override
    public @NotNull String getVersion() {
        return PLUGIN_DESC.getVersion();
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        if (params.equals("in_mod_mode")) {
            if (!player.isOnline()) return "false";
            else if (player.getPlayer().hasMetadata("modmode")) return "true";
            else return "false";
        } else if (params.equals("mod_mode_star")) {
            if (!player.isOnline()) return "";
            else if (player.getPlayer().hasMetadata("modmode")) return "*";
            else return "";
        }

        return null;
    }
}
