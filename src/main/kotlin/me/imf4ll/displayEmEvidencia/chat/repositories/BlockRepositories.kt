package me.imf4ll.displayEmEvidencia.chat.repositories

import me.imf4ll.displayEmEvidencia.chat.models.Blocked
import me.imf4ll.displayEmEvidencia.services.Database
import org.bukkit.Bukkit
import org.bukkit.entity.Player as PlayerEntity

class BlockRepositories {
  private val db = Database.connection;

  fun getBlockedUsers(userID: String): List<Blocked> {
    val blocked = mutableListOf<Blocked>();

    db?.prepareStatement("SELECT * FROM displayemevidencia_blocks WHERE blocked_by = ?;")?.use { stmt ->
      stmt.setString(1, userID);

      val result = stmt.executeQuery();

      while (result.next()) {
        blocked.add(Blocked(
          userID = result.getString("userID"),
          blockedBy = result.getString("blocked_by"),
        ));
      }
    }

    return blocked;
  }

  fun blockPlayer(player: PlayerEntity, target: PlayerEntity): Boolean {
    val blocked = getBlockedUsers(player.uniqueId.toString());
    val targetIsBlocked = blocked.firstOrNull() { it.userID == target.uniqueId.toString() }

    if (targetIsBlocked != null) {
      db?.prepareStatement("DELETE FROM displayemevidencia_blocks WHERE userID = ? AND blocked_by = ?;")?.use { stmt ->
        stmt.setString(1, target.uniqueId.toString());
        stmt.setString(2, player.uniqueId.toString());

        if (stmt.executeUpdate() == 0) Bukkit.getLogger().severe("Não foi possível desbloquear um jogador.");
      }

      return false;

    } else {
      db?.prepareStatement("INSERT INTO displayemevidencia_blocks (userID, blocked_by) VALUES (?, ?);")?.use { stmt ->
        stmt.setString(1, target.uniqueId.toString());
        stmt.setString(2, player.uniqueId.toString());

        if (stmt.executeUpdate() == 0) Bukkit.getLogger().severe("Não foi possível bloquear um jogador.");
      }

      return true;
    }
  }
}