package me.imf4ll.displayEmEvidencia.chat

import me.imf4ll.displayEmEvidencia.chat.commands.GlobalCommand
import me.imf4ll.displayEmEvidencia.chat.commands.LocalCommand
import me.imf4ll.displayEmEvidencia.chat.commands.LockPrivateCommand
import me.imf4ll.displayEmEvidencia.chat.commands.PrivateCommand
import me.imf4ll.displayEmEvidencia.chat.commands.StaffCommand
import me.imf4ll.displayEmEvidencia.chat.commands.TeamCommand
import me.imf4ll.displayEmEvidencia.chat.listeners.ChatListener
import me.imf4ll.displayEmEvidencia.chat.listeners.CommandListener
import org.bukkit.plugin.java.JavaPlugin

object Chat {
  fun run(plugin: JavaPlugin) {
    // Listeners
    plugin.server.pluginManager.registerEvents(ChatListener(plugin), plugin);
    plugin.server.pluginManager.registerEvents(CommandListener(plugin), plugin);

    // Comandos
    val privateCommand = plugin.getCommand("pm");
    privateCommand?.setExecutor(PrivateCommand());
    privateCommand?.setAliases(listOf("msg"));

    plugin.getCommand("g")?.setExecutor(GlobalCommand());
    plugin.getCommand("l")?.setExecutor(LocalCommand());
    plugin.getCommand("staff")?.setExecutor(StaffCommand());
    plugin.getCommand("team")?.setExecutor(TeamCommand());
    //plugin.getCommand("mute")?.setExecutor();
    //plugin.getCommand("unmute")?.setExecutor();
    //plugin.getCommand("bloquear")?.setExecutor();
    plugin.getCommand("lockpm")?.setExecutor(LockPrivateCommand());
  }
}