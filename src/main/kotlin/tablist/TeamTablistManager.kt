package tablist

import Utils.toComponent
import net.minestom.server.MinecraftServer
import net.minestom.server.entity.Player
import net.minestom.server.scoreboard.TeamManager
import playerData.Ranks
import java.util.*

object TeamTablistManager {
    private val teamManager: TeamManager = MinecraftServer.getTeamManager()
    private val playerRankCache = mutableMapOf<UUID, Ranks>()

    fun setupTeams() {
        Ranks.entries.sortedByDescending { it.weight }.forEach { rank ->
            createTeam(rank)
        }
    }

    private fun createTeam(rank: Ranks) {
        // Prefix team names with weight for sorting order
        val teamName = "%02d_%s".format(99 - rank.weight, rank.name)
        teamManager.createTeam(teamName).apply {
            this.prefix = ("${rank.prefix} ").toComponent()
        }
    }

    fun assignPlayerToTeam(player: Player, rank: Ranks) {
        val uuid = player.uuid
        val cachedRank = playerRankCache[uuid]
        if (cachedRank == rank) return

        val teamName = "%02d_%s".format(99 - rank.weight, rank.name)
        val team = teamManager.getTeam(teamName) ?: return

        teamManager.teams.forEach { it.players.remove(player) }

        team.players.add(player)
        player.team = team
        player.displayName = ("${rank.prefix}${player.username}").toComponent()

        playerRankCache[uuid] = rank
    }
}

