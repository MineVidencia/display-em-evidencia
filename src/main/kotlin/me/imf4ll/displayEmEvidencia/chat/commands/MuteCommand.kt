package me.imf4ll.displayEmEvidencia.chat.commands

import me.imf4ll.displayEmEvidencia.chat.models.Permissions
import me.imf4ll.displayEmEvidencia.chat.repositories.MuteRepositories
import me.imf4ll.displayEmEvidencia.services.Hooks
import me.imf4ll.displayEmEvidencia.utils.toLocalDateTime
import me.imf4ll.displayEmEvidencia.utils.toTime
import org.bukkit.Bukkit
import org.bukkit.Sound
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
      sender.sendMessage("§c§lERRO:§r §cUso correto: /mute <jogador> <tempo> <motivo>");

      return true;
    }

    val target = Bukkit.getPlayer(args[0].toString());
    val permission = Hooks.permission;

    if (target != null) {
      if (sender is ConsoleCommandSender) {
        if (!target.hasPlayedBefore()) {
          sender.sendMessage("§c§lERRO:§r §cJogador não encontrado.");

          return true;
        }

      } else if (sender is Player) {
        val groups = permission.groups;
        val targetGroup = Hooks.permission.getPrimaryGroup(target);
        val senderGroup = Hooks.permission.getPrimaryGroup(Bukkit.getPlayer(sender.name));

        if (!target.hasPlayedBefore()) {
          sender.sendMessage("§c§lERRO:§r §cJogador não encontrado.");

          return true;

          // HIERARQUIA
        } else if (sender.uniqueId == target.uniqueId || groups.indexOf(senderGroup) <= groups.indexOf(targetGroup)) {
          sender.sendMessage("§c§lERRO:§r §cVocê não pode silenciar esse jogador.");

          return true;
        }
      }

    } else {
      sender.sendMessage("§c§lERRO:§r §cJogador não encontrado.");

      return true;
    }

    val reason = args.slice(2..args.size - 1).joinToString(" ");
    val (time, validTime) = toTime(args[1].toString());

    if (!validTime) {
      sender.sendMessage("§c§lERRO:§r §cTempo inválido, use o formato: 1h20m50s");

      return true;
    }

    val muteRepositories = MuteRepositories();

    if (muteRepositories.mutePlayer(target, if (sender is Player) sender.uniqueId.toString() else "CONSOLE", reason, time)) {
      sender.sendMessage("§eO jogador '${target.name}' foi silenciado com sucesso.§r");

      Bukkit.getOnlinePlayers().filter { it.player != target }.forEach { it.sendMessage("""

§c§l---------------------------------------------§r

          §c§lUm jogador foi silenciado!§r
 
 §c· §lJogador: §r§7${ target.displayName }§r
 §c· §lMotivo: §r§7$reason§r
 §c· §lDuração: §r§7${toLocalDateTime(time).format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm"))}§r

§c§l---------------------------------------------§r
      """.trimIndent()) }

      target.playSound(target.location, Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
      target.sendMessage("""

§c§l---------------------------------------------§r

                         §c§lVocê foi silenciado!§r
                      
 §c· §lMotivo: §r§7$reason§r
 §c· §lDuração: §r§7${ toLocalDateTime(time).format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm")) }§r

§c§l---------------------------------------------§r

""".trimIndent());

    } else {
      sender.sendMessage("§c§lERRO:§r §cEsse jogador já está silenciado.");

      return true;
    }

    return true;
  }
}