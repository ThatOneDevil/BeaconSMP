package beacon

import Utils.toComponent
import displayEntities.TextDisplay
import net.kyori.adventure.text.Component
import net.minestom.server.coordinate.Point
import net.minestom.server.coordinate.Vec
import net.minestom.server.entity.Entity
import net.minestom.server.entity.EntityType
import net.minestom.server.entity.Player
import net.minestom.server.entity.metadata.display.AbstractDisplayMeta
import net.minestom.server.entity.metadata.display.TextDisplayMeta
import net.minestom.server.instance.Instance
import playerData.PlayerDataManager.getData


class Beacon(var player: Player, private var instance: Instance? = player.instance) {
    private var textDisplay: TextDisplay?

    init {
        textDisplay = player.getData()?.textDisplay
    }

    fun getTextDisplay(): TextDisplay? {
        return textDisplay
    }

    fun create(blockVec: Point){
        makeHologram(blockVec)
    }

    fun removeEntity() {
        val data = player.getData() ?: return
        if (data.textDisplay != null) {
            println("Removed entity with UUID: ${data.textDisplay!!.uuid}")
            instance?.getEntityByUuid(data.textDisplay!!.uuid)?.remove()

        }
    }

    fun remove(){
        removeEntity()
        player.getData()?.textDisplay = null

    }

    private fun makeHologram(blockVec: Point) {
        val data = player.getData()!!
        val displayPos = textDisplay?.position ?: blockVec.add(0.5, 1.0, 0.5)
        val textDisplay = Entity(EntityType.TEXT_DISPLAY)

        textDisplay.setInstance(instance!!, displayPos)
        textDisplay.setNoGravity(true)

        val textDisplayMeta = textDisplay.entityMeta as TextDisplayMeta
        textDisplayMeta.text = beaconText(player)
        textDisplayMeta.scale = Vec(1.0, 1.0, 1.0)

        textDisplayMeta.billboardRenderConstraints = AbstractDisplayMeta.BillboardConstraints.CENTER
        data.textDisplay = TextDisplay(textDisplay.uuid, displayPos)
    }

    private fun beaconText(player: Player): Component {
        val data = player.getData()!!
        val text = StringBuilder().append("&b${player.username}&f's Beacon")
            .append("\n&bRank: ${data.rank.prefix}")
            .append("\n&bMoney: &a${data.money}")

        return text.toString().toComponent()

    }

}