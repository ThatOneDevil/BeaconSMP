package tablist

import net.kyori.adventure.text.Component
import net.minestom.server.MinecraftServer
import net.minestom.server.adventure.audience.Audiences
import net.minestom.server.entity.Player
import net.minestom.server.event.EventNode
import net.minestom.server.utils.time.TimeUnit
import playerData.PlayerDataManager.getData


class TabListLoader {

    private val node = EventNode.all("serverScoreboard")

    init {
        val eventHandler = MinecraftServer.getGlobalEventHandler()
        eventHandler.addChild(node)


        val header = Component.text("\n       §b§lBeacon§3§lSMP§f        \n")
        val footer = Component.text("\n       §7Made in §bMinestom §7by: §bThatOneDevil       \n")

        MinecraftServer.getSchedulerManager().buildTask() {
            if (MinecraftServer.getConnectionManager().onlinePlayerCount == 0) {
                return@buildTask
            }

            Audiences.players().forEachAudience {
                it as Player
                it.sendPlayerListHeaderAndFooter(header, footer)
                it.getData()?.rank?.let { it1 -> TeamTablistManager.assignPlayerToTeam(it, it1) }
            }

        }.repeat(1, TimeUnit.SECOND).schedule()

    }
}