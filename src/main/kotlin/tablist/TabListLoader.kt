package tablist

import net.kyori.adventure.text.Component
import net.minestom.server.MinecraftServer
import net.minestom.server.adventure.audience.Audiences
import net.minestom.server.event.EventNode
import net.minestom.server.utils.time.TimeUnit


class TabListLoader {

    private val node = EventNode.all("serverScoreboard")

    init {
        val eventHandler = MinecraftServer.getGlobalEventHandler()
        eventHandler.addChild(node)

        MinecraftServer.getSchedulerManager().buildTask() {
            if (MinecraftServer.getConnectionManager().onlinePlayerCount == 0) {
                return@buildTask
            }

            val header = Component.text("\n       §b§lBeacon§3§lSMP§f        \n")
            val footer = Component.text("\n       §7Made in §bMinestom §7by: §bThatOneDevil       \n")

            Audiences.players().sendPlayerListHeaderAndFooter(header, footer)

        }.repeat(20, TimeUnit.SERVER_TICK).schedule()

    }
}