package net.pitan76.ordinarycrook;

import net.minecraft.item.Item;
import net.pitan76.mcpitanlib.api.CommonModInitializer;
import net.pitan76.mcpitanlib.api.item.v2.CompatibleItemSettings;
import net.pitan76.mcpitanlib.api.item.v3.VanillaCompatToolMaterial;
import net.pitan76.mcpitanlib.api.registry.result.RegistryResult;
import net.pitan76.mcpitanlib.api.registry.v2.CompatRegistryV2;
import net.pitan76.mcpitanlib.midohra.item.ItemGroups;
import net.pitan76.ordinarycrook.item.BaseCrook;

public class OrdinaryCrook extends CommonModInitializer {
    public static final String MOD_ID = "ordinarycrook";
    public static final String MOD_NAME = "Ordinary Crook";

    public static OrdinaryCrook INSTANCE;
    public static CompatRegistryV2 registry;

    public static RegistryResult<Item> WOODEN_CROOK;
    public static RegistryResult<Item> BONE_CROOK;
    public static RegistryResult<Item> STONE_CROOK;
    public static RegistryResult<Item> BLAZE_ROD_CROOK;
    public static RegistryResult<Item> WITHERED_BONE_CROOK;

    public void init() {
        INSTANCE = this;
        registry = super.registry;

        WOODEN_CROOK = registry.registerItem(compatId("wooden_crook"), () -> new BaseCrook(VanillaCompatToolMaterial.WOOD, CompatibleItemSettings.of(compatId("wooden_crook")).addGroup(ItemGroups.TOOLS), 1, 3.0F));
        BONE_CROOK = registry.registerItem(compatId("bone_crook"), () -> new BaseCrook(VanillaCompatToolMaterial.STONE, CompatibleItemSettings.of(compatId("bone_crook")).addGroup(ItemGroups.TOOLS), 2, 3.5F));
        STONE_CROOK = registry.registerItem(compatId("stone_crook"), () -> new BaseCrook(VanillaCompatToolMaterial.STONE, CompatibleItemSettings.of(compatId("stone_crook")).addGroup(ItemGroups.TOOLS), 3, 4.0F));
        BLAZE_ROD_CROOK = registry.registerItem(compatId("blaze_rod_crook"), () -> new BaseCrook(VanillaCompatToolMaterial.GOLD, CompatibleItemSettings.of(compatId("blaze_rod_crook")).addGroup(ItemGroups.TOOLS), 5, 4.5F));
        WITHERED_BONE_CROOK = registry.registerItem(compatId("withered_bone_crook"), () -> new BaseCrook(VanillaCompatToolMaterial.IRON, CompatibleItemSettings.of(compatId("withered_bone_crook")).addGroup(ItemGroups.TOOLS), 7, 5.0F));
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