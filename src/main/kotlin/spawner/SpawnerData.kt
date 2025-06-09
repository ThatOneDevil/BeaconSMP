package spawner

import Utils.formatName
import Utils.toComponent
import net.kyori.adventure.text.Component
import net.minestom.server.coordinate.BlockVec
import net.minestom.server.entity.EntityType
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import net.minestom.server.tag.Tag
import playerData.gson.Exclude
import playerData.gson.GSON
import java.util.UUID

data class SpawnerData(
    @Exclude var entityID: EntityType?,
    var player: UUID,
    var location: BlockVec? = null,
    var entityName: String = formatName(entityID?.name().toString()),
    var drops: Drops = Drops.valueOf(entityName.uppercase()),
    var spawnCount: Int = 1,
    var spawnDelay: Long = 20,
    val stackSize: Int = 0,
    val expGain: Double = 1.0,
) {

    fun spawnerItem(): ItemStack {
        val tag = Tag.String("spawnerData")
        val spawnerData = GSON.toJson(this)

        return ItemStack.builder(Material.SPAWNER)
            .customName("<color:#BFA2DB>${entityName} Spawner".toComponent()) // soft pastel lavender
            .hideExtraTooltip()
            .lore(
                listOf(
                    ("<color:#E3DFFF>ʀɪɢʜᴛ ᴄʟɪᴄᴋ ᴛᴏ ᴏᴘᴇɴ ɢᴜɪ".toComponent()), // pastel white
                    (Component.empty()),
                    ("<color:#BFA2DB><bold>Spawner Info:").toComponent(), // lavender
                    (" <color:#BFA2DB>▪ <color:#C5FAD5>Entity Type: <color:#FFF5BA>$entityName").toComponent(),
                    (" <color:#BFA2DB>▪ <color:#C5FAD5>Stack Size: <color:#FFF5BA>${this.stackSize}").toComponent(),
                    (" <color:#BFA2DB>▪ <color:#C5FAD5>Spawn Delay: <color:#FFF5BA>${this.spawnDelay} ticks").toComponent(),
                    (" <color:#BFA2DB>▪ <color:#C5FAD5>Spawn Count: <color:#FFF5BA>${this.spawnCount}").toComponent(),
                    (" <color:#BFA2DB>▪ <color:#C5FAD5>Experience Gain: <color:#FFF5BA>${this.expGain}").toComponent()
                )
            ).build().withTag(tag, spawnerData)
    }

}