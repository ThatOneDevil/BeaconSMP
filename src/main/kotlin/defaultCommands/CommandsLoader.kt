package defaultCommands

import net.minestom.server.MinecraftServer

class CommandsLoader {

    init {
        val commandManager = MinecraftServer.getCommandManager()
        commandManager.register(GamemodeCommands())
    }
}