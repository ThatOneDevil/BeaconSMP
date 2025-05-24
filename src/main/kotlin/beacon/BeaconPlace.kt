package beacon

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.minestom.server.event.Event
import net.minestom.server.event.EventNode
import net.minestom.server.event.player.PlayerBlockPlaceEvent
import net.minestom.server.item.Material
import playerData.PlayerDataManager.getData

object BeaconPlace {

    var node: EventNode<Event> = EventNode.all("beacon-events")

    init {
        node.addListener(PlayerBlockPlaceEvent::class.java) { event ->
            val player = event.player
            val blockPos = event.blockPosition

            if (player.itemInMainHand.material() == Material.BEACON) {
                if (player.getData()?.textDisplay != null) {
                    player.sendMessage(Component.text("You already have a beacon placed!", TextColor.color(0xFF6961)))
                    event.isCancelled = true
                    return@addListener
                }

                Beacon(player).create(blockPos)

            }
        }
    }


}