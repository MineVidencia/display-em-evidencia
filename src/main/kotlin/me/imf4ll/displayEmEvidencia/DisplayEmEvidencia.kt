package me.imf4ll.displayEmEvidencia

import me.imf4ll.displayEmEvidencia.chat.Chat
import me.imf4ll.displayEmEvidencia.services.Hooks
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
    saveDefaultConfig();

    Hooks.init(this);

    // Serviços
    Chat.run(this);

    logger.info("Inicializado com sucesso.");
  }

  override fun onDisable() {
    logger.info("Desabilitando, até mais!");
  }
}