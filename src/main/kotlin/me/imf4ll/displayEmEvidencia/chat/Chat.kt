package me.imf4ll.displayEmEvidencia.chat

import me.imf4ll.displayEmEvidencia.chat.commands.GlobalCommand
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.java.JavaPlugin

object Chat {
  lateinit var config: FileConfiguration;

  fun run(plugin: JavaPlugin) {
    config = plugin.config;

    // Comandos
    plugin.getCommand("g")?.setExecutor(GlobalCommand());
    //plugin.getCommand("l")?.setExecutor();
    //plugin.getCommand("staff")?.setExecutor();
    //plugin.getCommand("mute")?.setExecutor();
    //plugin.getCommand("pm")?.setExecutor() // aliases: msg
  }
}