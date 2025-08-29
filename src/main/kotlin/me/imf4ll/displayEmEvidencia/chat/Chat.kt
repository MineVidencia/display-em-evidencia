package me.imf4ll.displayEmEvidencia.chat

import me.imf4ll.displayEmEvidencia.chat.commands.BlockCommand
import me.imf4ll.displayEmEvidencia.chat.commands.GlobalCommand
import me.imf4ll.displayEmEvidencia.chat.commands.LocalCommand
import me.imf4ll.displayEmEvidencia.chat.commands.LockPrivateCommand
import me.imf4ll.displayEmEvidencia.chat.commands.MuteCommand
import me.imf4ll.displayEmEvidencia.chat.commands.PrivateCommand
import me.imf4ll.displayEmEvidencia.chat.commands.StaffCommand
import me.imf4ll.displayEmEvidencia.chat.commands.TeamCommand
import me.imf4ll.displayEmEvidencia.chat.commands.UnmuteCommand
import me.imf4ll.displayEmEvidencia.chat.listeners.ChatListener
import me.imf4ll.displayEmEvidencia.chat.listeners.CommandListener
import me.imf4ll.displayEmEvidencia.chat.listeners.PlayerListener
import org.bukkit.plugin.java.JavaPlugin

class Chat {
  fun run(plugin: JavaPlugin) {
    // Listeners
    plugin.server.pluginManager.registerEvents(ChatListener(plugin), plugin);
    plugin.server.pluginManager.registerEvents(CommandListener(plugin), plugin);
    plugin.server.pluginManager.registerEvents(PlayerListener(), plugin);

    // Comandos
    val privateCommand = plugin.getCommand("pm");
    privateCommand?.setExecutor(PrivateCommand());
    privateCommand?.aliases = listOf("msg");

    plugin.getCommand("g")?.setExecutor(GlobalCommand());
    plugin.getCommand("l")?.setExecutor(LocalCommand());
    plugin.getCommand("staff")?.setExecutor(StaffCommand());
    plugin.getCommand("team")?.setExecutor(TeamCommand());
    plugin.getCommand("mute")?.setExecutor(MuteCommand());
    plugin.getCommand("unmute")?.setExecutor(UnmuteCommand());
    plugin.getCommand("bloquear")?.setExecutor(BlockCommand());
    plugin.getCommand("lockpm")?.setExecutor(LockPrivateCommand());
  }
}