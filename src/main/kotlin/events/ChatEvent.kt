package events

import Utils.toComponent
import net.minestom.server.event.Event
import net.minestom.server.event.EventNode
import net.minestom.server.event.player.PlayerChatEvent
import playerData.PlayerDataManager.getDataClass

object ChatEvent {
    var node: EventNode<Event> = EventNode.all("player-events")

    init {
        node.addListener(PlayerChatEvent::class.java) { event ->
            val player = event.player

            val rank = getDataClass(player)?.rank

            val chatMessage = ("${rank?.prefix}${player.username} » ${event.rawMessage}").toComponent()

            event.formattedMessage = chatMessage
        }
    }

}