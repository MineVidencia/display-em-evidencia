package me.imf4ll.displayEmEvidencia

import me.imf4ll.displayEmEvidencia.services.VaultService
import org.bukkit.plugin.java.JavaPlugin

class DisplayEmEvidencia : JavaPlugin() {
  override fun onLoad() {
    if (server.pluginManager.getPlugin("Vault") == null) {
      logger.severe("O 'Vault' parece não estar instalado, desabilitando...");
      server.pluginManager.disablePlugin(this);

      return;
    }
  }

  override fun onEnable() {
    VaultService.init(this);

    logger.info("Inicializado com sucesso.");
  }

  override fun onDisable() {
    logger.info("Desabilitando, até mais!");
  }
}