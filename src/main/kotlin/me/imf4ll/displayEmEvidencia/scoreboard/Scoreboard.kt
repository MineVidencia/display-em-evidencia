package me.imf4ll.displayEmEvidencia.scoreboard

import me.imf4ll.displayEmEvidencia.scoreboard.listeners.SidebarListen
import org.bukkit.plugin.Plugin

class Scoreboard() {
  fun run(plugin: Plugin) = plugin.server.pluginManager.registerEvents(SidebarListen(plugin), plugin);
}
