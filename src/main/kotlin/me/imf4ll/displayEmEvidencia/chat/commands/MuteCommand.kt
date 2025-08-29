package me.imf4ll.displayEmEvidencia.chat.commands

import me.imf4ll.displayEmEvidencia.chat.models.Permissions
import me.imf4ll.displayEmEvidencia.chat.repositories.MuteRepositories
import me.imf4ll.displayEmEvidencia.services.Hooks
import me.imf4ll.displayEmEvidencia.utils.toLocalDateTime
import me.imf4ll.displayEmEvidencia.utils.toTime
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import java.time.format.DateTimeFormatter

class MuteCommand : CommandExecutor {
  override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String?>): Boolean {
    if (!sender.hasPermission(Permissions.Mute().permission)) return true;

    if (args.isEmpty() || args.size < 3) {
      sender.sendMessage("§cUso correto:§r /mute <jogador> <tempo> <motivo>");

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
          sender.sendMessage("§cVocê não pode silenciar esse jogador.§r");

          return true;
        }
      }

    } else {
      sender.sendMessage("§cJogador não encontrado.§r");

      return true;
    }

    val reason = args.slice(2..args.size - 1).joinToString(" ");
    val (time, validTime) = toTime(args[1].toString());

    if (!validTime) {
      sender.sendMessage("§cTempo inválido, use o formato:§r 1h20m50s");

      return true;
    }

    val muteRepositories = MuteRepositories();

    if (muteRepositories.mutePlayer(target, if (sender is Player) sender.uniqueId.toString() else "CONSOLE", reason, time)) {
      sender.sendMessage("§eO jogador '${target.name}' foi silenciado com sucesso.§r");
      target.sendMessage("§cVocê foi silenciado até ${ toLocalDateTime(time).format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm")) }. §8Motivo: $reason§r");

    } else {
      sender.sendMessage("§cEsse jogador já está silenciado.§r");

      return true;
    }

    return true;
  }
}