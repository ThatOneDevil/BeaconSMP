package commands.admin

import commands.AdminCommand
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.Component

import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.command.builder.suggestion.SuggestionEntry
import net.minestom.server.entity.Player
import playerdata.PlayerDataManager
import playerdata.Ranks
import tablist.TeamTablistManager

class RankCommands : AdminCommand("rank") {

    init {
        setDefaultExecutor { sender, _ ->
            sender.sendMessage("Usage: /rank <set> <player> <rank>")
        }

        val rankCommand = ArgumentType.String("rankCommand")
        val target = ArgumentType.Entity("target").onlyPlayers(true)
        val rank = ArgumentType.Enum("rank", Ranks::class.java)
        val suggestions = listOf("set")

        rankCommand.setSuggestionCallback { _, _, suggestion ->
            suggestions.forEach { suggestion.addEntry(SuggestionEntry(it)) }
        }

        addSyntax({ sender, context ->
            sender as Player

            val commandArg = context[rankCommand]
            val targetPlayer = context[target].findFirstPlayer(sender)
            val rankArg = context[rank]

            if (commandArg == null || targetPlayer == null || rankArg == null) {
                sender.sendMessage(Component.text("Invalid argument!", TextColor.color(0xFF6961)))
                return@addSyntax
            }

            if (commandArg !in suggestions) {
                sender.sendMessage(Component.text("Invalid argument. Must be one of: $suggestions", TextColor.color(0xFF6961)))
                return@addSyntax
            }

            val targetPlayerData = PlayerDataManager.getOrCreatePlayerData(targetPlayer.uuid)
            targetPlayerData.rank = rankArg
            TeamTablistManager.assignPlayerToTeam(targetPlayer, rankArg)

            sender.sendMessage(Component.text("Rank for ${targetPlayer.username} set to ${rankArg.name}")
                .color(TextColor.fromHexString(rankArg.color)))

            if (targetPlayer != sender) {
                targetPlayer.sendMessage(Component.text("Your rank has been set to ${rankArg.name}")
                    .color(TextColor.fromHexString(rankArg.color)))
            }

        }, rankCommand, target, rank)
    }

}

