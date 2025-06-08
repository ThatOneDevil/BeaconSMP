package inventoryUtils

import Utils.toComponent
import net.kyori.adventure.text.Component
import net.minestom.server.inventory.Inventory
import net.minestom.server.inventory.InventoryType
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material

class Inventory(inventoryType: InventoryType, name: String) : Inventory(inventoryType, name.toComponent()) {

    private val inventoryFiller = ItemStack.builder(Material.GRAY_STAINED_GLASS_PANE).customName(Component.empty()).build()

    private val boarderSlots: Map<InventoryType, List<Int>> = mapOf(
        InventoryType.CHEST_3_ROW to listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26),
        InventoryType.CHEST_4_ROW to listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35),
        InventoryType.CHEST_5_ROW to listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44),
        InventoryType.CHEST_6_ROW to listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53)
    )

    init {
        boarderSlots[inventoryType]?.forEach { slot ->
            setItemStack(slot, inventoryFiller)
        }
    }


}