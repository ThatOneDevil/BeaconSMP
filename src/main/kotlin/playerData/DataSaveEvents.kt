package playerData

import net.minestom.server.MinecraftServer
import net.minestom.server.event.Event
import net.minestom.server.event.EventNode
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent
import net.minestom.server.event.player.PlayerDisconnectEvent
import net.minestom.server.utils.time.TimeUnit
import playerData.PlayerDataManager.getOrCreatePlayerData
import playerData.PlayerDataManager.loadPlayerData
import playerData.PlayerDataManager.savePlayerData
import tablist.TeamTablistManager


object DataSaveEvents {
    var node: EventNode<Event> = EventNode.all("data-save-events")

    init {
        node.addListener(AsyncPlayerConfigurationEvent::class.java) { event ->
            val player = event.player
            val data = loadPlayerData(getOrCreatePlayerData(player.uuid))

            MinecraftServer.getSchedulerManager().buildTask() {
                TeamTablistManager.assignPlayerToTeam(player, data.get().rank)
            }.delay(20, TimeUnit.SERVER_TICK).schedule()

        }

        node.addListener(PlayerDisconnectEvent::class.java) { event ->
            val player = event.player

            savePlayerData(getOrCreatePlayerData(player.uuid))
        }

    }

}