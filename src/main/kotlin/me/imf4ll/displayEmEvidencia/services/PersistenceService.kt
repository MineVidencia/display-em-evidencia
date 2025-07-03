package me.imf4ll.displayEmEvidencia.services

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import me.imf4ll.displayEmEvidencia.chat.models.Muted
import me.imf4ll.displayEmEvidencia.chat.models.PrivateMessages
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import java.io.File

object PersistenceService {
  lateinit var plugin: Plugin;
  lateinit var muted: MutableList<Muted>;
  lateinit var privateMessages: MutableList<PrivateMessages>;
  val gson: Gson = GsonBuilder().setPrettyPrinting().create();

  fun init(pl: Plugin) {
    plugin = pl;

    val mutedFile = File(plugin.dataFolder, "muted.json");
    val privateMessagesFile = File(plugin.dataFolder, "privatemessages.json");

    if (!mutedFile.exists() || !privateMessagesFile.exists()) {
      mutedFile.createNewFile();
      mutedFile.writeText("[]");

      privateMessagesFile.createNewFile();
      privateMessagesFile.writeText("[]");
    }

    val mutedType = object : TypeToken<List<Muted>>() {}.type;
    muted = gson.fromJson(mutedFile.readText(), mutedType);

    val privateMessagesType = object : TypeToken<List<PrivateMessages>>() {}.type;
    privateMessages = gson.fromJson(privateMessagesFile.readText(), privateMessagesType);

    Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, Runnable { save() }, 20L * 30, 20L * 30);
  }

  fun save() {
    val mutedFile = File(plugin.dataFolder, "muted.json");
    val privateMessagesFile = File(plugin.dataFolder, "privatemessages.json");

    mutedFile.writeText(gson.toJson(muted));
    privateMessagesFile.writeText(gson.toJson(privateMessages));
  }

  fun lockPrivate(player: Player, reason: String): Boolean {
    if (privateMessages.none { it.player == player.uniqueId }) {
      privateMessages.add(PrivateMessages(
        player.uniqueId,
        true,
        reason,
        mutableListOf(),
      ));

      return true;

    } else {
      privateMessages.forEach { pv ->
        if (pv.player == player.uniqueId) {
          pv.pmLocked = !pv.pmLocked;
          pv.lockReason = reason;

          return pv.pmLocked;
        }
      }

      return privateMessages.first { it.player == player.uniqueId }.pmLocked;
    }
  }

  fun blockPlayer(player: Player, target: Player): Boolean {
    return false;
  }
}