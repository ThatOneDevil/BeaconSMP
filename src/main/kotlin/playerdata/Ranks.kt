package adminCommands.playerdata

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import java.io.Serializable

enum class Ranks(val prefix: Component, val weight: Int) {

    PLAYER(Component.text("ᴘʟᴀʏᴇʀ").color(TextColor.color(0xBFBFBF)), 0),
    ADMIN(Component.text("ᴀᴅᴍɪɴ").color(TextColor.color(0xFF6961)), 1),
    OWNER(Component.text("ᴏᴡɴᴇʀ").color(TextColor.color(0xA7C7E7)), 2);


    companion object {
        fun getRankByWeight(weight: Int): Ranks {
            return entries.first { it.weight == weight }
        }

    }


}