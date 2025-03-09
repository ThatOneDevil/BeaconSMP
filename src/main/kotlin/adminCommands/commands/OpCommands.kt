package adminCommands.commands

import adminCommands.AdminCommand
import net.minestom.server.MinecraftServer
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType.Entity
import net.minestom.server.entity.Player

class OpCommand : AdminCommand("op") {
    init {
        setDefaultExecutor { sender, _ ->
            sender.sendMessage("Usage: /op <player>")
        }

        val target = Entity("target1").onlyPlayers(true)

        addSyntax({ sender, context ->
            sender as Player
            val player = context[target].findFirstPlayer(sender)!!
            player.permissionLevel = 4
            player.sendMessage("You are now opped by ${sender.username}")

            for (onlinePlayer in MinecraftServer.getConnectionManager().onlinePlayers) {
                if (onlinePlayer.permissionLevel >= 2) {
                    onlinePlayer.sendMessage("${player.username} was opped by ${sender.username}")
                }
            }
        }, target)
    }
}

class DeopCommand : Command("deop") {
    init {
        setDefaultExecutor { sender, _ ->
            sender.sendMessage("Usage: /deop <player>")
        }

        setCondition { sender, _ -> sender is Player && sender.permissionLevel >= 2 }

        val target = Entity("target1").onlyPlayers(true)

        addSyntax({ sender, context ->
            sender as Player
            val player = context[target].findFirstPlayer(sender)!!
            player.permissionLevel = 0
            player.sendMessage("You are no longer opped by ${sender.username}")

            for (onlinePlayer in MinecraftServer.getConnectionManager().onlinePlayers) {
                if (onlinePlayer.permissionLevel >= 2) {
                    onlinePlayer.sendMessage("${player.username} was deopped by ${sender.username}")
                }
            }
        }, target)
    }
}