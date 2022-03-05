/*
 * ModerationUtils - ItemManager.java
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

package com.mnewt00.moderationutils.modmode.items;

import com.google.common.collect.Maps;
import com.mnewt00.moderationutils.ModerationUtils;
import com.mnewt00.moderationutils.modmode.items.impl.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class ItemManager implements Listener {
    public ItemManager() {
        Bukkit.getPluginManager().registerEvents(this, ModerationUtils.getInstance());
    }

    Item[] ITEMS = {
            new CompassItem(),
            new BetterviewItem(),
            new InspectionItem(),
            new VanishOffItem(),
            new VanishOnItem()
    };

    public boolean isItem(ItemStack item) {
        for (Item i : ITEMS) {
            if (i.isItem(item)) return true;
        }
        return false;
    }

    public void giveItems(Player player) {
        for (Item item : ITEMS) {
            if (!item.isDontGive())
                player.getInventory().setItem(item.getSlot(), item.getItem());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST) // Allows us to cancel the message provided at a later priority.
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            for (Item item : ITEMS) {
                if (item.isItem(((Player) event.getDamager()).getInventory().getItemInMainHand())) {
                    item.onClick((Player) event.getDamager(), event.getEntity());
                    event.setCancelled(true);
                }
            }
        }
    }

    private final HashMap<UUID, Long> protectionMap = Maps.newHashMap();

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        for (Item item : ITEMS) {
            if (item.isItem(event.getItem())) {
                if (event.getAction().name().contains("BLOCK")) {
                    if (protectionMap.containsKey(event.getPlayer().getUniqueId())) {
                        if (System.currentTimeMillis() - protectionMap.get(event.getPlayer().getUniqueId()) < 250) {
                            return;
                        } else {
                            protectionMap.put(event.getPlayer().getUniqueId(), System.currentTimeMillis() + 250);
                        }
                    } else {
                        protectionMap.put(event.getPlayer().getUniqueId(), System.currentTimeMillis() + 250);
                    }
                }

                item.onInteract(event);
            }
        }
    }

    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent event) {
        for (Item item : ITEMS) {
            if (item.isItem(event.getPlayer().getInventory().getItemInMainHand())) {
                item.onInteractEntity(event);
            }
        }
    }
}
