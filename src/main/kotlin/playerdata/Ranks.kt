package playerdata

import java.util.*


enum class Ranks(val prefix: String, val weight: Int, val color: String) {

    PLAYER("ᴘʟᴀʏᴇʀ", 0, "#BFBFBF"),
    ADMIN("ᴀᴅᴍɪɴ", 1, "#FF6961"),
    OWNER("ᴏᴡɴᴇʀ", 2, "#A7C7E7");

    companion object {
        fun getRankByWeight(weight: Int): Ranks {
            return entries.first { it.weight == weight }
        }

    }


}