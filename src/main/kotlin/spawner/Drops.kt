package spawner

import net.minestom.server.item.Material

enum class Drops(val items: MutableList<Material>, val dropChances: MutableList<Float>) {

    CREEPER(mutableListOf(Material.GUNPOWDER), mutableListOf(1.0f)),
    SKELETON(mutableListOf(Material.BONE, Material.ARROW), mutableListOf(1.0f, 0.5f)),
    SPIDER(mutableListOf(Material.STRING, Material.SPIDER_EYE), mutableListOf(1.0f, 0.5f)),
    ZOMBIE(mutableListOf(Material.ROTTEN_FLESH), mutableListOf(1.0f)),
    CAVE_SPIDER(mutableListOf(Material.STRING, Material.SPIDER_EYE), mutableListOf(1.0f, 0.5f)),
    ENDERMAN(mutableListOf(Material.ENDER_PEARL), mutableListOf(1.0f)),
    BLAZE(mutableListOf(Material.BLAZE_ROD), mutableListOf(1.0f)),
    GHAST(mutableListOf(Material.GHAST_TEAR), mutableListOf(1.0f)),
    MAGMA_CUBE(mutableListOf(Material.MAGMA_CREAM), mutableListOf(1.0f)),
    SLIME(mutableListOf(Material.SLIME_BALL), mutableListOf(1.0f)),
    WITCH(mutableListOf(Material.GLASS_BOTTLE, Material.REDSTONE, Material.GLOWSTONE_DUST), mutableListOf(0.33f, 0.33f, 0.33f));


}