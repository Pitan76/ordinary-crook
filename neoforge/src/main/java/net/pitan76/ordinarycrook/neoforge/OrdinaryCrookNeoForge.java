package net.pitan76.ordinarycrook.neoforge;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.pitan76.ordinarycrook.OrdinaryCrook;

@Mod(OrdinaryCrook.MOD_ID)
public class OrdinaryCrookNeoForge {
    public OrdinaryCrookNeoForge(ModContainer modContainer) {
        new OrdinaryCrook();
    }
}