package adminCommands

import adminCommands.commands.*
import net.minestom.server.MinecraftServer

class CommandsLoader {

    init {
        val commandManager = MinecraftServer.getCommandManager()

        commandManager.register(GamemodeCommands())
        commandManager.register(GiveCommand())
        commandManager.register(TeleportCommand())
        commandManager.register(OpCommand())
        commandManager.register(DeopCommand())
    }

}