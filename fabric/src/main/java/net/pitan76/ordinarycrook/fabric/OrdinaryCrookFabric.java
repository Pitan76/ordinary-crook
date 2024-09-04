package net.pitan76.ordinarycrook.fabric;

import net.fabricmc.api.ModInitializer;
import net.pitan76.ordinarycrook.OrdinaryCrook;

public class OrdinaryCrookFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        new OrdinaryCrook();
    }
}