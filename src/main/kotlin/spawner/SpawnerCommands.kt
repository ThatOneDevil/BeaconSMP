package spawner

import commands.AdminCommand
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.command.builder.arguments.ArgumentType.Entity
import net.minestom.server.command.builder.suggestion.SuggestionEntry
import net.minestom.server.entity.EntityType
import net.minestom.server.entity.Player

class SpawnerCommands : AdminCommand("spawner") {

    init {
        setDefaultExecutor { sender, _ ->
            sender.sendMessage("Usage: /spawner <type> <player>")
        }

        setCondition { sender, _ -> sender is Player && sender.permissionLevel >= 2 }

        val target = Entity("target").onlyPlayers(true)
        val entityTypeArg = ArgumentType.StringArray("entityType")

        val entityTypeValues = EntityType.values().map { it.name() }
        entityTypeArg.setSuggestionCallback { _, _, suggestion ->
            entityTypeValues.forEach { type ->
                suggestion.addEntry(SuggestionEntry(type))
            }
        }

        addSyntax({ sender, context ->
            sender as Player
            val type = EntityType.fromKey(context[entityTypeArg][0].toString())

            val targetPlayer = context[target].findFirstPlayer(sender)

            targetPlayer?.inventory?.addItemStack(SpawnerData(type, targetPlayer.uuid).spawnerItem())
        }, target, entityTypeArg)
    }
}