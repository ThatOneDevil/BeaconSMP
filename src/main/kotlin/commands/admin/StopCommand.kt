package commands.admin

import commands.AdminCommand
import net.minestom.server.MinecraftServer
import net.minestom.server.entity.Player

class StopCommand : AdminCommand("stop") {
    init {
        setDefaultExecutor { sender, _ ->
            sender.sendMessage("Usage: /stop")
        }

        addSyntax({ sender, _ ->
            sender as Player
            MinecraftServer.getSchedulerManager().shutdown()
            MinecraftServer.stopCleanly()
        })
    }
}