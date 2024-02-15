package net.pitan76.ordinarycrook;

import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Identifier;
import net.pitan76.mcpitanlib.api.item.CompatibleItemSettings;
import net.pitan76.mcpitanlib.api.item.DefaultItemGroups;
import net.pitan76.mcpitanlib.api.registry.CompatRegistry;
import net.pitan76.mcpitanlib.api.registry.result.RegistryResult;
import net.pitan76.ordinarycrook.item.BaseCrook;

public class OrdinaryCrook {
    public static final String MOD_ID = "ordinarycrook";

    public static CompatRegistry registry = CompatRegistry.createRegistry(MOD_ID);

    public static RegistryResult<Item> WOODEN_CROOK;
    public static RegistryResult<Item> BONE_CROOK;
    public static RegistryResult<Item> STONE_CROOK;
    public static RegistryResult<Item> BLAZE_ROD_CROOK;
    public static RegistryResult<Item> WITHERED_BONE_CROOK;

    public static void init() {
        WOODEN_CROOK = registry.registerItem(id("wooden_crook"), () -> new BaseCrook(ToolMaterials.WOOD, CompatibleItemSettings.of().addGroup(() -> DefaultItemGroups.TOOLS, id("wooden_crook")), 1, 3.0F));
        BONE_CROOK = registry.registerItem(id("bone_crook"), () -> new BaseCrook(ToolMaterials.STONE, CompatibleItemSettings.of().addGroup(() -> DefaultItemGroups.TOOLS, id("bone_crook")), 2, 3.5F));
        STONE_CROOK = registry.registerItem(id("stone_crook"), () -> new BaseCrook(ToolMaterials.STONE, CompatibleItemSettings.of().addGroup(() -> DefaultItemGroups.TOOLS, id("stone_crook")), 3, 4.0F));
        BLAZE_ROD_CROOK = registry.registerItem(id("blaze_rod_crook"), () -> new BaseCrook(ToolMaterials.GOLD, CompatibleItemSettings.of().addGroup(DefaultItemGroups.TOOLS, id("blaze_rod_crook")), 5, 4.5F));
        WITHERED_BONE_CROOK = registry.registerItem(id("withered_bone_crook"), () -> new BaseCrook(ToolMaterials.IRON, CompatibleItemSettings.of().addGroup(() -> DefaultItemGroups.TOOLS, id("withered_bone_crook")), 7, 5.0F));

        registry.allRegister();
    }

    public static Identifier id(String id) {
        return new Identifier(MOD_ID, id);
    }
}