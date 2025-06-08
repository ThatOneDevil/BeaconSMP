package playerData.gson.typeAdapters

import com.google.gson.*
import net.minestom.server.coordinate.Point
import net.minestom.server.coordinate.Vec
import net.minestom.server.item.Material
import java.lang.reflect.Type

object MaterialAdapter : JsonDeserializer<Material>, JsonSerializer<Material> {
    override fun deserialize(json: JsonElement?, type: Type?, context: JsonDeserializationContext?): Material? {
        if (json == null) throw JsonParseException("JSON cannot be null.")
        if (json !is JsonObject) throw JsonParseException("Not a valid JSON Object.")

        val materialName = json.get("material")?.asString ?: throw JsonParseException("Material name not found.")

        return Material.fromKey(materialName)

    }

    override fun serialize(material: Material?, type: Type?, context: JsonSerializationContext?): JsonElement {
        if (material == null) throw JsonParseException("Point cannot be null.")

        return JsonObject().apply {
            addProperty("material", material.key().asString())
        }
    }
}