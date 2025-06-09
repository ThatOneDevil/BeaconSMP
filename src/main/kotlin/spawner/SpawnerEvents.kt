package spawner

import Utils.yesMessage
import net.minestom.server.MinecraftServer
import net.minestom.server.coordinate.BlockVec
import net.minestom.server.event.Event
import net.minestom.server.event.EventNode
import net.minestom.server.event.player.PlayerBlockBreakEvent
import net.minestom.server.event.player.PlayerBlockInteractEvent
import net.minestom.server.event.player.PlayerBlockPlaceEvent
import net.minestom.server.instance.block.Block
import net.minestom.server.item.Material
import net.minestom.server.tag.Tag
import net.minestom.server.timer.Task
import net.minestom.server.utils.time.TimeUnit
import playerData.PlayerDataManager.getData
import playerData.gson.GSON
import spawner.tick.SpawnerTick

object SpawnerEvents {

    val node: EventNode<Event> = EventNode.all("spawner-events")
    val spawnerTicks = mutableMapOf<BlockVec, Task>()
    private val TAG_SPAWNER_DATA = Tag.String("spawnerData")

    init {
        registerPlaceEvent()
        registerBreakEvent()
        registerInteractEvent()
    }

    private fun registerPlaceEvent() {
        node.addListener(PlayerBlockPlaceEvent::class.java) { event ->
            val player = event.player
            val item = player.itemInMainHand

            if (item.material() != Material.SPAWNER) return@addListener

            val tag = item.getTag(TAG_SPAWNER_DATA) ?: return@addListener
            val data = GSON.fromJson(tag, SpawnerData::class.java) ?: return@addListener

            val position = event.blockPosition
            data.location = position

            event.block = Block.SPAWNER.withTag(TAG_SPAWNER_DATA, tag)

            val task = MinecraftServer.getSchedulerManager()
                .buildTask(SpawnerTick(data))
                .repeat(5, TimeUnit.SECOND)
                .schedule()

            spawnerTicks[position] = task
            player.getData()?.placedSpawners?.add(data)
            player.yesMessage("Spawner placed successfully!")
        }
    }

    private fun registerBreakEvent() {
        node.addListener(PlayerBlockBreakEvent::class.java) { event ->
            val position = event.blockPosition
            val data = getSpawnerDataOrNull(event.block) ?: return@addListener
            val player = event.player

            spawnerTicks.remove(position)?.cancel()
            player.getData()?.placedSpawners?.remove(data)

            player.inventory.addItemStack(data.spawnerItem())
            player.yesMessage("Spawner removed successfully!")
        }
    }

    private fun registerInteractEvent() {
        node.addListener(PlayerBlockInteractEvent::class.java) { event ->
            val data = getSpawnerDataOrNull(event.block) ?: return@addListener
            val gui = SpawnerGui(data).createSpawnerGui()
            event.player.openInventory(gui)
        }
    }

    private fun getSpawnerDataOrNull(block: Block): SpawnerData? {
        if (!block.compare(Block.SPAWNER)) return null
        return block.getTag(TAG_SPAWNER_DATA)?.let {
            GSON.fromJson(it, SpawnerData::class.java)
        }
    }
}
