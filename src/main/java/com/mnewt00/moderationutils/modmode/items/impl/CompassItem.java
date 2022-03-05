/*
 * ModerationUtils - CompassItem.java
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

// Just a WorldEdit compass, for teleporting yk
public class CompassItem extends Item {
    public CompassItem() {
        super("&cCompass", XMaterial.COMPASS, 0);

        setLore(
                "&c&lLeft Click &7to jump to the block you are looking at.",
                "&6&lRight Click &7to pass through walls and doors."
        );
        setGivePermission("worldedit.navigation.jumpto.tool");
    }
}
