package ml.pkom.ordinarycrook.fabric;

import ml.pkom.ordinarycrook.OrdinaryCrook;
import net.fabricmc.api.ModInitializer;

public class OrdinaryCrookFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        OrdinaryCrook.init();
    }
}