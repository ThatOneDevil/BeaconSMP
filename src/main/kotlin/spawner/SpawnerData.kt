package spawner

import Utils.toComponent
import net.kyori.adventure.text.Component
import net.minestom.server.entity.EntityType
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material

data class SpawnerData(
    var entityID: EntityType,
    var drops: MutableList<ItemStack> = mutableListOf(),
    var spawnCount: Int = 1,
    var spawnDelay: Int = 20,
    val stackSize: Int = 0
) {

    fun spawnerItem(): ItemStack {
        val entityName = this.entityID.name().split(":").last().replace("_", " ")
        return ItemStack.builder(Material.SPAWNER)
            .customName("<color:#BFA2DB>&l${entityName} Spawner".toComponent()) // soft pastel lavender
            .hideExtraTooltip()
            .lore(
                listOf(
                    ("<color:#E3DFFF>Right click to open GUI".toComponent()), // pastel white
                    (Component.empty()),
                    ("<color:#BFA2DB><bold>Spawner Info:").toComponent(), // lavender
                    (" <color:#BFA2DB>▪ <color:#C5FAD5>Entity Type: <color:#FFF5BA>$entityName").toComponent(),
                    (" <color:#BFA2DB>▪ <color:#C5FAD5>Stack Size: <color:#FFF5BA>${this.stackSize}").toComponent(),
                    (" <color:#BFA2DB>▪ <color:#C5FAD5>Spawn Delay: <color:#FFF5BA>${this.spawnDelay} ticks").toComponent(),
                    (" <color:#BFA2DB>▪ <color:#C5FAD5>Spawn Count: <color:#FFF5BA>${this.spawnCount}").toComponent()
                )
            )
            .build()
    }


}