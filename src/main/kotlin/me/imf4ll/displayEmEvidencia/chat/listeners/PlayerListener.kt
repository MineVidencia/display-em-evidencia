package me.imf4ll.displayEmEvidencia.chat.listeners

import me.imf4ll.displayEmEvidencia.services.FormatterService
import me.imf4ll.displayEmEvidencia.services.PlayerEventFormat
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class PlayerListener : Listener {
  @EventHandler
  fun onJoin(event: PlayerJoinEvent) { event.joinMessage = FormatterService().formatPlayerEvent(PlayerEventFormat.Join(), event.player) };

  @EventHandler
  fun onQuit(event: PlayerQuitEvent) { event.quitMessage = FormatterService().formatPlayerEvent(PlayerEventFormat.Quit(), event.player) };
}