package spawner

import Utils.toComponent
import net.kyori.adventure.text.Component
import net.minestom.server.entity.EntityType
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import net.minestom.server.tag.Tag
import playerData.gson.Exclude
import playerData.gson.GSON

data class SpawnerData(
    @Exclude var entityID: EntityType,
    var entityName: String = entityID.entityName(),
    var drops: MutableList<ItemStack> = mutableListOf(),
    var spawnCount: Int = 1,
    var spawnDelay: Int = 20,
    val stackSize: Int = 0
) {

    fun spawnerItem(): ItemStack {
        val tag = Tag.String("spawnerData")
        val spawnerData = GSON.toJson(this)

        return ItemStack.builder(Material.SPAWNER)
            .customName("<color:#BFA2DB>${entityName} Spawner".toComponent()) // soft pastel lavender
            .hideExtraTooltip()
            .lore(
                arrayListOf(
                    ("<color:#E3DFFF>Right click to open GUI".toComponent()), // pastel white
                    (Component.empty()),
                    ("<color:#BFA2DB><bold>Spawner Info:").toComponent(), // lavender
                    (" <color:#BFA2DB>▪ <color:#C5FAD5>Entity Type: <color:#FFF5BA>$entityName").toComponent(),
                    (" <color:#BFA2DB>▪ <color:#C5FAD5>Stack Size: <color:#FFF5BA>${this.stackSize}").toComponent(),
                    (" <color:#BFA2DB>▪ <color:#C5FAD5>Spawn Delay: <color:#FFF5BA>${this.spawnDelay} ticks").toComponent(),
                    (" <color:#BFA2DB>▪ <color:#C5FAD5>Spawn Count: <color:#FFF5BA>${this.spawnCount}").toComponent()
                )
            ).build().withTag(tag, spawnerData)
    }

}

private fun EntityType.entityName(): String {
    val rawName = this.name().split(":").last().replace("_", " ")
    return rawName.split(" ").joinToString(" ") { word ->
        word.replaceFirstChar { it.uppercase() }
    }
}