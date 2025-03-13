package adminCommands.playerdata

import adminCommands.playerdata.dataClasses.PlayerDataClass
import com.google.gson.Gson
import java.util.UUID

object PlayerDataManager {

    private val GSON = Gson()
    private val playerDataMap: HashMap<UUID, PlayerDataClass> = hashMapOf()

    fun savePlayerData(playerData: PlayerDataClass) {
        playerDataMap[playerData.uuid] = playerData
    }

    fun deletePlayerData(playerData: PlayerDataClass) {
        playerDataMap.remove(playerData.uuid)
    }

    fun getPlayerData(uuid: UUID): PlayerDataClass? {
        return playerDataMap[uuid]
    }

    fun serialize(playerData: PlayerDataClass): String {
        return GSON.toJson(playerData, PlayerDataClass::class.java)
    }

    fun deserialize(json: String): PlayerDataClass {
        return GSON.fromJson(json, PlayerDataClass::class.java)
    }

}