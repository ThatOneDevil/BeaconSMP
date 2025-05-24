import commands.CommandsLoader
import events.EventLoader
import net.minestom.server.MinecraftServer
import net.minestom.server.coordinate.Pos
import net.minestom.server.entity.GameMode
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent
import net.minestom.server.extras.MojangAuth
import net.minestom.server.instance.InstanceContainer
import net.minestom.server.instance.LightingChunk
import net.minestom.server.instance.anvil.AnvilLoader
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import net.minestom.server.utils.time.TimeUnit
import tablist.TabListLoader
import tablist.TeamTablistManager
import java.time.Duration

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

    MinecraftServer.getBenchmarkManager().enable(Duration.of(10, TimeUnit.SECOND));
    MinecraftServer.setBrandName("§b§lBeacon§3§lSMP§f")

    ServerInfoBossBar()
    CommandsLoader()
    EventLoader()
    TabListLoader()
    TeamTablistManager.setupTeams()
    MojangAuth.init();

    minecraftServer.start("0.0.0.0", 25565)

    MinecraftServer.getSchedulerManager().buildShutdownTask {
        instanceContainer.saveChunksToStorage()
        println("Saving chunks...")
        MinecraftServer.stopCleanly()

    }

}