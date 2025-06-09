package spawner


import Utils.formatName
import Utils.toComponent
import net.kyori.adventure.text.Component
import net.minestom.server.MinecraftServer
import net.minestom.server.event.EventFilter
import net.minestom.server.event.EventNode
import net.minestom.server.event.inventory.InventoryPreClickEvent
import net.minestom.server.inventory.Inventory
import net.minestom.server.inventory.InventoryType
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material


class SpawnerGui(private var spawnerData: SpawnerData) {

    fun createSpawnerGui(): Inventory {
        val inventory = inventoryUtils.Inventory(InventoryType.CHEST_3_ROW, "<color:#854ebb>&l${spawnerData.entityName} Gui")

        registerEventNodes(inventory)

        inventory.setItemStack(11, spawnerStorageItem())
        inventory.setItemStack(13, spawnerData.spawnerItem())
        inventory.setItemStack(15, ItemStack.of(Material.EXPERIENCE_BOTTLE).withCustomName("<color:#56AE57>Collect Experience".toComponent()))

        return inventory
    }

    private fun spawnerStorageItem(): ItemStack {
        val lore = mutableListOf<Component>()
        val spawnerDrops = spawnerData.drops

        lore.addAll(listOf(
            "<color:#FFD6E8>ʀɪɢʜᴛ ᴄʟɪᴄᴋ ᴛᴏ ᴏᴘᴇɴ ꜱᴛᴏʀᴀɢᴇ".toComponent(),
            Component.empty(),
            "<color:#B5EAD7><bold>Spawner Storage:".toComponent(),
        ))

        val formatDrops = spawnerDrops.items.map { itemStack ->
            " <color:#FFDAC1>▪ <color:#C7CEEA>${formatName(itemStack.name())} 0".toComponent()
        }
        lore.addAll(formatDrops)

        return ItemStack.of(Material.CHEST)
            .withCustomName("<color:#FFB7B2>Spawner Storage".toComponent())
            .withLore(lore)
    }

    private fun registerEventNodes(inventory: Inventory) {
        val node = EventNode.type("spawner-inventory", EventFilter.INVENTORY) { _, inv -> inventory == inv }
            .addListener(InventoryPreClickEvent::class.java) { event ->
                event.isCancelled = true
                event.player.sendMessage("Clicked!" + event.slot)
            }

        MinecraftServer.getGlobalEventHandler().addChild(node)
    }
}