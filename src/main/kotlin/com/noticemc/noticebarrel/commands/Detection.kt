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
import com.noticemc.noticebarrel.NoticeBarrel.Companion.plugin
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.monster.Shulker
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.craftbukkit.v1_18_R1.CraftWorld
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer
import org.bukkit.entity.Player

@CommandMethod("noticebarrel|nb")
class Detection {

    @CommandPermission("NoticeBarrel.detection")
    @CommandMethod("detection <radius> <time> <material> ")
    fun detection(
        sender: CommandSender,
        @Argument("radius") @Range(min = "1", max = "256") radius: Int,
        @Argument("time") @Range(min = "-1", max = "300") time: Int,
        @Argument("material") material: Material
    ) {
        plugin.launch {
            val player: Player = sender as Player
            val count = display(player, radius, material, time)

            if (count == null) {
                player.sendMessage("検出した量が多いため処理を中断しました(over 5000)")
                return@launch
            }
            player.sendMessage("$count 個の$material を表示しました")
        }
    }



    private suspend fun summonGlowingShulker(loc: Location, player: Player, time: Int, world: CraftWorld) {
        val shulker = Shulker(EntityType.SHULKER, world.handle)

        shulker.setGlowingTag(true)
        shulker.isInvisible = true
        shulker.isNoGravity = true
        shulker.setPos(loc.x + 0.5, loc.y + 0.125, loc.z + 0.5)

        val packetSpawn = ClientboundAddEntityPacket(shulker)
        val packetMetadata = ClientboundSetEntityDataPacket(shulker.id, shulker.entityData, true)
        val playerConnection = (player as CraftPlayer).handle.connection.connection
        playerConnection.send(packetSpawn)
        playerConnection.send(packetMetadata)
        if (time > 0) {
            delay(time * 1000.toLong())
            val packetDestroy = ClientboundRemoveEntitiesPacket(shulker.id)
            playerConnection.send(packetDestroy)
        }
    }

    private suspend fun display(player: Player, radius: Int, material: Material, time: Int): Int? {

        var count = 0

        player.sendMessage("検索中...")
        val x = player.location.blockX
        val z = player.location.blockZ
        val item: ArrayList<Location> = ArrayList()

        var temp = 0
        for (i in -radius..radius) {
            for (j in -64..319) {
                for (k in -radius..radius) {
                    val loc = Location(player.world, (x + i).toDouble(), j.toDouble(), (z + k).toDouble())
                    if (temp % 1000000 == 0) {
                        delay(100)
                    }
                    if (loc.block.type == material) {
                        item.add(loc)
                        count++
                    }
                    if (item.size >= 5000) {
                        return null
                    }
                    temp++
                }
            }
        }
        player.sendMessage("検索完了")

        plugin.launch {
            item.map {
                async {
                    summonGlowingShulker(it, player, time, it.world as CraftWorld)
                }
            }
        }
        return count
    }

}
