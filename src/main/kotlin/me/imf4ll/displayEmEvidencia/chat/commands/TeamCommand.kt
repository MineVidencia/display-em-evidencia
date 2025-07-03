package me.imf4ll.displayEmEvidencia.chat.commands

import me.imf4ll.displayEmEvidencia.services.ChatFormat
import me.imf4ll.displayEmEvidencia.services.FormatterService
import me.imf4ll.displayEmEvidencia.services.Hooks
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class TeamCommand : CommandExecutor {
  override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String?>): Boolean {
    if (sender !is Player || !sender.hasPermission("displayemevidencia.staff")) return true;

    if (args.isEmpty()) {
      sender.sendMessage("§cUso correto:§r /team <mensagem>");

      return true;
    }

    val message = args.joinToString(" ");
    val formatted = FormatterService().format(ChatFormat.Team(), sender, message);
    val permission = Hooks.permission;

    val players = Bukkit.getOnlinePlayers().filter {
      it.hasPermission("displayemevidencia.staff") && permission.getPrimaryGroup(it.player!!).contains(permission.getPrimaryGroup(sender.player));

    }

    players.forEach { it.sendMessage(formatted) };

    return true;
  }
}