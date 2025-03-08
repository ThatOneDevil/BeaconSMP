import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.MinecraftServer
import net.minestom.server.adventure.MinestomAdventure
import net.minestom.server.adventure.audience.Audiences
import net.minestom.server.event.EventNode
import net.minestom.server.event.server.ServerTickMonitorEvent
import net.minestom.server.monitoring.TickMonitor
import net.minestom.server.utils.MathUtils
import net.minestom.server.utils.time.TimeUnit
import java.util.*
import java.util.concurrent.atomic.AtomicReference
import java.util.function.BiFunction

class ServerInfoBossBar {

    private val DEMO_NODE = EventNode.all("serverInfo")
    private val LAST_TICK = AtomicReference<TickMonitor>()
    private var bossBar: BossBar

    init {
        val eventHandler = MinecraftServer.getGlobalEventHandler()
        eventHandler.addChild(DEMO_NODE)

        MinestomAdventure.AUTOMATIC_COMPONENT_TRANSLATION = true
        MinestomAdventure.COMPONENT_TRANSLATOR = BiFunction { c: Component?, _: Locale? -> c }

        eventHandler.addListener(ServerTickMonitorEvent::class.java) { event ->
            LAST_TICK.set(event.tickMonitor)
        }

        val benchmarkManager = MinecraftServer.getBenchmarkManager()

        bossBar = BossBar.bossBar(Component.text("Initializing..."), 0f, BossBar.Color.BLUE, BossBar.Overlay.PROGRESS)


        MinecraftServer.getSchedulerManager().buildTask {
            val ramUsage: Long = (benchmarkManager.getUsedMemory() / 1e6).toLong()
            val tickMonitor = LAST_TICK.get()

            if (tickMonitor != null || MinecraftServer.getConnectionManager().onlinePlayerCount == 0) {
                val text = Component.text("RAM USAGE: ", NamedTextColor.GRAY)
                    .append(Component.text("$ramUsage MB", NamedTextColor.WHITE))
                    .append(Component.text(" | ", NamedTextColor.DARK_GRAY))
                    .append(Component.text("TICK TIME: ", NamedTextColor.GRAY))
                    .append(Component.text("${MathUtils.round(tickMonitor.tickTime, 2)} ms", NamedTextColor.WHITE))
                bossBar.name(text)
                Audiences.players().showBossBar(bossBar)
            }
        }.repeat(20, TimeUnit.SERVER_TICK).schedule()
    }
}
