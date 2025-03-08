package defaultCommands

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.MinecraftServer
import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.CommandContext
import net.minestom.server.entity.Player

class StopCommand : Command("shutdown", "stop") {
    init {
        setDefaultExecutor { sender, _ ->
            sender.sendMessage(Component.text("Usage: /shutdown", NamedTextColor.RED))
        }

        setCondition { sender, _ -> sender is Player && sender.permissionLevel >= 2 }

        addSyntax({ _, _ ->
            MinecraftServer.stopCleanly()
        })
    }

}