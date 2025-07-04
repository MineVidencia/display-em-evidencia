package me.imf4ll.displayEmEvidencia.chat.commands

import me.imf4ll.displayEmEvidencia.services.ChatFormat
import me.imf4ll.displayEmEvidencia.services.FormatterService
import me.imf4ll.displayEmEvidencia.services.PersistenceService
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class PrivateCommand : CommandExecutor {
  override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String?>): Boolean {
    if (sender !is Player) return true;

    if (args.isEmpty() || args.size < 2) {
      sender.sendMessage("§cUso correto:§r /$label <jogador> <mensagem>");

      return true;
    }

    val target = args[0];

    if (sender.name == target) {
      sender.sendMessage("§cVocê não pode enviar uma mensagem privada para você mesmo.§r");

      return true;
    }

    val message = args.slice(1..args.size - 1).joinToString(" ");
    val player = Bukkit.getOnlinePlayers().firstOrNull() { it.name == target };

    if (player != null) {
      val playerPm = PersistenceService.privateMessages.firstOrNull() { it.player == sender.uniqueId };
      val target = PersistenceService.privateMessages.firstOrNull() { it.player == player.uniqueId };

      if (playerPm != null && playerPm.pmLocked) {
        sender.sendMessage("§cVocê não pode enviar mensagens privadas enquanto está com as mensagens privadas bloqueadas.§r");

        return true;

      } else if (target != null && target.pmLocked) {
        sender.sendMessage("§cO jogador não pode receber mensagens privadas${ if (target.lockReason.isNotBlank()) ": ${ target.lockReason }" else "." }§r");

        return true;
      }

      val formattedSend = FormatterService().format(ChatFormat.PrivateSend(), player, message);
      val formattedReceive = FormatterService().format(ChatFormat.PrivateReceive(), sender, message);

      sender.sendMessage(formattedSend);

      val blockedUsers = PersistenceService.privateMessages.firstOrNull() { it.player == player.uniqueId }?.blocked;

      if (blockedUsers == null || !blockedUsers.contains(sender.uniqueId)) {
        player.sendMessage(formattedReceive);
        player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
      }

    } else {
      sender.sendMessage("§cJogador não encontrado.§r");

      return true;
    }

    return true;
  }
}