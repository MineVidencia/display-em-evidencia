package me.imf4ll.displayEmEvidencia.chat.commands

import me.imf4ll.displayEmEvidencia.services.ChatFormat
import me.imf4ll.displayEmEvidencia.services.FormatterService
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class GlobalCommand : CommandExecutor {
  override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String?>): Boolean {
    if (sender !is Player) return true;

    if (!sender.hasPermission("displayemevidencia.chat")) {
      // lógica

      return true;
    }

    if (args.isEmpty()) {
      sender.sendMessage("§cUso correto:§r /g <mensagem>");

      return true;
    }

    val message = args.joinToString(" ");
    val formatted = FormatterService().format(ChatFormat.Global(), sender, message);

    Bukkit.getOnlinePlayers().forEach { it.sendMessage(formatted) };

    return true;
  }
}