package net.notjustanna.elytraboosters.item

import net.minecraft.client.item.ClampedModelPredicateProvider
import net.notjustanna.elytraboosters.active
import net.minecraft.client.item.ModelPredicateProvider
import net.minecraft.client.world.ClientWorld
import net.minecraft.entity.LivingEntity
import net.minecraft.item.ItemStack

object BoosterModelPredicateProvider : ClampedModelPredicateProvider {

    override fun unclampedCall(stack: ItemStack?, world: ClientWorld?, entity: LivingEntity?, seed: Int): Float {
        return if (stack?.nbt?.active == true) 1f else 0f
    }
}
