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
import spawner.SpawnerData
import spawner.SpawnerEvents.spawnerTicks
import spawner.tick.SpawnerTick

object DataSaveEvents {
    val node: EventNode<Event> = EventNode.all("data-save-events")

    init {
        node.addListener(AsyncPlayerConfigurationEvent::class.java) { event ->
            val player = event.player
            val data = loadPlayerData(getOrCreatePlayerData(player.uuid))
            reactivateSpawners(data.get().placedSpawners)
        }

        node.addListener(PlayerDisconnectEvent::class.java) { event ->
            val player = event.player
            val data = getOrCreatePlayerData(player.uuid)
            savePlayerData(data)
            deactivateSpawners(data.placedSpawners)
        }
    }

    private fun reactivateSpawners(spawners: MutableSet<SpawnerData>) {
        for (spawner in spawners) {
            val location = spawner.location ?: continue

            val task = MinecraftServer.getSchedulerManager()
                .buildTask(SpawnerTick(spawner))
                .repeat(5, TimeUnit.SECOND)
                .schedule()

            spawnerTicks[location] = task
        }
    }

    private fun deactivateSpawners(spawners: MutableSet<SpawnerData>) {
        for (spawner in spawners) {
            val location = spawner.location ?: continue
            spawnerTicks.remove(location)?.cancel()
        }
    }
}