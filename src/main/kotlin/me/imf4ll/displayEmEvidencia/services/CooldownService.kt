package me.imf4ll.displayEmEvidencia.services

import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import java.util.UUID

object CooldownService {
  lateinit var plugin: Plugin;
  lateinit var chatCooldown: MutableList<UUID>;
  lateinit var commandCooldown: MutableList<UUID>;

  fun init(pl: Plugin, chatc: MutableList<UUID>, cmdc: MutableList<UUID>) {
    plugin = pl;
    chatCooldown = chatc;
    commandCooldown = cmdc;
  }

  fun addChatCooldown(uuid: UUID, time: Int) {
    chatCooldown.add(uuid);

    Bukkit.getScheduler().runTaskLater(plugin, Runnable { chatCooldown.remove(uuid) }, 20L * time);
  }

  fun addCommandCooldown(uuid: UUID, time: Int) {
    commandCooldown.add(uuid);

    Bukkit.getScheduler().runTaskLater(plugin, Runnable { commandCooldown.remove(uuid) }, 20L * time);
  }
}