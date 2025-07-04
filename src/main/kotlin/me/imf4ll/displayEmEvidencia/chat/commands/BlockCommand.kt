package me.imf4ll.displayEmEvidencia.chat.commands

import me.imf4ll.displayEmEvidencia.services.PersistenceService
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class BlockCommand : CommandExecutor {
  override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String?>): Boolean {
    if (sender !is Player) return true;

    if (args.isEmpty()) {
      sender.sendMessage("§cUso correto:§r /bloquear <jogador>");

      return true;
    }

    val target = Bukkit.getPlayer(args[0].toString());

    if (target != null && target.hasPlayedBefore()) {
      if (PersistenceService.blockPlayer(sender, target)) {
        sender.sendMessage("§eVocê agora não receberá mais mensagens privadas de ${ target.name }");

      } else {
        sender.sendMessage("§eO jogador foi desbloqueado.§r");

        return true;
      }

    } else {
      sender.sendMessage("§cJogador não encontrado.§r");

      return true;
    }

    return true;
  }
}