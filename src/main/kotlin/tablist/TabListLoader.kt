package tablist


import net.kyori.adventure.text.Component
import net.minestom.server.MinecraftServer
import net.minestom.server.event.EventNode
import net.minestom.server.scoreboard.Sidebar

class TabList {

    private val node = EventNode.all("serverScoreboard")

    init {
        val eventHandler = MinecraftServer.getGlobalEventHandler()
        eventHandler.addChild(node)

        val sidebar = Sidebar(Component.text("§7Test "))


    }
}