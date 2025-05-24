package commands

import commands.admin.*
import net.minestom.server.MinecraftServer

class CommandsLoader {

    init {
        val commandManager = MinecraftServer.getCommandManager()

        //admin commands
        commandManager.register(GamemodeCommands())
        commandManager.register(GiveCommand())
        commandManager.register(TeleportCommand())
        commandManager.register(OpCommand())
        commandManager.register(DeopCommand())
        commandManager.register(RankCommands())
        commandManager.register(SaveWorldCommand())
        commandManager.register(StopCommand())

        //normal commands
    }

}