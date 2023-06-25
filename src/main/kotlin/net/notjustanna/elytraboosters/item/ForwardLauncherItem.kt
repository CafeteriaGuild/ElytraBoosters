package net.notjustanna.elytraboosters.item

import net.notjustanna.elytraboosters.ElytraBoostersItems.emptyBooster
import net.notjustanna.elytraboosters.data.ElytraBoostersData.BoosterVelocity
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.sound.SoundCategory.PLAYERS
import net.minecraft.sound.SoundEvents.BLOCK_PISTON_EXTEND
import net.minecraft.sound.SoundEvents.ENTITY_ITEM_BREAK
import net.minecraft.text.Text
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World


class ForwardLauncherItem(settings: Settings, private val data: BoosterVelocity) : Item(settings) {
    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val stack = user.getStackInHand(hand)
        if (user.isOnGround) {
            world.playSound(user, user.blockPos, BLOCK_PISTON_EXTEND, PLAYERS, 0.8f, 1.2f)
            data.applyBoost(user)
            user.isOnGround = false
            user.checkFallFlying()
            if (!user.isCreative && stack.nbt?.getBoolean("Unbreakable") != true) {
                stack.damage += 1
                if (stack.damage == maxDamage) {
                    world.playSound(user, user.blockPos, ENTITY_ITEM_BREAK, PLAYERS, 0.8f, 1.2f)
                    return TypedActionResult.success(ItemStack(emptyBooster))
                }
            }
            return TypedActionResult.success(stack)
        }
        return TypedActionResult.fail(stack)
    }

    @Environment(EnvType.CLIENT)
    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, ctx: TooltipContext) {
        tooltip += Text.translatable("$translationKey.description")
        tooltip += Text.translatable("tooltip.elytraboosters.activate_forward")
    }
}