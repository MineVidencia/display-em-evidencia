package me.imf4ll.displayEmEvidencia.chat.repositories

import me.imf4ll.displayEmEvidencia.chat.models.Muted
import me.imf4ll.displayEmEvidencia.services.Database
import org.bukkit.entity.Player as PlayerEntity

class MuteRepositories {
  private val db = Database.connection;

  fun getMuted(userID: String): Muted? {
    db?.prepareStatement("SELECT * FROM displayemevidencia_mutes WHERE userID = ?;")?.use { stmt ->
      stmt.setString(1, userID);

      val result = stmt.executeQuery();

      if (result.next()) return Muted(
        userID = result.getString("userID"),
        mutedBy = result.getString("muted_by"),
        reason = result.getString("reason"),
        time = result.getLong("time"),
      );
    }

    return null;
  }

  fun mutePlayer(player: PlayerEntity, staff: String, reason: String, time: Long): Boolean {
    val isMuted = getMuted(player.uniqueId.toString());

    if (isMuted != null) return false;

    db?.prepareStatement("INSERT INTO displayemevidencia_mutes (userID, muted_by, reason, time) VALUES (?, ?, ?, ?);")?.use { stmt ->
      stmt.setString(1, player.uniqueId.toString());
      stmt.setString(2, staff);
      stmt.setString(3, reason);
      stmt.setLong(4, time);

      if (stmt.executeUpdate() == 0) return false;
    }

    return true;
  }

  fun unmutePlayer(player: PlayerEntity): Boolean {
    val isMuted = getMuted(player.uniqueId.toString());

    if (isMuted == null) return false;

    db?.prepareStatement("DELETE FROM displayemevidencia_mutes WHERE userID = ?;")?.use { stmt ->
      stmt.setString(1, player.uniqueId.toString());

      if (stmt.executeUpdate() == 0) return false;
    }

    return true;
  }
}