package playerData

import net.minestom.server.entity.Player
import playerData.gson.GSON
import java.util.UUID
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap

object PlayerDataManager {

    private const val PATH = "playerData"
    private val playerDataMap: ConcurrentHashMap<UUID, PlayerDataClass> = ConcurrentHashMap()

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
                playerDataMap[playerData.uuid] = playerData
                savePlayerData(playerData).join() // Save the new data
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
            playerDataMap[playerData.uuid] = playerData // Update cache with latest data
        }
    }

    fun deletePlayerData(playerData: PlayerDataClass) {
        playerDataMap.remove(playerData.uuid)
        val file = java.io.File("$PATH/${playerData.uuid}.json")
        if (file.exists()) file.delete() // Delete file from disk
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
    }

    fun getDataClass(player: Player): PlayerDataClass? {
        return playerDataMap[player.uuid]
    }

    fun Player.getData(): PlayerDataClass? {
        return playerDataMap[this.uuid]
    }
}