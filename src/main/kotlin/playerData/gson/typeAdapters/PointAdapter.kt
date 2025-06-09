package playerData.gson.typeAdapters

import com.google.gson.*
import net.minestom.server.coordinate.Point
import net.minestom.server.coordinate.Vec
import java.lang.reflect.Type

object PointAdapter : JsonDeserializer<Point>, JsonSerializer<Point> {
    override fun deserialize(json: JsonElement?, type: Type?, context: JsonDeserializationContext?): Point {
        if (json == null) throw JsonParseException("JSON cannot be null.")
        if (json !is JsonArray) throw JsonParseException("Not a valid JSON Object.")

        val x = json.get(0)
        val y = json.get(1)
        val z = json.get(2)

        if (!x.isJsonPrimitive && !(x as JsonPrimitive).isNumber) throw JsonParseException("\"x\" not of type number.")
        if (!y.isJsonPrimitive && !(y as JsonPrimitive).isNumber) throw JsonParseException("\"y\" not of type number.")
        if (!z.isJsonPrimitive && !(z as JsonPrimitive).isNumber) throw JsonParseException("\"z\" not of type number.")

        return Vec(x.asDouble, y.asDouble, z.asDouble)
    }

    override fun serialize(point: Point?, type: Type?, context: JsonSerializationContext?): JsonElement {
        if (point == null) throw JsonParseException("Point cannot be null.")

        return JsonArray().apply {
            add(point.x())
            add(point.y())
            add(point.z())
        }
    }
}