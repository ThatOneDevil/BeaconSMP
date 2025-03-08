package defaultCommands

import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType.*
import net.minestom.server.entity.Entity
import net.minestom.server.entity.Player
import net.minestom.server.inventory.PlayerInventory
import net.minestom.server.inventory.TransactionOption
import net.minestom.server.item.ItemStack
import net.minestom.server.utils.entity.EntityFinder
import kotlin.math.min


class GiveCommand : Command("give") {
    init {
        setDefaultExecutor { sender, _ ->
            sender.sendMessage("Usage: /give <player> <item> [count]")
        }

        addSyntax({ sender, context ->
            if (sender is Player && sender.permissionLevel < 2) {
                sender.sendMessage(
                    net.kyori.adventure.text.Component.text(
                        "You don't have permission to use this command.",
                        NamedTextColor.RED
                    )
                )
                return@addSyntax
            }

            val entityFinder: EntityFinder = context["target"]
            var count: Int = context["count"]
            count = min(count.toDouble(), (PlayerInventory.INVENTORY_SIZE * 64).toDouble()).toInt()
            val itemStack: ItemStack = context["item"]

            val itemStacks: MutableList<ItemStack> = if (count <= 64) {
                listOf(itemStack.withAmount(count)).toMutableList()
            } else {
                val stacks = mutableListOf<ItemStack>()
                while (count > 64) {
                    stacks.add(itemStack.withAmount(64))
                    count -= 64
                }
                stacks.add(itemStack.withAmount(count))
                stacks
            }

            val targets: List<Entity> = entityFinder.find(sender)
            for (target in targets) {
                if (target is Player) {
                    target.inventory.addItemStacks(itemStacks, TransactionOption.ALL)
                }
            }
        }, Entity("target").onlyPlayers(true), ItemStack("item"), Integer("count").setDefaultValue(1))
    }
}