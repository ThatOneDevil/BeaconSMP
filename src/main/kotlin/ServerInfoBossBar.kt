import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.MinecraftServer
import net.minestom.server.adventure.audience.Audiences
import net.minestom.server.event.EventNode
import net.minestom.server.event.server.ServerTickMonitorEvent
import net.minestom.server.monitoring.TickMonitor
import net.minestom.server.utils.MathUtils
import net.minestom.server.utils.time.TimeUnit
import java.util.concurrent.atomic.AtomicReference

class ServerInfoBossBar {

    private val node = EventNode.all("serverInfo")
    private val LAST_TICK = AtomicReference<TickMonitor>()
    private var bossBar: BossBar

    init {
        val eventHandler = MinecraftServer.getGlobalEventHandler()
        eventHandler.addChild(node)

        eventHandler.addListener(ServerTickMonitorEvent::class.java) { event ->
            LAST_TICK.set(event.tickMonitor)
        }

        val benchmarkManager = MinecraftServer.getBenchmarkManager()

        bossBar = BossBar.bossBar(Component.text("Initializing..."), 0f, BossBar.Color.BLUE, BossBar.Overlay.PROGRESS)

        var lastUsedRam = -1L
        var lastTickTime = -1.0
        val maxRam = Runtime.getRuntime().maxMemory() / (1024 * 1024)

        MinecraftServer.getSchedulerManager().buildTask {
            if (MinecraftServer.getConnectionManager().onlinePlayerCount == 0) {
                return@buildTask
            }

            val usedRam: Long = benchmarkManager.usedMemory / (1024 * 1024)
            val ramProgress = (usedRam.toDouble() / maxRam).coerceIn(0.0, 1.0).toFloat()

            val tickMonitor = LAST_TICK.get() ?: return@buildTask
            val tickTime = tickMonitor.tickTime

            bossBar.progress(ramProgress)
            if (usedRam != lastUsedRam || tickTime != lastTickTime) {
                lastUsedRam = usedRam
                lastTickTime = tickTime

                val text = Component.text()
                    .append(Component.text("RAM: ", NamedTextColor.GRAY))
                    .append(Component.text("$usedRam/$maxRam MB", NamedTextColor.WHITE))
                    .append(Component.text(" | ", NamedTextColor.DARK_GRAY))
                    .append(Component.text("TICK: ", NamedTextColor.GRAY))
                    .append(Component.text("${MathUtils.round(tickTime, 2)} ms", NamedTextColor.WHITE))
                    .build()

                bossBar.name(text)
            }

            Audiences.players().showBossBar(bossBar)

        }.repeat(1, TimeUnit.SERVER_TICK).schedule()



    }
}
