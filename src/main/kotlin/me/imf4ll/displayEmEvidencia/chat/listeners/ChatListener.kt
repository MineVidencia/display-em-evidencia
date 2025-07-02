package me.imf4ll.displayEmEvidencia.chat.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

class ChatListener : Listener {
  @EventHandler
  fun onChat(event: AsyncPlayerChatEvent) {
    val player = event.player;
    val message = event.message;
  }
}