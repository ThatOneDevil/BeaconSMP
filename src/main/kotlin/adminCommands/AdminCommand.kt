package adminCommands

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.command.builder.Command
import net.minestom.server.entity.Player

open class AdminCommand(name: String, vararg aliases: String?) : Command(name, *aliases) {
    init {
        this.setCondition { sender, _ ->
            return@setCondition if (sender is Player && sender.permissionLevel > 2) true
            else {
                sender.sendMessage(Component.text("You do not have permission to use this command", NamedTextColor.RED))
                false
            }
        }
    }

}