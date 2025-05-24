package playerData.gson.typeAdapters

import com.google.gson.*
import net.minestom.server.coordinate.Point
import net.minestom.server.coordinate.Vec
import java.lang.reflect.Type

object PointAdapter : JsonDeserializer<Point>, JsonSerializer<Point> {
    override fun deserialize(json: JsonElement?, type: Type?, context: JsonDeserializationContext?): Point {
        if (json == null) throw JsonParseException("JSON cannot be null.")
        if (json !is JsonObject) throw JsonParseException("Not a valid JSON Object.")

        val x = json.get("x")
        val y = json.get("y")
        val z = json.get("z")

        if (!x.isJsonPrimitive && !(x as JsonPrimitive).isNumber) throw JsonParseException("\"x\" not of type number.")
        if (!y.isJsonPrimitive && !(y as JsonPrimitive).isNumber) throw JsonParseException("\"y\" not of type number.")
        if (!z.isJsonPrimitive && !(z as JsonPrimitive).isNumber) throw JsonParseException("\"z\" not of type number.")

        return Vec(x.asDouble, y.asDouble, z.asDouble)
    }

    override fun serialize(point: Point?, type: Type?, context: JsonSerializationContext?): JsonElement {
        if (point == null) throw JsonParseException("Point cannot be null.")

        return JsonObject().apply {
            addProperty("x", point.x())
            addProperty("y", point.y())
            addProperty("z", point.z())
        }
    }
}