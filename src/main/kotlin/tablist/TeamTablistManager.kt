package tablist

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.minestom.server.MinecraftServer
import net.minestom.server.entity.Player
import net.minestom.server.scoreboard.TeamManager
import playerdata.Ranks

object TeamTablistManager {
    private val teamManager: TeamManager = MinecraftServer.getTeamManager()

    fun setupTeams() {
        Ranks.entries.sortedByDescending { it.weight }.forEach { rank ->
            createTeam(rank)
        }
    }

    private fun createTeam(rank: Ranks) {
        // Prefix team names with weight for sorting order
        val teamName = "%02d_%s".format(99 - rank.weight, rank.name)
        teamManager.createTeam(teamName).apply {
            this.prefix = Component.text("${rank.prefix} ").color(TextColor.fromHexString(rank.color))
        }
    }

    fun assignPlayerToTeam(player: Player, rank: Ranks) {
        val teamName = "%02d_%s".format(99 - rank.weight, rank.name)
        val team = teamManager.getTeam(teamName) ?: return

        teamManager.teams.forEach { it.players.remove(player) }

        team.players.add(player)
        player.team = team
    }
}

