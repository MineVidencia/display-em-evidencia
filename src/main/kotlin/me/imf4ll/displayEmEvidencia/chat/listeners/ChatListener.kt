package me.imf4ll.displayEmEvidencia.chat.listeners

import me.imf4ll.displayEmEvidencia.chat.utils.checkMuted
import me.imf4ll.displayEmEvidencia.services.ChatFormat
import me.imf4ll.displayEmEvidencia.services.CooldownService
import me.imf4ll.displayEmEvidencia.services.FormatterService
import me.imf4ll.displayEmEvidencia.services.Hooks
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.plugin.Plugin

class ChatListener(val plugin: Plugin) : Listener {
  @EventHandler
  fun onChat(event: AsyncPlayerChatEvent) {
    event.isCancelled = true;

    if (checkMuted(event.player)) return;

    val player = event.player;
    val cooldowned = CooldownService.chatCooldown;

    if (!player.isOp) {
      if (cooldowned.contains(player.uniqueId)) {
        player.sendMessage("§cEspere um momento para enviar outra mensagem.§r");

        event.isCancelled = true;

        return;

      } else {
        val cooldown = Hooks.config.getInt("message-cooldown");

        CooldownService.addChatCooldown(player.uniqueId, cooldown);
      }
    }

    val message = event.message;
    val radius = Hooks.config.getDouble("local-radius");
    val formatted = FormatterService().format(ChatFormat.Local(), player, message);

    val players = Bukkit.getOnlinePlayers().filter { it.world == player.world && it.location.distance(player.location) <= (if (radius != 0.0) radius else 50.0) }

    if (players.size > 1) {
      players.forEach { p -> p.sendMessage(formatted) };

    } else {
      player.sendMessage(formatted);
      player.sendMessage("§cNão há ninguém por perto.§r");

    }

    plugin.logger.info("${ player.name }: $message");
  }
}