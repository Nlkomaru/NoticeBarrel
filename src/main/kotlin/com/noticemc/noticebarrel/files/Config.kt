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

package com.noticemc.noticebarrel.files

import com.noticemc.noticebarrel.NoticeBarrel.Companion.plugin
import org.spongepowered.configurate.CommentedConfigurationNode
import org.spongepowered.configurate.hocon.HoconConfigurationLoader
import org.spongepowered.configurate.loader.ConfigurationLoader
import java.io.File
import java.io.IOException
import java.nio.file.Files

object Config {
    var config: CommentedConfigurationNode? = null

    fun load() {
        val file = File(plugin.dataFolder, "config.conf")
        if (!file.parentFile.exists()) {
            file.parentFile.mkdirs()
        }
        if (!file.exists()) {
            val input = this.javaClass.getResourceAsStream("/config.conf")!!
            Files.copy(input, file.toPath())
        }
        val configManager: ConfigurationLoader<CommentedConfigurationNode> = HoconConfigurationLoader.builder()
            .path(file.toPath()).build()
        try {
            config = configManager.load()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
}