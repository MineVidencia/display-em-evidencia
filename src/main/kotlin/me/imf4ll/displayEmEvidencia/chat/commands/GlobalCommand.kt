package me.imf4ll.displayEmEvidencia.chat.commands

import me.imf4ll.displayEmEvidencia.chat.services.ChatFormat
import me.imf4ll.displayEmEvidencia.chat.services.FormatterService
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class GlobalCommand : CommandExecutor {
  override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String?>): Boolean {
    if (sender !is Player) return true;

    if (args.isEmpty()) {
      sender.sendMessage("§cUso correto: /g <mensagem>§r");

      return false;
    }

    val message = args.joinToString(" ");
    val formatted = FormatterService().format(ChatFormat.Global(), sender, message);

    Bukkit.getOnlinePlayers().forEach { it.sendMessage(formatted) };

    return true;
  }
}