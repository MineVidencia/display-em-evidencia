package me.imf4ll.displayEmEvidencia.chat.commands

import me.imf4ll.displayEmEvidencia.chat.listeners.ChatListener
import me.imf4ll.displayEmEvidencia.chat.utils.checkMuted
import me.imf4ll.displayEmEvidencia.services.ChatFormat
import me.imf4ll.displayEmEvidencia.services.FormatterService
import me.imf4ll.displayEmEvidencia.services.Hooks
import me.imf4ll.displayEmEvidencia.services.PersistenceService
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class GlobalCommand : CommandExecutor {
  override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String?>): Boolean {
    if (sender !is Player) return true;
    if (checkMuted(sender)) return true;

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