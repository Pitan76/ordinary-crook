package ml.pkom.ordinarycrook.forge;

import dev.architectury.platform.forge.EventBuses;
import ml.pkom.ordinarycrook.OrdinaryCrook;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(OrdinaryCrook.MOD_ID)
public class OrdinaryCrookForge {
    public OrdinaryCrookForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(OrdinaryCrook.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        OrdinaryCrook.init();
    }
}