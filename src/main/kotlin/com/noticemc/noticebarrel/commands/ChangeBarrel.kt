/*
 * Copyright © 2021 Nikomaru
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.noticemc.noticebarrel.commands

import cloud.commandframework.annotations.CommandMethod
import cloud.commandframework.annotations.CommandPermission
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

@CommandMethod("noticebarrel|nb")
class ChangeBarrel {

    @CommandPermission("NoticeBarrel.changeBarrel")
    @CommandMethod("change")
    fun enableChangeBarrel(sender: CommandSender) {
        val player: Player = sender as Player
        if (canChangeBarrel[player.uniqueId] == null || canChangeBarrel[player.uniqueId] == false) {
            canChangeBarrel[player.uniqueId] = true
            player.sendMessage("ChangeBarrelモードが有効です")
            player.sendMessage("$canChangeBarrel")
        } else {
            canChangeBarrel[player.uniqueId] = false
            player.sendMessage("ChangeBarrelモードを無効にしました")
        }
    }

    companion object {
        var canChangeBarrel: HashMap<UUID, Boolean> = HashMap()
    }
}