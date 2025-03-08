package defaultCommands

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.MinecraftServer
import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.CommandContext
import net.minestom.server.command.builder.CommandExecutor
import net.minestom.server.command.builder.arguments.ArgumentType.Entity
import net.minestom.server.command.builder.arguments.ArgumentType.RelativeVec3
import net.minestom.server.coordinate.Pos
import net.minestom.server.entity.Entity
import net.minestom.server.entity.Player
import net.minestom.server.utils.MathUtils


class TeleportCommand : Command("teleport", "tp") {
    init {
        defaultExecutor = CommandExecutor { source: CommandSender, _: CommandContext? ->
            source.sendMessage(
                Component.text("Usage: /tp <player> [<player>/<x><y><z>] ", NamedTextColor.RED)
            )
        }

        setCondition { sender, _ -> sender is Player && sender.permissionLevel >= 2 }

        val posArg = RelativeVec3("pos")
        val player = Entity("player").onlyPlayers(true)
        val target = Entity("target").onlyPlayers(true)


        addSyntax({ sender, context ->
            sender as Player

            val playerName = context[player].findFirstPlayer(sender)
            val pl = MinecraftServer.getConnectionManager().getOnlinePlayerByUsername(playerName!!.username)!!

            sender.teleport(pl.position)

            sender.sendMessage(Component.text("Teleported to player ${playerName.username}"))
        }, player)


        addSyntax({ sender, context ->
            sender as Player

            val playerName = context[player].findFirstPlayer(sender)
            val targetName = context[target].findFirstPlayer(sender)
            val pl = MinecraftServer.getConnectionManager().getOnlinePlayerByUsername(playerName!!.username)!!
            val targetPl = MinecraftServer.getConnectionManager().getOnlinePlayerByUsername(targetName!!.username)!!

            pl.teleport(targetPl.position)

            sender.sendMessage(Component.text("Teleported ${targetName.username} to ${playerName.username}"))

        }, player, target)

    }

    private fun teleportOthers(sender: Player, targets: List<Entity> , entities: List<Entity>) {
        for (entity in entities) {
            sender.teleport(entity.position)
            sender.sendMessage(Component.text("Teleported ${entities.size} entities to ${getPrettyLocation(entity.position)}"))
        }
    }

    private fun getPrettyLocation(pos: Pos): String {
        return "X: ${MathUtils.round(pos.x(), 2)} Y: ${MathUtils.round(pos.y(), 2)} Z: ${MathUtils.round(pos.z(), 2)}"
    }
}