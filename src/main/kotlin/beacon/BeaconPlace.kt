package beacon

import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.minestom.server.event.Event
import net.minestom.server.event.EventNode
import net.minestom.server.event.player.PlayerBlockBreakEvent
import net.minestom.server.event.player.PlayerBlockPlaceEvent
import net.minestom.server.instance.block.Block
import net.minestom.server.tag.Tag
import net.minestom.server.item.Material
import playerData.PlayerDataManager.getData

object BeaconPlace {

    var node: EventNode<Event> = EventNode.all("beacon-events")

    init {
        node.addListener(PlayerBlockPlaceEvent::class.java) { event ->
            val player = event.player

            if (player.itemInMainHand.material() == Material.BEACON) {
                val blockPos = event.blockPosition

                val tag = Tag.UUID("beacon-owner")
                val block = Block.BEACON.withTag(tag, player.uuid)

                event.block = block

                if (player.getData()?.textDisplay != null) {
                    player.sendMessage(Component.text("You already have a beacon placed!", TextColor.color(0xFF6961)))
                    event.isCancelled = true
                    return@addListener
                }

                Beacon(player).create(blockPos)
            }
        }

        node.addListener(PlayerBlockBreakEvent::class.java) { event ->
            val player = event.player
            val block = event.block

            val tag = Tag.UUID("beacon-owner")
            if (block.hasTag(tag)){
                if (player.uuid != block.getTag(tag)){
                    player.sendMessage(Component.text("You cannot break this beacon!", TextColor.color(0xFF6961)))
                    event.isCancelled = true
                    return@addListener
                }

                val beacon = Beacon(player)
                beacon.remove()
                player.sendMessage(Component.text("Beacon removed successfully!", TextColor.color(0x00FF00)))

            }
        }
    }

}