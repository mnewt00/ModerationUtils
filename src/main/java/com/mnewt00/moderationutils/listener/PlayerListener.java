/*
 * ModerationUtils - PlayerListener.java
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

package com.mnewt00.moderationutils.listener;

import com.mnewt00.moderationutils.ModerationUtils;
import com.mnewt00.moderationutils.utils.Common;
import com.mnewt00.moderationutils.utils.LunarUtil;
import com.mnewt00.moderationutils.utils.ModmodeUtil;
import de.myzelyam.api.vanish.PlayerVanishStateChangeEvent;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {
    @EventHandler
    public void onJoinEvent(PlayerJoinEvent event) {
        if (event.getPlayer().hasMetadata("modmode")) {
            LunarUtil.updateNametag(event.getPlayer());
            LunarUtil.updateNametagsForPlayer(event.getPlayer());
        }
    }

    @EventHandler
    public void vanishEvent(PlayerVanishStateChangeEvent event) {
        OfflinePlayer offlinePlayer = ModerationUtils.getInstance().getServer().getOfflinePlayer(event.getUUID());
        if (!offlinePlayer.isOnline()) return;
        assert offlinePlayer.getPlayer() != null;

        if (event.isVanishing() && !offlinePlayer.getPlayer().hasMetadata("modmode")) {
            // Basically just force the user to enter mod-mode if they enable vanish manually.
            ModmodeUtil.enableModmode(offlinePlayer.getPlayer());
        }
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        if (event.getPlayer().hasMetadata("modmode") && !event.getPlayer().hasPermission("moderationutils.modmode.drop")) {
            event.setCancelled(true);
            event.getPlayer().updateInventory();
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getPlayer().hasMetadata("modmode") && !event.getPlayer().hasPermission("moderationutils.modmode.build")) {
            event.setCancelled(true);
            Common.tell(event.getPlayer(), "&c&lYou can't break blocks while in mod-mode.");
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getPlayer().hasMetadata("modmode") && !event.getPlayer().hasPermission("moderationutils.modmode.build")) {
            event.setCancelled(true);
            Common.tell(event.getPlayer(), "&c&lYou can't place blocks while in mod-mode.");
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager().hasMetadata("modmode")) {

            event.setCancelled(true);
            event.getDamager().sendMessage(Common.legacyColor("&c&lYou can't do this while in mod-mode."));

        } else if (event.getEntity().hasMetadata("modmode")) {
            event.setCancelled(true);
        }
    }
}
