package me.imf4ll.displayEmEvidencia.chat.commands

import me.imf4ll.displayEmEvidencia.chat.models.Permissions
import me.imf4ll.displayEmEvidencia.services.Hooks
import me.imf4ll.displayEmEvidencia.services.PersistenceService
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player

class UnmuteCommand : CommandExecutor {
  override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String?>): Boolean {
    if (!sender.hasPermission(Permissions.Mute().permission)) return true;

    if (args.isEmpty()) {
      sender.sendMessage("§cUso correto:§r /unmute <jogador>");

      return true;
    }

    val target = Bukkit.getPlayer(args[0].toString());
    val permission = Hooks.permission;

    if (target != null) {
      if (sender is ConsoleCommandSender) {
        if (!target.hasPlayedBefore()) {
          sender.sendMessage("§cJogador não encontrado.§r");

          return true;
        }

      } else if (sender is Player) {
        val groups = permission.groups;
        val targetGroup = Hooks.permission.getPrimaryGroup(target);
        val senderGroup = Hooks.permission.getPrimaryGroup(Bukkit.getPlayer(sender.name));

        if (!target.hasPlayedBefore()) {
          sender.sendMessage("§cJogador não encontrado.§r");

          return true;

        } else if (sender.uniqueId == target.uniqueId || groups.indexOf(senderGroup) <= groups.indexOf(targetGroup)) {
          sender.sendMessage("§cVocê não pode tirar o silenciamento desse jogador.§r");

          return true;
        }
      }
    } else {
      sender.sendMessage("§cJogador não encontrado.§r");

      return true;
    }

    if (PersistenceService.unmutePlayer(target)) {
      sender.sendMessage("§eO jogador agora poderá falar.§r");
      target.sendMessage("§eSeu silenciamento foi retirado por um administrador.§r");

    } else {
      sender.sendMessage("§cEsse jogador não está silenciado.§r");

      return true;
    }

    return true;
  }
}