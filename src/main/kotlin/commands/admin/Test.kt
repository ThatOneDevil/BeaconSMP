package commands.admin

import commands.AdminCommand
import net.minestom.server.entity.Player
import playerData.PlayerDataManager.getData

class Test : AdminCommand("test") {

    init {

        addSyntax({ sender, _ ->
            val player = sender as Player
            val data = player.getData() ?: return@addSyntax
            player.instance?.getEntityByUuid(data.textDisplay!!.uuid)?.remove()
            println("Removed entity with UUID: ${data.textDisplay!!.uuid}")

        })
    }
}