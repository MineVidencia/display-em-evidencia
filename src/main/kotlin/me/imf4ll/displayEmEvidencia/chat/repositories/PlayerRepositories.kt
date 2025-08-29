package me.imf4ll.displayEmEvidencia.chat.repositories

import me.imf4ll.displayEmEvidencia.chat.models.Player
import me.imf4ll.displayEmEvidencia.services.Database
import org.bukkit.Bukkit
import org.bukkit.entity.Player as PlayerEntity

class PlayerRepositories {
  private val db = Database.connection;

  fun createPlayer(userID: String): Boolean {
    db?.prepareStatement("INSERT INTO displayemevidencia_players (userID, pm_locked, lock_reason) VALUES (?, FALSE, \"\");")?.use { stmt ->
      stmt.setString(1, userID);

      if (stmt.executeUpdate() != 0) return true;
    }

    return false;
  }

  fun getPlayer(userID: String): Player? {
    db?.prepareStatement("SELECT * FROM displayemevidencia_players WHERE userID = ?;")?.use { stmt ->
      stmt.setString(1, userID);

      val result = stmt.executeQuery();

      if (result.next()) return Player(
        userID = result.getString("userID"),
        pmLocked = result.getBoolean("pm_locked"),
        lockReason = result.getString("lock_reason") ?: "",
      );
    }

    return null;
  }

  fun lockPrivate(player: PlayerEntity, reason: String = ""): Boolean {
    db?.prepareStatement("UPDATE displayemevidencia_players SET pm_locked = ?, lock_reason = ? WHERE userID = ?;")?.use { stmt ->
      val isLocked = getPlayer(player.uniqueId.toString())?.pmLocked!!;

      stmt.setBoolean(1, !isLocked);
      stmt.setString(2, reason);
      stmt.setString(3, player.uniqueId.toString());

      if (stmt.executeUpdate() == 0) {
        Bukkit.getLogger().severe("Não foi possível bloquear o privado de um jogador.");

        return false;
      }

      if (!isLocked) return true;
    }

    return false;
  }
}