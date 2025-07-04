package me.imf4ll.displayEmEvidencia.services

import org.bukkit.entity.Player

sealed class ChatFormat(val format: String, val chat: String) {
  class Global: ChatFormat("global", "g");
  class Local: ChatFormat("local", "l");
  class Staff: ChatFormat("staff", "s");
  class Team: ChatFormat("team", "t");
  class PrivateSend: ChatFormat("private-send", ">");
  class PrivateReceive: ChatFormat("private-receive", "<");
}

sealed class PlayerEventFormat(val format: String) {
  class Join: PlayerEventFormat("join-message");
  class Quit: PlayerEventFormat("quit-message");
}

class FormatterService() {
  fun format(chat: ChatFormat, player: Player, message: String): String {
    var messageFormatter = Hooks.config.getString("message-template.${ chat.format }")!!;
    val groups = Hooks.permission.getPlayerGroups(player).filter { it != "default" };

    if (groups.isEmpty()) {
      messageFormatter = messageFormatter.split(" ").filter { !listOf("%PRIMARYGROUP%", "%SECONDARYGROUP%").contains(it) }.joinToString(" ");

    } else if (groups.size == 1) {
      messageFormatter = messageFormatter.split(" ").filter { it != "%SECONDARYGROUP%" }.joinToString(" ");

    }

    return messageFormatter
      .replace("&", "§")
      .replaceFirst("%PLAYERNAME%", player.name)
      .replaceFirst("%DISPLAYNAME%", player.displayName)
      .replaceFirst("%CHAT%", chat.chat)
      .replaceFirst("%WORLD%", player.world.name)
      .replaceFirst("%MESSAGE%", message)
      .replaceFirst("%PRIMARYGROUP%", if (groups.isNotEmpty()) groups[0].replace("&", "§") else "")
      .replaceFirst("%SECONDARYGROUP%", if ((groups.size) < 2) "" else groups[1].replace("&", "§"));
  }

  fun formatPlayerEvent(event: PlayerEventFormat, player: Player): String {
    val messageFormatter = Hooks.config.getString(event.format)!!;

    return messageFormatter
      .replace("&", "§")
      .replaceFirst("%PLAYERNAME%", player.name)
      .replaceFirst("%DISPLAYNAME%", player.displayName);
  }
}