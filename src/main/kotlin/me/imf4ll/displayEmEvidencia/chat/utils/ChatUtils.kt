package me.imf4ll.displayEmEvidencia.chat.utils

import me.imf4ll.displayEmEvidencia.services.PersistenceService
import me.imf4ll.displayEmEvidencia.utils.toLocalDateTime
import org.bukkit.entity.Player
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun checkMuted(player: Player): Boolean {
  val muted = PersistenceService.muted.firstOrNull() { it.player == player.uniqueId };

  if (muted != null) {
    val time = toLocalDateTime(muted.time);
    val remaining = time.compareTo(LocalDateTime.now());

    if (remaining <= 0) {
      PersistenceService.unmutePlayer(player);

    } else {
      player.sendMessage("§cVocê está silenciado até ${ time.format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm")) }.§r");

      return true;
    }
  }

  return false;
}