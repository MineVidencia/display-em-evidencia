package me.imf4ll.displayEmEvidencia

import me.imf4ll.displayEmEvidencia.chat.Chat
import me.imf4ll.displayEmEvidencia.scoreboard.Scoreboard
import me.imf4ll.displayEmEvidencia.services.CooldownService
import me.imf4ll.displayEmEvidencia.services.Database
import me.imf4ll.displayEmEvidencia.services.Hooks
import org.bukkit.plugin.java.JavaPlugin
import java.util.UUID

class DisplayEmEvidencia : JavaPlugin() {
  override fun onLoad() {
    // Banco de dados
    Database.connect(this.config);

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
    Hooks.init(this);
    CooldownService.init(this, chatCooldown, commandCooldown);

    // Pacotes
    Chat().run(this);
    Scoreboard().run(this);

    logger.info("Inicializado com sucesso.");
  }

  override fun onDisable() = logger.info("Desabilitando, até mais!");
}