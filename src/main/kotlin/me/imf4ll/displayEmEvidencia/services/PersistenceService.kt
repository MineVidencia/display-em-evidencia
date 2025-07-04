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
    val tempMutedFile = File(plugin.dataFolder, "muted_temp.json");
    val privateMessagesFile = File(plugin.dataFolder, "privatemessages.json");
    val tempPrivateMessagesFile = File(plugin.dataFolder, "privatemessages_temp.json");

    tempMutedFile.createNewFile()
    tempMutedFile.writeText(gson.toJson(muted));
    tempMutedFile.copyTo(mutedFile, true);
    tempMutedFile.delete();

    tempPrivateMessagesFile.createNewFile();
    tempPrivateMessagesFile.writeText(gson.toJson(privateMessages));
    tempPrivateMessagesFile.copyTo(privateMessagesFile, true);
    tempPrivateMessagesFile.delete();
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
    val blockedUsers = privateMessages.firstOrNull() { it.player == player.uniqueId }?.blocked;

    if (blockedUsers != null) {
      if (blockedUsers.contains(target.uniqueId)) {
        blockedUsers.removeIf { it == target.uniqueId };

        return false;

      } else blockedUsers.add(target.uniqueId);

    } else {
      privateMessages.add(PrivateMessages(
        player.uniqueId,
        false,
        "",
        mutableListOf(target.uniqueId),
      ));
    }

    return true;
  }

  fun mutePlayer(player: Player, time: Long, reason: String): Boolean {
    if (muted.firstOrNull() { it.player == player.uniqueId } != null) return false;

    muted.add(Muted(
        player.uniqueId,
        time,
        reason,
      )
    );

    return true;
  }

  fun unmutePlayer(player: Player): Boolean {
    val isMuted = muted.firstOrNull() { it.player == player.uniqueId };

    if (isMuted != null) {
      muted.removeIf { it.player == player.uniqueId };

    } else return false;

    return true;
  }
}