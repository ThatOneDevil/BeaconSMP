package events

import beacon.Beacon
import net.minestom.server.MinecraftServer
import net.minestom.server.event.Event
import net.minestom.server.event.EventNode
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent
import net.minestom.server.event.player.PlayerDisconnectEvent
import net.minestom.server.utils.time.TimeUnit
import playerData.PlayerDataManager.getData

object PlayerConnectionEvent {

    var node: EventNode<Event> = EventNode.all("player-events")

    init {
        node.addListener(AsyncPlayerConfigurationEvent::class.java) { event ->
            val player = event.player
            event.player.instance

            MinecraftServer.getSchedulerManager().buildTask() {
                val pos = player.getData()?.textDisplay?.position ?: return@buildTask

                Beacon(player, event.spawningInstance).create(pos)
            }.delay(20, TimeUnit.SERVER_TICK).schedule()

        }

        node.addListener(PlayerDisconnectEvent::class.java) { event ->
            val player = event.player
            Beacon(player, event.instance).removeEntity()

        }

    }
}