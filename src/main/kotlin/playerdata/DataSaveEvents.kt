package playerdata

import net.minestom.server.event.Event
import net.minestom.server.event.EventNode
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent
import net.minestom.server.event.player.PlayerDisconnectEvent
import playerdata.PlayerDataManager.getOrCreatePlayerData
import playerdata.PlayerDataManager.loadPlayerData
import playerdata.PlayerDataManager.savePlayerData
import tablist.TeamTablistManager


object DataSaveEvents {
    var node: EventNode<Event> = EventNode.all("data-save-events")

    init {
        node.addListener(AsyncPlayerConfigurationEvent::class.java) { event ->
            val player = event.player
            val data = getOrCreatePlayerData(player.uuid)

            loadPlayerData(data)

            TeamTablistManager.assignPlayerToTeam(player, data.rank)

        }

        node.addListener(PlayerDisconnectEvent::class.java) { event ->
            val player = event.player

            savePlayerData(getOrCreatePlayerData(player.uuid))
        }

    }

}