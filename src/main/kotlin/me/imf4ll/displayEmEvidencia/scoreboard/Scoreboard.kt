package me.imf4ll.displayEmEvidencia.scoreboard

import me.imf4ll.displayEmEvidencia.scoreboard.listeners.SidebarListen
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import net.milkbowl.vault.economy.Economy
import org.bukkit.plugin.Plugin

class Scoreboard() {
  fun run(plugin: Plugin) {
    plugin.server.pluginManager.registerEvents(SidebarListen(plugin), plugin);
  }
}

class SScoreboard : JavaPlugin()  {



  companion object {
    var econ: Economy? = null
      private set
  }

  override fun onEnable() {

    if (!setup()) {
      logger.severe("Vault não foi iniciado ou não encontrado. Verifique a existência do vault")
      return
    }

    Bukkit.getPluginManager().registerEvents(SidebarListen(this), this)



  }

  private fun setup(): Boolean {
    if (server.pluginManager.getPlugin("Vault") == null) {
      return false
    }

    val response =  server.servicesManager.getRegistration(Economy::class.java) ?: return false
    econ = response.provider
    return econ != null
  }


}

