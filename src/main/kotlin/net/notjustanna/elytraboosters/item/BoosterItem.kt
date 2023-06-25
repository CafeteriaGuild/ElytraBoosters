package net.notjustanna.elytraboosters.item

import net.notjustanna.elytraboosters.ElytraBoostersItems
import net.notjustanna.elytraboosters.ExpandedPlayerInventory
import net.notjustanna.elytraboosters.active
import net.notjustanna.elytraboosters.data.ElytraBoostersData
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.particle.ParticleTypes
import net.minecraft.text.Text
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World

class BoosterItem(settings: Settings, private val data: ElytraBoostersData.BoosterData) : Item(settings) {
    override fun use(world: World?, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val stack = user.getStackInHand(hand)

        if (stack?.nbt?.active == true) {
            stack.orCreateNbt.active = false
            return TypedActionResult.success(stack)
        } else {
            if (user.isFallFlying) {
                (user.inventory as ExpandedPlayerInventory).ensureOnlyActiveBooster(null)
                stack.orCreateNbt.active = true
                return TypedActionResult.success(stack)
            }
            return TypedActionResult.fail(stack)
        }
    }

    override fun inventoryTick(stack: ItemStack, world: World, user: Entity, slot: Int, selected: Boolean) {
        if (stack.nbt?.active == true && user is PlayerEntity) {
            val inv = user.inventory as ExpandedPlayerInventory
            if (user.isFallFlying) {
                val ticksLeft = stack.nbt?.getInt("TicksLeft") ?: 0
                val unbreakable = stack.nbt?.getBoolean("Unbreakable") ?: false
                if (world.isClient && ticksLeft % 5 == 1) {
                    world.addParticle(
                        ParticleTypes.FIREWORK,
                        user.x, user.y - 0.3, user.z,
                        user.random.nextGaussian() * 0.05,
                        -user.velocity.y * 0.5,
                        user.random.nextGaussian() * 0.05
                    )
                }
                when {
                    user.isCreative || unbreakable -> {
                        data.applyBoost(user)
                        inv.ensureOnlyActiveBooster(stack)
                    }
                    ticksLeft > 0 -> {
                        stack.orCreateNbt.putInt("TicksLeft", ticksLeft - 1)
                        data.applyBoost(user)
                        inv.ensureOnlyActiveBooster(stack)
                    }
                    stack.damage < stack.maxDamage -> {
                        stack.damage++
                        stack.orCreateNbt.putInt("TicksLeft", data.ticksPerDamage)
                        data.applyBoost(user)
                        inv.ensureOnlyActiveBooster(stack)
                    }
                    else -> {
                        inv.findAndReplace(stack, ItemStack(ElytraBoostersItems.emptyBooster))
                    }
                }
                return
            }
            stack.orCreateNbt.active = false
        }
    }

    override fun getTranslationKey(stack: ItemStack?): String {
        if (stack?.nbt?.active == true) {
            return super.getTranslationKey(stack) + "_active"
        }
        return super.getTranslationKey(stack)
    }

    @Environment(EnvType.CLIENT)
    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, ctx: TooltipContext) {
        tooltip += Text.translatable("$translationKey.description")

        if (stack.nbt?.active == true) {
            tooltip += Text.translatable("tooltip.elytraboosters.deactivate_booster")
            tooltip += Text.translatable("tooltip.elytraboosters.autodeactivate_booster")
        } else {
            tooltip += Text.translatable("tooltip.elytraboosters.activate_booster")
        }

        if (ctx.isAdvanced) {
            tooltip += Text.translatable("tooltip.elytraboosters.time_left", data.secondsLeft(stack))
        }
    }
}