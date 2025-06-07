package commands.admin

import commands.AdminCommand
import net.minestom.server.MinecraftServer
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType.Entity
import net.minestom.server.entity.Player

class OpCommand : AdminCommand("op") {
    init {
        setDefaultExecutor { sender, _ ->
            sender.sendMessage("Usage: /op <player>")
        }

        val target = Entity("target").onlyPlayers(true)

        addSyntax({ sender, context ->
            sender as Player
            val player = context[target].findFirstPlayer(sender)!!
            player.permissionLevel = 4
            player.sendMessage("You are now opped by ${sender.username}")

            for (onlinePlayer in MinecraftServer.getConnectionManager().onlinePlayers) {
                if (onlinePlayer.permissionLevel >= 2) {
                    if (onlinePlayer == player) {
                        continue
                    }
                    onlinePlayer.sendMessage("${player.username} was deopped by ${sender.username}")
                }
            }
        }, target)
    }
}

class DeopCommand : AdminCommand("deop") {
    init {
        setDefaultExecutor { sender, _ ->
            sender.sendMessage("Usage: /deop <player>")
        }

        val target = Entity("target").onlyPlayers(true)

        addSyntax({ sender, context ->
            sender as Player
            val player = context[target].findFirstPlayer(sender)!!
            player.permissionLevel = 0
            player.sendMessage("You are no longer opped by ${sender.username}")

            for (onlinePlayer in MinecraftServer.getConnectionManager().onlinePlayers) {
                if (onlinePlayer.permissionLevel >= 2) {
                    if (onlinePlayer == player) {
                        continue
                    }
                    onlinePlayer.sendMessage("${player.username} was deopped by ${sender.username}")
                }
            }
        }, target)
    }
}