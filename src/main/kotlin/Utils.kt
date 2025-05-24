import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage

object Utils {

    private fun convertLegacyToMiniMessage(input: String): Component {
        val replacements = mapOf(
            "&0" to "<black>", "&1" to "<dark_blue>", "&2" to "<dark_green>", "&3" to "<dark_aqua>",
            "&4" to "<dark_red>", "&5" to "<dark_purple>", "&6" to "<gold>", "&7" to "<gray>",
            "&8" to "<dark_gray>", "&9" to "<blue>", "&a" to "<green>", "&b" to "<aqua>",
            "&c" to "<red>", "&d" to "<light_purple>", "&e" to "<yellow>", "&f" to "<white>",
            "&l" to "<bold>", "&o" to "<italic>", "&n" to "<underlined>", "&m" to "<strikethrough>",
            "&r" to "<reset>"
        )

        val regex = Regex(replacements.keys.joinToString("|") { Regex.escape(it) })

        val convertedInput = regex.replace(input) { matchResult ->
            replacements[matchResult.value] ?: matchResult.value
        }

        return MiniMessage.miniMessage().deserialize(convertedInput)
    }

    /**
     * Converts a string to a MiniMessage component.
     *
     * @return A miniMessage component
     */
    fun String.toComponent(): Component {
        return convertLegacyToMiniMessage(this)
    }

    /**
     * Converts a MiniMessage component to a serialized string
     *
     * @return A serialized string
     */
    fun Component.toRawString(): String {
        return MiniMessage.miniMessage().serialize(this)
    }
}