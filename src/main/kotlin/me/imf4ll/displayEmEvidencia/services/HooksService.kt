package me.imf4ll.displayEmEvidencia.services

import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import net.milkbowl.vault.permission.Permission
import org.bukkit.configuration.file.FileConfiguration

object Hooks {
  lateinit var economy: Economy;
  lateinit var permission: Permission;
  lateinit var config: FileConfiguration;

  fun init(plugin: Plugin) {
    val vaultEconomy = Bukkit.getServicesManager().getRegistration(Economy::class.java);
    val vaultPermission = Bukkit.getServicesManager().getRegistration(Permission::class.java);

    config = plugin.config;

    if (vaultEconomy != null && vaultPermission != null) {
      economy = vaultEconomy.provider;
      permission = vaultPermission.provider;

    } else {
      plugin.logger.severe("Não foi encontrado um plugin de economia/permissão compatível, desabilitando...");
      plugin.server.pluginManager.disablePlugin(plugin);

      return;
    }
  }
}