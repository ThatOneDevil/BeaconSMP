package playerdata

import playerdata.dataClasses.PlayerDataClass
import com.google.gson.Gson
import net.minestom.server.entity.Player
import java.util.UUID
import java.util.concurrent.CompletableFuture

object PlayerDataManager {

    private val GSON = Gson()
    private const val PATH = "playerdata"
    private val playerDataMap: HashMap<UUID, PlayerDataClass> = hashMapOf()

    fun loadPlayerData(playerData: PlayerDataClass): CompletableFuture<PlayerDataClass> {
        return CompletableFuture.supplyAsync {
            createDataFolder()
            val file = java.io.File("$PATH/${playerData.uuid}.json")
            if (file.exists()) {
                val json = file.readText()
                val loadedData = deserialize(json)
                playerDataMap[playerData.uuid] = loadedData
                loadedData
            } else {
                playerData
            }
        }

    }

    fun savePlayerData(playerData: PlayerDataClass): CompletableFuture<Void> {
        return CompletableFuture.runAsync {
            createDataFolder()
            val file = java.io.File("$PATH/${playerData.uuid}.json")
            val json = serialize(playerData)
            file.writeText(json)
        }
    }

    fun deletePlayerData(playerData: PlayerDataClass) {
        playerDataMap.remove(playerData.uuid)
    }

    fun getOrCreatePlayerData(uuid: UUID): PlayerDataClass {
        return playerDataMap.getOrPut(uuid) { PlayerDataClass(uuid) }
    }

    private fun serialize(playerData: PlayerDataClass): String {
        return GSON.toJson(playerData, PlayerDataClass::class.java)
    }

    private fun deserialize(json: String): PlayerDataClass {
        return GSON.fromJson(json, PlayerDataClass::class.java)
    }

    private fun createDataFolder() {
        val folder = java.io.File(PATH)
        if (!folder.exists()) {
            folder.mkdirs()
        }
        println(folder.path)
    }

    fun getDataClass(player: Player): PlayerDataClass? {
        return playerDataMap[player.uuid]
    }

}