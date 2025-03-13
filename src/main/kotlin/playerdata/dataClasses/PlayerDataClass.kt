package adminCommands.playerdata.dataClasses

import java.util.UUID

data class PlayerDataClass(
    val uuid: UUID,
    val rank: RankDataClass = RankDataClass(uuid)
)
