/*
 * ModerationUtils - ModmodeCommand.java
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

package com.mnewt00.moderationutils.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.mnewt00.moderationutils.utils.Common;
import com.mnewt00.moderationutils.utils.ModmodeUtil;
import org.bukkit.entity.Player;

@CommandAlias("modmode|silent|staffmode|v|vanish")
public class ModmodeCommand extends BaseCommand {
    @Default
    @Syntax("[player]")
    @CommandPermission("moderationutils.modmode.self")
    public void onCommand(Player sender, @Optional @Flags("other") Player player) {
        if (player != null && !sender.hasPermission("moderationutils.modmode.other")
        ) {
            Common.tell(sender, "&cYou do not have permission.");
        } else if (player != null && !player.hasPermission("moderationutils.modmode.self")) {
            Common.tell(sender, "&cTarget does not have permission.");
        }

        ModmodeUtil.toggle(player == null ? sender : player);
    }
}
