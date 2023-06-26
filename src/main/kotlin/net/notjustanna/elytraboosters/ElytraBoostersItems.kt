package net.notjustanna.elytraboosters

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.item.ItemGroups
import net.notjustanna.elytraboosters.ElytraBoosters.data
import net.notjustanna.elytraboosters.data.ElytraBoostersData.BoosterType.*
import net.notjustanna.elytraboosters.item.BoosterItem
import net.notjustanna.elytraboosters.item.ForwardLauncherItem
import net.notjustanna.elytraboosters.item.FuelPelletItem
import net.notjustanna.elytraboosters.item.LoreItem

@Suppress("MemberVisibilityCanBePrivate")
object ElytraBoostersItems {
    val emptyBooster = LoreItem(toolSettings())

    val fastBooster = BoosterItem(toolSettings().maxDamage(100), data.booster(FAST))
    //val fastBoosterActive = ActiveBoosterItem(Item.Settings().maxDamage(100), data.booster(FAST))

    val standardBooster = BoosterItem(toolSettings().maxDamage(200), data.booster(STANDARD))
    //val standardBoosterActive = ActiveBoosterItem(Item.Settings().maxDamage(200), data.booster(STANDARD))

    val slowBooster = BoosterItem(toolSettings().maxDamage(400), data.booster(SLOW))
    //val slowBoosterActive = ActiveBoosterItem(Item.Settings().maxDamage(400), data.booster(SLOW))

    val forwardLauncher = ForwardLauncherItem(toolSettings().maxDamage(128), data.launcherVelocity)

    val standardFuelPellet = FuelPelletItem(itemSettings(), data.fuelPellet(STANDARD))
    val slowFuelPellet = FuelPelletItem(itemSettings(), data.fuelPellet(SLOW))
    val fastFuelPellet = FuelPelletItem(itemSettings(), data.fuelPellet(FAST))

    fun register() {
        identifier("booster_empty").item(emptyBooster)
        identifier("booster_standard").item(standardBooster)
        identifier("booster_fast").item(fastBooster)
        identifier("booster_slow").item(slowBooster)
        identifier("forward_launcher").item(forwardLauncher)

        identifier("fuel_pellet_standard").item(standardFuelPellet)
        identifier("fuel_pellet_fast").item(fastFuelPellet)
        identifier("fuel_pellet_slow").item(slowFuelPellet)

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register { group ->
            group.add(emptyBooster)
            group.add(fastBooster)
            group.add(standardBooster)
            group.add(slowBooster)
            group.add(forwardLauncher)
            group.add(standardFuelPellet)
            group.add(slowFuelPellet)
            group.add(fastFuelPellet)
        }
    }

    fun boosterItems() = listOf(fastBooster, standardBooster, slowBooster)
}