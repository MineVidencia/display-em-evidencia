package me.imf4ll.displayEmEvidencia.chat.services

import me.imf4ll.displayEmEvidencia.chat.Chat
import me.imf4ll.displayEmEvidencia.services.Hooks
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender

sealed class ChatFormat(val format: String, val chat: String) {
  class Global: ChatFormat("global", "g");
  class Local: ChatFormat("local", "l");
  class Staff: ChatFormat("staff", "s");
}

class FormatterService() {
  fun format(chat: ChatFormat, sender: CommandSender, message: String): String {
    val messageFormatter = Chat.config.getString("message-template.${ chat.format }")!!;
    val player = Bukkit.getOfflinePlayer(sender.name).player!!;
    val groups = Hooks.permission.getPlayerGroups(player).filter { it != "default" };

    return messageFormatter
      .replace("&", "§")
      .replaceFirst("%PLAYERNAME%", sender.name)
      .replaceFirst("%DISPLAYNAME%", player.displayName)
      .replaceFirst("%CHAT%", chat.chat)
      .replaceFirst("%WORLD%", player.world.name)
      .replaceFirst("%MESSAGE%", message)
      .replaceFirst("%PRIMARYGROUP%", groups[0].replace("&", "§"))
      .replaceFirst("%SECONDARYGROUP%", if ((groups.size) < 2) "" else groups[1].replace("&", "§"));
  }
}