package playerData

import displayEntities.TextDisplay
import spawner.SpawnerData
import java.math.BigInteger
import java.util.UUID

data class PlayerDataClass(
    val uuid: UUID,
    var rank: Ranks = Ranks.PLAYER,
    var money: BigInteger = BigInteger.ZERO,
    var textDisplay: TextDisplay? = null,
    var placedSpawners: MutableSet<SpawnerData> = mutableSetOf()
)
