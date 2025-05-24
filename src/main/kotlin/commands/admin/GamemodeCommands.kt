package commands.admin

import commands.AdminCommand
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.ArgumentCallback
import net.minestom.server.command.builder.CommandContext
import net.minestom.server.command.builder.CommandExecutor
import net.minestom.server.command.builder.arguments.ArgumentEnum
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.command.builder.exception.ArgumentSyntaxException
import net.minestom.server.entity.Entity
import net.minestom.server.entity.GameMode
import net.minestom.server.entity.Player
import net.minestom.server.utils.entity.EntityFinder

class GamemodeCommands : AdminCommand("gamemode", "gm") {
    init {
        val gamemode = ArgumentType.Enum("gamemode", GameMode::class.java).setFormat(ArgumentEnum.Format.LOWER_CASED)
        val player = ArgumentType.Entity("targets").onlyPlayers(true)

        gamemode.callback = ArgumentCallback { sender: CommandSender, exception: ArgumentSyntaxException ->
            sender.sendMessage(
                Component.text("Invalid gamemode ", NamedTextColor.RED)
                    .append(Component.text(exception.input, NamedTextColor.WHITE))
                    .append(Component.text("!"))
            )
        }

        defaultExecutor = CommandExecutor { sender: CommandSender, context: CommandContext ->
            val commandName = context.commandName
            sender.sendMessage(
                Component.text(
                    "Usage: /$commandName <gamemode> [targets]",
                    NamedTextColor.RED
                )
            )
        }

        addSyntax({ sender, context ->
            val mode = context[gamemode]

            executeSelf(sender as Player, mode)
        }, gamemode)

        addSyntax({ sender, context ->
            val finder: EntityFinder = context[player]
            val mode = context[gamemode]

            executeOthers(sender, mode, finder.find(sender))
        }, gamemode, player)

    }

    private fun executeSelf(sender: Player, mode: GameMode) {
        sender.setGameMode(mode)

        val gamemodeString = "gameMode." + mode.name.lowercase()
        val gamemodeComponent: Component = Component.translatable(gamemodeString)

        sender.sendMessage(
            Component.translatable("commands.gamemode.success.self", gamemodeComponent)
        )
    }

    private fun executeOthers(sender: CommandSender, mode: GameMode, entities: List<Entity>) {
        if (entities.isEmpty()) {
            if (sender is Player) {
                sender.sendMessage(Component.translatable("argument.entity.notfound.player", NamedTextColor.RED))
            } else {
                sender.sendMessage(Component.text("No player was found", NamedTextColor.RED))
            }
        }

        for (entity in entities) {
            if (entity is Player) {
                if (entity === sender) {
                    executeSelf(sender, mode)
                    return
                }

                val gamemodeString = "gameMode." + mode.name.lowercase()
                val gamemodeComponent: Component = Component.translatable(gamemodeString)
                val playerName: Component = entity.displayName ?: entity.name
                entity.sendMessage(Component.translatable("gameMode.changed", gamemodeComponent))
                sender.sendMessage(
                    Component.translatable(
                        "commands.gamemode.success.other",
                        playerName,
                        gamemodeComponent
                    )
                )
            }
        }
    }

}