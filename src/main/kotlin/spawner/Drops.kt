package spawner

import net.minestom.server.item.Material

enum class Drops(val items: MutableList<Material>) {

    CREEPER(mutableListOf(Material.GUNPOWDER)),
    SKELETON(mutableListOf(Material.BONE, Material.ARROW)),
    SPIDER(mutableListOf(Material.STRING, Material.SPIDER_EYE)),
    ZOMBIE(mutableListOf(Material.ROTTEN_FLESH)),
    CAVE_SPIDER(mutableListOf(Material.STRING, Material.SPIDER_EYE)),
    ENDERMAN(mutableListOf(Material.ENDER_PEARL)),
    BLAZE(mutableListOf(Material.BLAZE_ROD)),
    GHAST(mutableListOf(Material.GHAST_TEAR)),
    MAGMA_CUBE(mutableListOf(Material.MAGMA_CREAM)),
    SLIME(mutableListOf(Material.SLIME_BALL)),
    WITCH(mutableListOf(Material.GLASS_BOTTLE, Material.REDSTONE, Material.GLOWSTONE_DUST)),

}