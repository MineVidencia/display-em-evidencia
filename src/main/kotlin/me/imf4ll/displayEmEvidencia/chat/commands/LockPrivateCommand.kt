package me.imf4ll.displayEmEvidencia.chat.commands

import me.imf4ll.displayEmEvidencia.chat.repositories.PlayerRepositories
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class LockPrivateCommand : CommandExecutor {
  override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String?>): Boolean {
    if (sender !is Player) return true;

    val reason = args.joinToString(" ");

    if (reason.count() > 50) {
      sender.sendMessage("§c§lERRO:§r §cO motivo pode possuir apenas até 50 caracteres.");

      return true;
    }

    val playerRepositories = PlayerRepositories();

    if (playerRepositories.lockPrivate(sender, reason)) {
      sender.sendMessage("§eVocê não receberá mais mensagens privadas.");

    } else {
      sender.sendMessage("§eVocê agora receberá mensagens privadas.");

    }

    return true;
  }
}