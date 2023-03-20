package ml.pkom.ordinarycrook;

import ml.pkom.mcpitanlibarch.api.event.registry.RegistryEvent;
import ml.pkom.mcpitanlibarch.api.item.CompatibleItemSettings;
import ml.pkom.mcpitanlibarch.api.item.DefaultItemGroups;
import ml.pkom.mcpitanlibarch.api.registry.ArchRegistry;
import ml.pkom.ordinarycrook.item.BaseCrook;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Identifier;

public class OrdinaryCrook {
    public static final String MOD_ID = "ordinarycrook";

    public static ArchRegistry registry = ArchRegistry.createRegistry(MOD_ID);

    public static RegistryEvent<Item> WOODEN_CROOK;
    public static RegistryEvent<Item> BONE_CROOK;
    public static RegistryEvent<Item> STONE_CROOK;
    public static RegistryEvent<Item> BLAZE_ROD_CROOK;
    public static RegistryEvent<Item> WITHERED_BONE_CROOK;

    public static void init() {
        WOODEN_CROOK = registry.registerItem(id("wooden_crook"), () -> new BaseCrook(ToolMaterials.WOOD, CompatibleItemSettings.of().addGroup(DefaultItemGroups.TOOLS, id("wooden_crook")), 1, 3.0F));
        BONE_CROOK = registry.registerItem(id("bone_crook"), () -> new BaseCrook(ToolMaterials.STONE, CompatibleItemSettings.of().addGroup(DefaultItemGroups.TOOLS, id("bone_crook")), 2, 3.5F));
        STONE_CROOK = registry.registerItem(id("stone_crook"), () -> new BaseCrook(ToolMaterials.STONE, CompatibleItemSettings.of().addGroup(DefaultItemGroups.TOOLS, id("stone_crook")), 3, 4.0F));
        BLAZE_ROD_CROOK = registry.registerItem(id("blaze_rod_crook"), () -> new BaseCrook(ToolMaterials.GOLD, CompatibleItemSettings.of().addGroup(DefaultItemGroups.TOOLS, id("blaze_rod_crook")), 5, 4.5F));
        WITHERED_BONE_CROOK = registry.registerItem(id("withered_bone_crook"), () -> new BaseCrook(ToolMaterials.IRON, CompatibleItemSettings.of().addGroup(DefaultItemGroups.TOOLS, id("withered_bone_crook")), 7, 5.0F));

        registry.allRegister();
    }

    public static Identifier id(String id) {
        return new Identifier(MOD_ID, id);
    }
}