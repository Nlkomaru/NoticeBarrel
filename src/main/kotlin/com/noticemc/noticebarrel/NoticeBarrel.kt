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

package com.noticemc.noticebarrel

import co.aikar.commands.PaperCommandManager
import com.noticemc.noticebarrel.commands.CommandManager
import com.noticemc.noticebarrel.event.ChestClickEvent
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class NoticeBarrel : JavaPlugin() {
    override fun onEnable() {
        // Plugin startup logic
        if (!server.pluginManager.isPluginEnabled("GriefPrevention")) {
            logger.severe("GriefPrevention not found!")
            server.pluginManager.disablePlugin(this)
            return
        }
        plugin = this
        val manager =PaperCommandManager(this)
        manager.registerCommand(CommandManager())
        Bukkit.getPluginManager().registerEvents(ChestClickEvent(),this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
    companion object{
        var plugin :NoticeBarrel? = null
    }
}