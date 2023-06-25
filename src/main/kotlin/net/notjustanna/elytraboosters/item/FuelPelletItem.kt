package net.notjustanna.elytraboosters.item

import net.notjustanna.elytraboosters.ElytraBoostersItems.emptyBooster
import net.notjustanna.elytraboosters.ElytraBoostersItems.fastFuelPellet
import net.notjustanna.elytraboosters.ElytraBoostersItems.slowFuelPellet
import net.notjustanna.elytraboosters.ElytraBoostersItems.standardFuelPellet
import net.notjustanna.elytraboosters.ExpandedPlayerInventory
import net.notjustanna.elytraboosters.data.ElytraBoostersData
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World

class FuelPelletItem(settings: Settings, private val data: ElytraBoostersData.FuelPelletData) : Item(settings) {
    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        if (user.isSneaking && user.inventory is ExpandedPlayerInventory) {
            val inv = user.inventory as ExpandedPlayerInventory
            if (
                inv.count(standardFuelPellet) >= data.standardPellets &&
                inv.count(fastFuelPellet) >= data.fastPellets &&
                inv.count(slowFuelPellet) >= data.slowPellets &&
                inv.takeOneAndReplace(emptyBooster, data.resultItem)
            ) {
                inv.takeFromInventory(standardFuelPellet, data.standardPellets)
                inv.takeFromInventory(fastFuelPellet, data.fastPellets)
                inv.takeFromInventory(slowFuelPellet, data.slowPellets)
            }
        }
        return TypedActionResult.pass(user.getStackInHand(hand))
    }

    @Environment(EnvType.CLIENT)
    override fun appendTooltip(stack: ItemStack?, world: World?, tooltip: MutableList<Text?>, ctx: TooltipContext?) {
        tooltip += Text.translatable("$translationKey.description")
        tooltip += Text.translatable("tooltip.elytraboosters.craft_booster1")
        tooltip += Text.translatable(
            "tooltip.elytraboosters.craft_booster2",
            Text.translatable(data.resultItem.translationKey)
        )
    }
}