package events

import net.minestom.server.MinecraftServer
import playerdata.DataSaveEvents

class EventLoader {

    init {
        val eventHandler = MinecraftServer.getGlobalEventHandler()

        eventHandler.addChild(DataSaveEvents.node)
        eventHandler.addChild(ChatEvent.node)
    }
}