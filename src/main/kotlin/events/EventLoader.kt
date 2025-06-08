package events

import beacon.BeaconPlace
import net.minestom.server.MinecraftServer
import playerData.DataSaveEvents
import spawner.SpawnerEvents
import spawner.SpawnerGui

class EventLoader {

    init {
        val eventHandler = MinecraftServer.getGlobalEventHandler()

        eventHandler.addChild(DataSaveEvents.node)
        eventHandler.addChild(ChatEvent.node)
        eventHandler.addChild(PlayerConnectionEvent.node)

        // Registering beacon events#
        eventHandler.addChild(BeaconPlace.node)

        // Registering spawner events
        eventHandler.addChild(SpawnerEvents.node)

    }
}