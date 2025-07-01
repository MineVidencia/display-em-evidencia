package me.imf4ll.displayEmEvidencia.services

import net.milkbowl.vault.economy.Economy
import net.milkbowl.vault.permission.Permission
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin

object VaultService {
  lateinit var economy: Economy;
  lateinit var permission: Permission;

  fun init(plugin: Plugin) {
    val vaultEconomy = Bukkit.getServicesManager().getRegistration(Economy::class.java);
    val vaultPermission = Bukkit.getServicesManager().getRegistration(Permission::class.java);

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