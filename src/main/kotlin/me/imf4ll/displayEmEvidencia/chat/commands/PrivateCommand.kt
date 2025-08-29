package me.imf4ll.displayEmEvidencia.chat.commands

import me.imf4ll.displayEmEvidencia.chat.repositories.BlockRepositories
import me.imf4ll.displayEmEvidencia.chat.repositories.PlayerRepositories
import me.imf4ll.displayEmEvidencia.services.ChatFormat
import me.imf4ll.displayEmEvidencia.services.FormatterService
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
      val playerRepositories = PlayerRepositories();
      val playerPm = playerRepositories.getPlayer(sender.uniqueId.toString());
      val targetPm = playerRepositories.getPlayer(player.uniqueId.toString());

      if (playerPm != null && playerPm.pmLocked) {
        sender.sendMessage("§cVocê não pode enviar mensagens privadas enquanto está com as mensagens privadas bloqueadas.§r");

        return true;

      } else if (targetPm != null && targetPm.pmLocked) {
        sender.sendMessage("§cO jogador não pode receber mensagens privadas${ if (targetPm.lockReason.isNotBlank()) ": ${ targetPm.lockReason }" else "." }§r");

        return true;
      }

      val formattedSend = FormatterService().format(ChatFormat.PrivateSend(), player, message);
      val formattedReceive = FormatterService().format(ChatFormat.PrivateReceive(), sender, message);

      sender.sendMessage(formattedSend);

      val blockRepositories = BlockRepositories();
      val blockedUsers = blockRepositories.getBlockedUsers(player.uniqueId.toString());

      if (blockedUsers.firstOrNull() { it.userID == sender.uniqueId.toString() } == null) {
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