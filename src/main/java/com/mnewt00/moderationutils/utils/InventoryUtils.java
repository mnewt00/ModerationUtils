/*
 * ModerationUtils - InventoryUtils.java
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
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.List;

@UtilityClass
public class InventoryUtils {
    public void clearPlayer(Player player) {
        player.getInventory().setArmorContents(null);
        player.getInventory().clear();

        player.setExp(0);
        player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
    }

    @UtilityClass
    static class InventorySaves {
        private static final File INVENTORY_FOLDER = new File(ModerationUtils.getInstance().getDataFolder(), "inventories");

        @SneakyThrows
        public void save(Player player) {
            File playerFile = new File(INVENTORY_FOLDER, player.getUniqueId().toString() + ".yml");
            playerFile.getParentFile().mkdirs();
            playerFile.createNewFile();

            FileConfiguration config = YamlConfiguration.loadConfiguration(playerFile);

            config.set("inventory.armor", player.getInventory().getArmorContents());
            config.set("inventory.contents", player.getInventory().getContents());

            config.set("player.xp", player.getExp());
            config.set("player.health", player.getHealth());

            config.save(playerFile);
        }

        public void restore(Player player) {
            File playerFile = new File(INVENTORY_FOLDER, player.getUniqueId().toString() + ".yml");
            if (!playerFile.exists()) {
                return;
            }

            FileConfiguration config = YamlConfiguration.loadConfiguration(playerFile);

            player.getInventory().setArmorContents(((List<ItemStack>) config.get("inventory.armor")).toArray(new ItemStack[0]));
            player.getInventory().setContents(((List<ItemStack>) config.get("inventory.contents")).toArray(new ItemStack[0]));

            player.setExp((float) config.getDouble("player.xp"));
            player.setHealth(config.getDouble("player.health"));

            playerFile.delete();
        }
    }
}
