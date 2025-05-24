import beacon.BeaconPlace
import commands.CommandsLoader
import events.EventLoader
import net.minestom.server.MinecraftServer
import net.minestom.server.coordinate.Pos
import net.minestom.server.entity.GameMode
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent
import net.minestom.server.instance.InstanceContainer
import net.minestom.server.instance.LightingChunk
import net.minestom.server.instance.anvil.AnvilLoader
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import tablist.TabListLoader
import tablist.TeamTablistManager

lateinit var instanceContainer: InstanceContainer


fun main() {
    val minecraftServer = MinecraftServer.init()
    val instanceManager = MinecraftServer.getInstanceManager()
    instanceContainer = instanceManager.createInstanceContainer()
    instanceContainer.chunkLoader = AnvilLoader("worlds/world")


    instanceContainer.setChunkSupplier(::LightingChunk)

    val globalEventHandler = MinecraftServer.getGlobalEventHandler()
    globalEventHandler.addListener(AsyncPlayerConfigurationEvent::class.java) { event ->
        val player = event.player
        event.spawningInstance = instanceContainer
        player.gameMode = GameMode.CREATIVE
        player.inventory.addItemStack(ItemStack.of(Material.BEACON))
        player.respawnPoint = Pos(0.0, 42.0, 0.0)
        player.permissionLevel = 4

    }

    ServerInfoBossBar()
    CommandsLoader()
    EventLoader()
    TabListLoader()
    TeamTablistManager.setupTeams()

    minecraftServer.start("0.0.0.0", 25565)

    MinecraftServer.getSchedulerManager().buildShutdownTask {
        instanceContainer.saveChunksToStorage()
        println("Saving chunks...")
        MinecraftServer.stopCleanly()

    }

}