package playerData.gson

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.GsonBuilder
import net.minestom.server.coordinate.Point
import playerData.gson.typeAdapters.PointAdapter


object ExclusionStrategy : ExclusionStrategy {
    override fun shouldSkipField(attributes: FieldAttributes) = attributes.getAnnotation(Exclude::class.java) != null

    override fun shouldSkipClass(clazz: Class<*>) = false
}

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class Exclude

val GSON = GsonBuilder()
    .setPrettyPrinting()
    .addSerializationExclusionStrategy(ExclusionStrategy)
    .registerTypeAdapter(Point::class.java, PointAdapter)
    .create()!!

fun Any.toJson(): String = GSON.toJson(this)