package me.imf4ll.displayEmEvidencia.chat.commands

import me.imf4ll.displayEmEvidencia.chat.models.Permissions
import me.imf4ll.displayEmEvidencia.services.ChatFormat
import me.imf4ll.displayEmEvidencia.services.FormatterService
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class StaffCommand : CommandExecutor {
  override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String?>): Boolean {
    if (sender !is Player || !sender.hasPermission(Permissions.StaffChat().permission)) return true;

    if (args.isEmpty()) {
      sender.sendMessage("§c§lERRO:§r §cUso correto: /staff <mensagem>");

      return true;
    }

    val message = args.joinToString(" ");
    val formatted = FormatterService().format(ChatFormat.Staff(), sender, message);

    val players = Bukkit.getOnlinePlayers().filter { it.hasPermission(Permissions.StaffChat().permission) || it.isOp };

    players.forEach { p -> p.sendMessage(formatted) };

    return true;
  }
}