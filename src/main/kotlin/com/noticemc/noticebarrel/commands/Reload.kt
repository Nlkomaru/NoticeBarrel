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
import com.noticemc.noticebarrel.files.Config
import org.bukkit.command.CommandSender

@CommandMethod("noticebarrel|nb")
class Reload {
    @CommandPermission("NoticeBarrel.reload")
    @CommandMethod("reload")
    fun reload(sender: CommandSender) {
        sender.sendMessage("NoticeBarrelを再読み込みしました")
        Config.load()
    }
}