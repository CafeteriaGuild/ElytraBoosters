package net.notjustanna.elytraboosters.data.impl

import net.notjustanna.elytraboosters.data.ElytraBoostersData
import net.notjustanna.elytraboosters.item.BoosterItem

class FuelPelletDataImpl(
    override val standardPellets: Int,
    override val fastPellets: Int,
    override val slowPellets: Int,
    lazyResult: () -> BoosterItem
) : ElytraBoostersData.FuelPelletData {
    override val resultItem by lazy(lazyResult)
}