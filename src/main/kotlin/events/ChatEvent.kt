package events

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.minestom.server.event.Event
import net.minestom.server.event.EventNode
import net.minestom.server.event.player.PlayerChatEvent
import playerdata.PlayerDataManager.getDataClass

object ChatEvent {
    var node: EventNode<Event> = EventNode.all("player-events")

    init {
        node.addListener(PlayerChatEvent::class.java) { event ->
            val player = event.player

            val rank = getDataClass(player)?.rank

            val chatMessage = Component.text("${rank?.prefix} ${player.username} » ${event.rawMessage}").color(
                TextColor.fromHexString(rank?.color.toString()))

            event.formattedMessage = chatMessage
        }
    }
}