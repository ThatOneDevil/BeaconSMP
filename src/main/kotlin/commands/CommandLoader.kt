package commands

import commands.admin.*
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.minestom.server.MinecraftServer
import spawner.SpawnerCommands


class CommandsLoader {

    init {
        val commandManager = MinecraftServer.getCommandManager()

        commandManager.setUnknownCommandCallback { sender, _ ->
            sender.sendMessage(
                Component.text(
                    "Unknown command"
                ).color(TextColor.color(0xFF6961))
            )
        }

        //admin commands
        commandManager.register(GamemodeCommands())
        commandManager.register(GiveCommand())
        commandManager.register(TeleportCommand())
        commandManager.register(OpCommand())
        commandManager.register(DeopCommand())
        commandManager.register(RankCommands())
        commandManager.register(SaveWorldCommand())
        commandManager.register(StopCommand())

        commandManager.register(SpawnerCommands())

        commandManager.register(Test())



        //normal commands
    }

}