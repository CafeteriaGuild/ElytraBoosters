package net.notjustanna.elytraboosters

import net.notjustanna.elytraboosters.item.BoosterModelPredicateProvider
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.`object`.builder.v1.client.model.FabricModelPredicateProviderRegistry

object ElytraBoostersClient : ClientModInitializer {
    override fun onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(ElytraBoostersPacketHandler.sync) { client, handler, buf, responseSender ->
            ElytraBoostersPacketHandler.updateConfigs(buf.readNbt()!!)
        }

        for (item in ElytraBoostersItems.boosterItems()) {
            FabricModelPredicateProviderRegistry.register(
                item, identifier("active_booster"), BoosterModelPredicateProvider
            )
        }
    }
}