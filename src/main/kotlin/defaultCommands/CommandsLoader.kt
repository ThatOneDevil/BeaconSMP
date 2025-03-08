package defaultCommands

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.MinecraftServer
import net.minestom.server.command.CommandSender
import net.minestom.server.entity.Player

class CommandsLoader {

    init {
        val commandManager = MinecraftServer.getCommandManager()
        commandManager.register(GamemodeCommands())
        commandManager.register(GiveCommand())
        commandManager.register(StopCommand())
        commandManager.register(TeleportCommand())
    }

}