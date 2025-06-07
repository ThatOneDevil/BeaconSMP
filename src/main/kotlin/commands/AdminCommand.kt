package commands

import Utils.noMessage
import net.minestom.server.command.builder.Command
import net.minestom.server.entity.Player

open class AdminCommand(name: String, vararg aliases: String?) : Command(name, *aliases) {
    init {

        this.setCondition { sender, _ ->
            return@setCondition if (sender is Player && sender.permissionLevel > 2) true
            else {
                sender.noMessage("You don't have permission to use this command!")
                false
            }
        }
    }


}