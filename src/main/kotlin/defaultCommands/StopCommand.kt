package defaultCommands

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.MinecraftServer
import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.CommandContext
import net.minestom.server.entity.Player


class ShutdownCommand : Command("shutdown", "stop") {
    init {
        addSyntax(::execute)
    }

    private fun execute(sender: CommandSender, context: CommandContext) {
        if (sender is Player && sender.permissionLevel < 2) {
            sender.sendMessage(Component.text("You don't have permission to use this command.", NamedTextColor.RED))
            return
        }

        MinecraftServer.stopCleanly()
    }
}