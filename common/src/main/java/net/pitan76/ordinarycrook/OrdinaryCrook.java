package net.pitan76.ordinarycrook;

import net.pitan76.mcpitanlib.api.CommonModInitializer;
import net.pitan76.mcpitanlib.api.item.v2.CompatibleItemSettings;
import net.pitan76.mcpitanlib.api.item.v3.VanillaCompatToolMaterial;
import net.pitan76.mcpitanlib.midohra.item.ItemGroups;
import net.pitan76.mcpitanlib.midohra.item.ItemWrapper;
import net.pitan76.mcpitanlib.midohra.registry.MidohraRegistry;
import net.pitan76.ordinarycrook.item.BaseCrook;

public class OrdinaryCrook extends CommonModInitializer {
    public static final String MOD_ID = "ordinarycrook";
    public static final String MOD_NAME = "Ordinary Crook";

    public static OrdinaryCrook INSTANCE;
    public static MidohraRegistry registry;

    public static ItemWrapper WOODEN_CROOK;
    public static ItemWrapper BONE_CROOK;
    public static ItemWrapper STONE_CROOK;
    public static ItemWrapper BLAZE_ROD_CROOK;
    public static ItemWrapper WITHERED_BONE_CROOK;

    public void init() {
        INSTANCE = this;
        registry = MidohraRegistry.of(super.registry);

        WOODEN_CROOK = registry.registerRawItem("wooden_crook", () -> new BaseCrook(VanillaCompatToolMaterial.WOOD, CompatibleItemSettings.of(compatId("wooden_crook")).addGroup(ItemGroups.TOOLS), 1, 3.0F));
        BONE_CROOK = registry.registerRawItem("bone_crook", () -> new BaseCrook(VanillaCompatToolMaterial.STONE, CompatibleItemSettings.of(compatId("bone_crook")).addGroup(ItemGroups.TOOLS), 2, 3.5F));
        STONE_CROOK = registry.registerRawItem("stone_crook", () -> new BaseCrook(VanillaCompatToolMaterial.STONE, CompatibleItemSettings.of(compatId("stone_crook")).addGroup(ItemGroups.TOOLS), 3, 4.0F));
        BLAZE_ROD_CROOK = registry.registerRawItem("blaze_rod_crook", () -> new BaseCrook(VanillaCompatToolMaterial.GOLD, CompatibleItemSettings.of(compatId("blaze_rod_crook")).addGroup(ItemGroups.TOOLS), 5, 4.5F));
        WITHERED_BONE_CROOK = registry.registerRawItem("withered_bone_crook", () -> new BaseCrook(VanillaCompatToolMaterial.IRON, CompatibleItemSettings.of(compatId("withered_bone_crook")).addGroup(ItemGroups.TOOLS), 7, 5.0F));
    }

    @Override
    public String getName() {
        return MOD_NAME;
    }

    @Override
    public String getId() {
        return MOD_ID;
    }
}