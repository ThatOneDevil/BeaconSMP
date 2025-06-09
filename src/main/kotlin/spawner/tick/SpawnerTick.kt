package spawner.tick

import spawner.SpawnerData

class SpawnerTick(private var spawnerData: SpawnerData) : Runnable {

    override fun run() {
        val chances = spawnerData.drops.dropChances
        val items = spawnerData.drops.items

        for (i in items.indices) {
            val item = items[i]
            val chance = chances[i]

            if (Math.random() < chance) {
                println("Dropping item: ${item.name()} with chance: $chance")
            }
        }
    }
}