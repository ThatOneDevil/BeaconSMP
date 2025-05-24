package displayEntities

import net.minestom.server.coordinate.Point
import java.util.UUID

data class TextDisplay(
    var uuid: UUID,
    var position: Point?,
    var beaconBlock: Point? = position?.add(-0.5, -1.0, -0.5)
)
