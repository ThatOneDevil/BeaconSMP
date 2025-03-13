package commands.admin

import commands.AdminCommand
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.MinecraftServer
import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.CommandContext
import net.minestom.server.command.builder.CommandExecutor
import net.minestom.server.command.builder.arguments.ArgumentType.Entity
import net.minestom.server.command.builder.arguments.ArgumentType.RelativeVec3
import net.minestom.server.coordinate.Pos
import net.minestom.server.entity.Player
import net.minestom.server.utils.MathUtils
import net.minestom.server.utils.location.RelativeVec


class TeleportCommand : AdminCommand("teleport", "tp") {
    private fun teleportPlayer(sender: Player, target1Name: String, target2Name: String? = null, pos: Pos? = null) {
        val connectionManager = MinecraftServer.getConnectionManager()
        val target1Player = connectionManager.getOnlinePlayerByUsername(target1Name)

        if (target2Name != null) {
            val target2Player = connectionManager.getOnlinePlayerByUsername(target2Name)
            target1Player?.teleport(target2Player!!.position)
            sender.sendMessage(Component.text("Teleported $target1Name to $target2Name"))
        } else if (pos != null) {
            target1Player?.teleport(pos)
            sender.sendMessage(Component.text("Teleported $target1Name to ${getPrettyLocation(pos)}"))
        } else {
            sender.teleport(target1Player!!.position)
            sender.sendMessage(Component.text("Teleported to player $target1Name"))
        }
    }

    init {
        defaultExecutor = CommandExecutor { source: CommandSender, _: CommandContext? ->
            source.sendMessage(
                Component.text("Usage: /tp [<player>/<x><y><z>] [<player>/<x><y><z>] ", NamedTextColor.RED)
            )
        }

        val posArg = RelativeVec3("pos")
        val target1 = Entity("target1").onlyPlayers(true)
        val target2 = Entity("target2").onlyPlayers(true)

        addSyntax({ sender, context ->
            sender as Player
            val target1Name = context[target1].findFirstPlayer(sender)?.username

            teleportPlayer(sender, target1Name!!)
        }, target1)

        addSyntax({ sender, context ->
            sender as Player
            val target1Name = context[target1].findFirstPlayer(sender)?.username
            val target2Name = context[target2].findFirstPlayer(sender)?.username

            teleportPlayer(sender, target1Name!!, target2Name)
        }, target1, target2)

        addSyntax({ sender, context ->
            sender as Player
            val relativeVec: RelativeVec = context[posArg]
            val pos = sender.position.withCoord(relativeVec.from(sender))

            sender.teleport(pos)
            sender.sendMessage(Component.text("Teleported to ${getPrettyLocation(pos)}"))
        }, posArg)

        addSyntax({ sender, context ->
            sender as Player
            val target1Name = context[target1].findFirstPlayer(sender)?.username
            val relativeVec: RelativeVec = context[posArg]
            val pos = sender.position.withCoord(relativeVec.from(sender))

            teleportPlayer(sender, target1Name!!, pos = pos)
        }, target1, posArg)
    }

    private fun getPrettyLocation(pos: Pos): String {
        return "X: ${MathUtils.round(pos.x(), 2)} Y: ${MathUtils.round(pos.y(), 2)} Z: ${MathUtils.round(pos.z(), 2)}"
    }
}