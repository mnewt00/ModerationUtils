/*
 * ModerationUtils - InspectionItem.java
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

package com.mnewt00.moderationutils.modmode.items.impl;

import com.mnewt00.moderationutils.modmode.items.Item;
import com.mnewt00.moderationutils.utils.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class InspectionItem extends Item {
    public InspectionItem() {
        super("&eInspection", XMaterial.BOOK, 8);
    }

    @Override
    protected void onInteractEntity(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof Player) {
            Bukkit.dispatchCommand(event.getPlayer(), String.format("invsee %s", event.getRightClicked().getName()));
        }
    }

    @Override
    protected void onClick(Player player, Entity clicked) {
        if (clicked instanceof Player) {
            Bukkit.dispatchCommand(player, String.format("invsee %s", clicked.getName()));
        }
    }
}
