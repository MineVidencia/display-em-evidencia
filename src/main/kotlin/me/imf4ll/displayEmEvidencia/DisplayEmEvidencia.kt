package me.imf4ll.displayEmEvidencia

import me.imf4ll.displayEmEvidencia.chat.Chat
import me.imf4ll.displayEmEvidencia.services.CooldownService
import me.imf4ll.displayEmEvidencia.services.Hooks
import me.imf4ll.displayEmEvidencia.services.PersistenceService
import org.bukkit.plugin.java.JavaPlugin
import java.util.UUID

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

    val chatCooldown = mutableListOf<UUID>();
    val commandCooldown = mutableListOf<UUID>();

    // Serviços
    PersistenceService.init(this);
    Hooks.init(this);
    CooldownService.init(this, chatCooldown, commandCooldown);

    // Pacotes
    Chat.run(this);

    logger.info("Inicializado com sucesso.");
  }

  override fun onDisable() {
    PersistenceService.save();

    logger.info("Desabilitando, até mais!");
  }
}