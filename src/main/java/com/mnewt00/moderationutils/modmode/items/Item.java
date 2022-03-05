/*
 * ModerationUtils - Item.java
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

import com.mnewt00.moderationutils.utils.Common;
import com.mnewt00.moderationutils.utils.XMaterial;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;
import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public abstract class Item {
    private final String name;
    private final XMaterial material;
    private String[] lore = {};
    @Setter
    private boolean enchanted = false;
    private final int slot;
    @Setter
    private String givePermission;

    @Setter
    private boolean dontGive;

    protected void setLore(String... lore) {
        String[] newLore = new String[lore.length + 1];
        newLore[0] = "";
        System.arraycopy(lore, 0, newLore, 1, lore.length);
        this.lore = newLore;
    }

    public ItemStack getItem() {
        ItemStack stack = getMaterial().parseItem();
        ItemMeta meta = stack.getItemMeta();
        stack.setAmount(1);

        meta.setDisplayName(Common.legacyColor(name));
        if (getLore().length > 0) {
            meta.setLore(Arrays.stream(getLore()).map(Common::legacyColor).toList());
        }

        if (enchanted) {
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            stack.setItemMeta(meta);
            stack.addUnsafeEnchantment(Enchantment.LURE, 1);
        } else {
            stack.setItemMeta(meta);
        }


        return stack;
    }

    public boolean isItem(ItemStack toCompare) {
        if (toCompare == null) return false;

        ItemStack item = getItem();

        if (toCompare.equals(item)) return true;
        else if (toCompare.getItemMeta() != null && toCompare.getItemMeta().equals(item.getItemMeta()) && toCompare.getType().equals(item.getType())) return true;
        else return false;
    }

    protected void onClick(Player player, Entity clicked) { /* Meant to be overridden */ }
    protected void onInteract(PlayerInteractEvent event) { /* Meant to be overridden */ }
    protected void onInteractEntity(PlayerInteractEntityEvent event) { /* Meant to be overridden */ }
}
