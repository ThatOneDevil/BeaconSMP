package spawner

import Utils.toComponent
import net.kyori.adventure.text.Component
import net.minestom.server.inventory.Inventory
import net.minestom.server.inventory.InventoryType
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material

class SpawnerGui {
    private val inventoryFiller = ItemStack.builder(Material.GRAY_STAINED_GLASS_PANE).customName(Component.empty()).build()

    fun createSpawnerGui(spawnerData: SpawnerData): Inventory {
        val inventory = Inventory(InventoryType.CHEST_6_ROW, ("<color:C3B1E1>${spawnerData.entityID?.name()}&f Gui").toComponent())

        // numbers from 0-8,9,17,18,26,27,35,36,44,45-53
        for (i in 0..53) {
            if (i % 9 == 0 || i % 9 == 8 || i < 9 || i > 44) {
                inventory.setItemStack(i, inventoryFiller)
            }
        }

        return inventory

    }
}