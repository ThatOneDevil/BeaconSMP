package playerdata.dataClasses

import playerdata.Ranks
import java.util.UUID

data class PlayerDataClass(
    val uuid: UUID,
    var rank: Ranks = Ranks.PLAYER
)
