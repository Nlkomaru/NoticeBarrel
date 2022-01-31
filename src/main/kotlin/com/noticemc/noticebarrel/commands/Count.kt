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

import cloud.commandframework.annotations.Argument
import cloud.commandframework.annotations.CommandMethod
import cloud.commandframework.annotations.CommandPermission
import cloud.commandframework.annotations.specifier.Range
import com.github.shynixn.mccoroutine.launch
import com.noticemc.noticebarrel.NoticeBarrel
import kotlinx.coroutines.delay
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@CommandMethod("noticebarrel|nb")
class Count {
    @CommandPermission("NoticeBarrel.count")
    @CommandMethod("count <radius> <material>")
    fun count(sender: CommandSender, @Argument("radius") @Range(min = "1", max = "256") radius: Int, @Argument("material") material: Material) {
        NoticeBarrel.plugin.launch {
            val player: Player = sender as Player
            val count = counter(player, radius, material)

            player.sendMessage("$count 個の$material が見つかりました")
        }
    }

    private suspend fun counter(player: Player, radius: Int, material: Material): Long {
        var count = 0L

        player.sendMessage("検索中...")
        val x = player.location.blockX
        val z = player.location.blockZ

        var temp = 0
        for (i in -radius..radius) {
                for (k in -radius..radius) {
                    for (j in -64..player.world.getHighestBlockYAt(x + i, z + k)) {
                        val loc = Location(player.world, (x + i).toDouble(), j.toDouble(), (z + k).toDouble())
                        if (temp % 5000000 == 0) {
                            delay(100)
                            player.sendMessage("$temp 個のブロックをスキャンしました")
                        }
                        if (loc.block.type == material) {
                            count++
                        }
                        temp++
                    }
                }
        }
        return count
    }

}