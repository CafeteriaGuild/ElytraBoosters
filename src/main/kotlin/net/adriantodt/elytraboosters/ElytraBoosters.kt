package net.adriantodt.elytraboosters

import me.shedaniel.autoconfig.AutoConfig
import me.shedaniel.autoconfig.ConfigHolder
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer
import net.adriantodt.elytraboosters.data.ElytraBoostersConfig
import net.adriantodt.elytraboosters.data.ElytraBoostersData
import net.adriantodt.elytraboosters.data.impl.ElytraBoostersDataImpl
import net.fabricmc.api.ModInitializer
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Suppress("MemberVisibilityCanBePrivate")
object ElytraBoosters : ModInitializer {
    val logger: Logger = LogManager.getLogger(ElytraBoosters.javaClass)

    val configHolder: ConfigHolder<ElytraBoostersConfig> =
        AutoConfig.register(ElytraBoostersConfig::class.java, ::JanksonConfigSerializer)

    val data: ElytraBoostersData = ElytraBoostersDataImpl(configHolder.config)

    override fun onInitialize() {
        ElytraBoostersItems.register()
    }
}
