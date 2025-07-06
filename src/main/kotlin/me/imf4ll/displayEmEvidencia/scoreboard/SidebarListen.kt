package me.imf4ll.displayEmEvidencia.scoreboard

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitTask
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Scoreboard
import org.bukkit.Statistic
import java.text.NumberFormat
import java.util.Locale
import java.util.UUID

class SidebarListen(private val plugin: JavaPlugin) : Listener {



    private val updateTasks = mutableMapOf<UUID, BukkitTask>()
    private val numberFormatter: NumberFormat = NumberFormat.getInstance(Locale.US)

    @EventHandler
    fun onJoin(e: PlayerJoinEvent) {
        createScoreboard(e.player)
    }

    @EventHandler
    fun onQuit(e: PlayerQuitEvent) {
        updateTasks.remove(e.player.uniqueId)?.cancel()
    }



    private fun createScoreboard(player: Player) {
        val manager = Bukkit.getScoreboardManager() ?: return
        val board = manager.newScoreboard

        val title = GradientUtil.createGradient("Minevidência", "#f95858", "#ed1212")
        val obj = board.registerNewObjective("sidebar", "dummy").apply {
            displaySlot = DisplaySlot.SIDEBAR
            displayName = title
        }
        createTeam(board, "money", "💰 Dinheiro: ", ChatColor.GREEN, 9)
        createTeam(board, "kills", "⚔ Kills: ", ChatColor.DARK_RED, 7)
        createTeam(board, "deaths", "💀 Mortes: ", ChatColor.DARK_GRAY, 5)
        createTeam(board, "online", "👥 Online: ", ChatColor.BLUE, 3)

        obj.getScore("      ").score = 10
        obj.getScore("    ").score = 8
        obj.getScore("   ").score = 6
        obj.getScore("  ").score = 4
        obj.getScore(" ").score = 2
        obj.getScore("§e§lmineemevidencia.lowping.host").score = 1
        obj.getScore("     ").score = 0

        startUpdateTask(player, board)

        player.scoreboard = board
    }



    private fun createTeam(board: Scoreboard, name: String, prefix: String, entryColor: ChatColor, score: Int) {
        val team = board.registerNewTeam(name).apply {
            this.prefix = prefix
            addEntry(entryColor.toString())
        }
        board.getObjective(DisplaySlot.SIDEBAR)?.getScore(entryColor.toString())?.score = score




    }
    private fun startUpdateTask(player: Player, board: Scoreboard) {
        val econ = me.imf4ll.displayEmEvidencia.scoreboard.Scoreboard.econ

        val task = Bukkit.getScheduler().runTaskTimer(plugin, Runnable {
            if (!player.isOnline) {
                updateTasks.remove(player.uniqueId)?.cancel()
                return@Runnable
            }
            board.getTeam("money")?.suffix = "§a${numberFormatter.format(econ?.getBalance(player) ?: 0.0)}"
            board.getTeam("kills")?.suffix = "§6${player.getStatistic(Statistic.PLAYER_KILLS)}"
            board.getTeam("deaths")?.suffix = "§c${player.getStatistic(Statistic.DEATHS)}"
            board.getTeam("online")?.suffix = "§b${Bukkit.getOnlinePlayers().size}"

        }, 0L, 20L)

        updateTasks[player.uniqueId] = task
    }
}


