package net.notjustanna.elytraboosters.data

import me.shedaniel.autoconfig.annotation.Config
import me.shedaniel.autoconfig.annotation.ConfigEntry
import me.shedaniel.autoconfig.ConfigData

@Config(name = "elytraboosters")
class ElytraBoostersConfig : ConfigData {
    @ConfigEntry.Gui.CollapsibleObject
    var forwardLauncher = ForwardLauncherValues()

    @ConfigEntry.Gui.CollapsibleObject
    var standardBooster = BoosterValues(0.1, 1.5, 0.5, 4)

    @ConfigEntry.Gui.CollapsibleObject
    var fastBooster = BoosterValues(0.2, 2.5, 0.6, 2)

    @ConfigEntry.Gui.CollapsibleObject
    var slowBooster = BoosterValues(0.1, 0.9, 0.4, 8)

    class ForwardLauncherValues(
        @ConfigEntry.Gui.Tooltip(count = 2)
        var constantVelocity: Double = 0.9,
        @ConfigEntry.Gui.Tooltip(count = 2)
        var interpolatingVelocity: Double = 0.1,
        @ConfigEntry.Gui.Tooltip(count = 2)
        var frictionFactor: Double = 0.4
    )

    class BoosterValues(
        @ConfigEntry.Gui.Tooltip(count = 2)
        var constantVelocity: Double = 0.1,
        @ConfigEntry.Gui.Tooltip(count = 2)
        var interpolatingVelocity: Double = 1.5,
        @ConfigEntry.Gui.Tooltip(count = 2)
        var frictionFactor: Double = 0.5,
        @ConfigEntry.Gui.Tooltip(count = 2)
        var ticksPerDamage: Int = 4
    )
}