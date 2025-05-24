package commands.admin

import commands.AdminCommand
import instanceContainer
import net.minestom.server.entity.Player

class SaveWorldCommand : AdminCommand("save") {
    init {
        setDefaultExecutor { sender, _ ->
            sender.sendMessage("Usage: /save")
        }

        addSyntax({ sender, _ ->
            sender as Player
            instanceContainer.saveChunksToStorage()
            sender.sendMessage("World saved successfully.")
        })
    }
}