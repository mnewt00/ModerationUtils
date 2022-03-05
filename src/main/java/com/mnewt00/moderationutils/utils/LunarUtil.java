/*
 * ModerationUtils - LunarUtil.java
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

import com.google.common.collect.ImmutableList;
import com.lunarclient.bukkitapi.LunarClientAPI;
import com.mnewt00.moderationutils.ModerationUtils;
import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

@UtilityClass
public class LunarUtil {
    public static final String[] NAMETAG_ARRAY = {
            "&7[Mod Mode]",
    };

    private List<String> applyNametag(Player player) {
        return Arrays.stream(NAMETAG_ARRAY.clone()).map(s -> Common.legacyColor(s.replace("%PLAYER%", player.getName()))).toList();
    }

    public void updateNametag(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(ModerationUtils.getInstance(), () -> {
            Bukkit.getOnlinePlayers().stream()
                    .filter(viewer -> LunarClientAPI.getInstance().isRunningLunarClient(viewer))
                    .forEach(viewer -> {
                if (player.hasMetadata("modmode"))
                    LunarClientAPI.getInstance().overrideNametag(player, applyNametag(player), viewer);
                else LunarClientAPI.getInstance().resetNametag(player, viewer);
            });
        });
    }

    public void updateNametags() {
        Bukkit.getScheduler().runTaskAsynchronously(ModerationUtils.getInstance(), () -> {
            Bukkit.getOnlinePlayers().forEach(LunarUtil::updateNametag);
        });
    }

    public void updateNametagsForPlayer(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(ModerationUtils.getInstance(), () -> {
            Bukkit.getOnlinePlayers().stream()
                    .filter(target -> LunarClientAPI.getInstance().isRunningLunarClient(target))
                    .forEach(target -> {
                if (target.hasMetadata("modmode"))
                    LunarClientAPI.getInstance().overrideNametag(target, applyNametag(player), player);
                else LunarClientAPI.getInstance().resetNametag(target, player);
            });
        });
    }
}
