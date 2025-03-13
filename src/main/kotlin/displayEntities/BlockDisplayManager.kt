package displayEntities

import java.util.UUID

class BlockDisplayManager {

    private var blockDisplaysMap: HashMap<UUID, List<BlockDisplay>> = hashMapOf()

    fun saveBlockDisplay(blockDisplay: BlockDisplay, uuid: UUID) {
        val blockDisplays = blockDisplaysMap.getOrDefault(uuid, mutableListOf())
        blockDisplaysMap[uuid] = blockDisplays + blockDisplay
    }

    fun deleteBlockDisplay(blockDisplay: BlockDisplay, uuid: UUID) {
        blockDisplaysMap[uuid]?.let { it ->
            blockDisplaysMap[uuid] = it.filter { it != blockDisplay }
        }
    }


}