package me.imf4ll.displayEmEvidencia.chat.commands

import me.imf4ll.displayEmEvidencia.chat.models.Permissions
import me.imf4ll.displayEmEvidencia.chat.repositories.MuteRepositories
import me.imf4ll.displayEmEvidencia.services.Hooks
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
      sender.sendMessage("§c§lERRO:§r §cUso correto: /unmute <jogador>");

      return true;
    }

    val target = Bukkit.getPlayer(args[0].toString());
    val permission = Hooks.permission;

    if (target != null) {
      if (sender is ConsoleCommandSender) {
        if (!target.hasPlayedBefore()) {
          sender.sendMessage("§c§lERRO:§r §cJogador não encontrado.");

          return true;
        }

      } else if (sender is Player) {
        val groups = permission.groups;
        val targetGroup = Hooks.permission.getPrimaryGroup(target);
        val senderGroup = Hooks.permission.getPrimaryGroup(Bukkit.getPlayer(sender.name));

        if (!target.hasPlayedBefore()) {
          sender.sendMessage("§c§lERRO:§r §cJogador não encontrado.");

          return true;

          // HIERARQUIA
        } else if (sender.uniqueId == target.uniqueId || groups.indexOf(senderGroup) <= groups.indexOf(targetGroup)) {
          sender.sendMessage("§c§lERRO:§r §cVocê não pode tirar o silenciamento desse jogador.");

          return true;
        }
      }
    } else {
      sender.sendMessage("§c§lERRO:§r §cJogador não encontrado.");

      return true;
    }

    val muteRepositories = MuteRepositories();

    if (muteRepositories.unmutePlayer(target)) {
      sender.sendMessage("§eO jogador agora poderá falar.");
      target.sendMessage("§eSeu silenciamento foi retirado por um administrador.");

    } else {
      sender.sendMessage("§c§lERRO:§r §cEsse jogador não está silenciado.");

      return true;
    }

    return true;
  }
}