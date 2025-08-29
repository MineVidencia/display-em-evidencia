package me.imf4ll.displayEmEvidencia.services

import org.bukkit.configuration.Configuration
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

object Database {
  var connection: Connection? = null;

  fun connect(config: Configuration) {
    try {
      val dbms = config.getString("database.dbms");

      when (dbms) {
        "sqlite" -> connection = DriverManager.getConnection("jdbc:sqlite:plugins/DisplayEmEvidencia/database.db");
        else -> {
          val database = config.getString("database.database");
          val host = config.getString("database.host");
          val port = config.getString("database.port");
          val user = config.getString("database.username");
          val pass = config.getString("database.password");

          connection = DriverManager.getConnection("jdbc:mysql://${ java.net.URLEncoder.encode(user, "utf-8") }:${ java.net.URLEncoder.encode(pass, "utf-8") }@${ host }:${ port }/${ database }");
        }
      }

      connection?.createStatement()?.use { it.execute("""
        CREATE TABLE IF NOT EXISTS displayemevidencia_players (
          userID VARCHAR(36) NOT NULL PRIMARY KEY UNIQUE,
          pm_locked BOOLEAN,
          lock_reason TEXT NULL
        );
       """.trimIndent());
      }

      connection?.createStatement()?.use { it.execute("""
        CREATE TABLE IF NOT EXISTS displayemevidencia_blocks (
          userID VARCHAR(36) NOT NULL PRIMARY KEY,
          blocked_by VARCHAR(36) NOT NULL,
          FOREIGN KEY (blocked_by) REFERENCES displayemevidencia_players(userID)
        );
        """.trimIndent());
      }

      connection?.createStatement()?.use { it.execute("""
        CREATE TABLE IF NOT EXISTS displayemevidencia_mutes (
          userID VARCHAR(36) NOT NULL PRIMARY KEY UNIQUE,
          muted_by VARCHAR(36) NOT NULL,
          reason VARCHAR(255) NOT NULL,
          time BIGINT NOT NULL,
          FOREIGN KEY (userID) REFERENCES displayemevidencia_players(userID)
        );
        """.trimIndent());
      }

    } catch (e: SQLException) { e.printStackTrace() }
  }
}