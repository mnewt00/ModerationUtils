/*
 * ModerationUtils - VanishOnItem.java
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
import de.myzelyam.api.vanish.VanishAPI;
import org.bukkit.event.player.PlayerInteractEvent;

public class VanishOnItem extends Item {
    public VanishOnItem() {
        super("&aBecome Hidden", XMaterial.LIME_DYE, 1);

        setEnchanted(true);
        setDontGive(true);
    }

    @Override
    protected void onInteract(PlayerInteractEvent event) {
        VanishAPI.hidePlayer(event.getPlayer());
        event.getPlayer().getInventory().setItemInMainHand(new VanishOffItem().getItem());
    }
}
