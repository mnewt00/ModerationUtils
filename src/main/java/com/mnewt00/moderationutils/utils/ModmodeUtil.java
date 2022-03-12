/*
 * ModerationUtils - ModmodeUtil.java
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
import de.myzelyam.api.vanish.VanishAPI;
import lombok.experimental.UtilityClass;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

@UtilityClass
public class ModmodeUtil {
    public void toggle(Player player) {

        if (player.hasMetadata("modmode")) disableModmode(player);
        else enableModmode(player);

        LunarUtil.updateNametag(player);
    }

    public void enableModmode(Player player) {
        Common.tell(player, "&eMod mode is now &aenabled&e.");

        player.setMetadata("modmode", new FixedMetadataValue(ModerationUtils.getInstance(), true));
        InventoryUtils.InventorySaves.save(player);
        InventoryUtils.clearPlayer(player);

        if (!player.hasMetadata("vanished")) VanishAPI.hidePlayer(player);
        player.setGameMode(GameMode.CREATIVE);

        ModerationUtils.getInstance().getItemManager().giveItems(player);
    }

    public void disableModmode(Player player) {
        Common.tell(player, "&eMod mode is now &cdisabled&e.");

        player.removeMetadata("modmode", ModerationUtils.getInstance());
        InventoryUtils.InventorySaves.restore(player);

        if (player.hasMetadata("vanished")) VanishAPI.showPlayer(player);
        player.setGameMode(GameMode.SURVIVAL);
    }
}
