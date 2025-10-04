package me.imf4ll.displayEmEvidencia.chat.listeners

import me.imf4ll.displayEmEvidencia.services.CooldownService
import me.imf4ll.displayEmEvidencia.services.Hooks
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.plugin.Plugin

class CommandListener(val plugin: Plugin) : Listener {
  val commands = mutableListOf<String>();

  init {
    for ((command, props) in plugin.description.commands) {
      commands.add(command);

      val aliases = props["aliases"];

      when (aliases) {
        is String -> commands.add(aliases)
        is List<*> -> aliases.forEach { commands.add(it.toString().lowercase()) }
      }
    }
  }

  @EventHandler
  fun onCommand(e: PlayerCommandPreprocessEvent) {
    val player = e.player;
    val cooldowned = CooldownService.commandCooldown;

    if (!player.isOp) {
      if (cooldowned.contains(player.uniqueId)) {
        e.isCancelled = true;

        player.sendMessage("§c§lERRO:§r §cEspere um momento para enviar outro comando.");

      } else {
        val cooldown = Hooks.config.getInt("command-cooldown");

        CooldownService.addCommandCooldown(player.uniqueId, cooldown);
      }
    }

    return;
  }
}