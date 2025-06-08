package spawner

import beacon.Beacon
import beacon.BeaconPlace
import net.kyori.adventure.nbt.CompoundBinaryTag
import net.minestom.server.entity.EntityType
import net.minestom.server.event.Event
import net.minestom.server.event.EventNode
import net.minestom.server.event.player.PlayerBlockInteractEvent
import net.minestom.server.event.player.PlayerBlockPlaceEvent
import net.minestom.server.instance.block.Block
import net.minestom.server.item.Material
import net.minestom.server.tag.Tag
import playerData.gson.GSON

object SpawnerEvents {

    var node: EventNode<Event> = EventNode.all("spawner-events")

    init {
        node.addListener(PlayerBlockPlaceEvent::class.java) { event ->
            val player = event.player
            val item = player.itemInMainHand
            if (item.material() == Material.SPAWNER) {
                val tag = Tag.String("spawnerData")
                val spawnerData = item.getTag(tag)
                val block = Block.SPAWNER.withTag(tag, spawnerData)
                event.block = block
            }
        }

        node.addListener(PlayerBlockInteractEvent::class.java) { event ->
            val player = event.player
            val block = event.block

            if (block.compare(Block.SPAWNER)){
                val tag = Tag.String("spawnerData")
                val spawnerData = GSON.fromJson(block.getTag(tag), SpawnerData::class.java)

                val spawnerGui = SpawnerGui(spawnerData).createSpawnerGui()
                player.openInventory(spawnerGui)
            }
        }


    }

}