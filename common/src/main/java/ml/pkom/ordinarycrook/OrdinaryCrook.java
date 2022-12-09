package ml.pkom.ordinarycrook;

import ml.pkom.mcpitanlibarch.api.event.registry.RegistryEvent;
import ml.pkom.mcpitanlibarch.api.item.DefaultItemGroups;
import ml.pkom.mcpitanlibarch.api.item.ExtendSettings;
import ml.pkom.mcpitanlibarch.api.registry.ArchRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ml.pkom.ordinarycrook.item.BaseCrook;

public class OrdinaryCrook {
    public static final String MOD_ID = "ordinarycrook";
    public static final String MOD_NAME = "Ordinary Crook";

    public static Logger LOGGER = LogManager.getLogger();
    public static void log(Level level, String message){
        LOGGER.log(level, "[" + MOD_NAME + "] " + message);
    }

    public static Identifier id(String id) {
        return new Identifier(MOD_ID, id);
    }
    public static ArchRegistry registry = ArchRegistry.createRegistry(MOD_ID);

    public static RegistryEvent<Item> WOODEN_CROOK;

    public static RegistryEvent<Item> BONE_CROOK;
    public static RegistryEvent<Item> STONE_CROOK;
    public static RegistryEvent<Item> BLAZE_ROD_CROOK;
    public static RegistryEvent<Item> WITHERED_BONE_CROOK;

    public static void init() {
        WOODEN_CROOK = registry.registerItem(id("wooden_crook"), () -> new BaseCrook(ToolMaterials.WOOD, new ExtendSettings().addGroup(DefaultItemGroups.TOOLS, id("wooden_crook")), 1, 3.0F));
        BONE_CROOK = registry.registerItem(id("bone_crook"), () -> new BaseCrook(ToolMaterials.STONE, new ExtendSettings().addGroup(DefaultItemGroups.TOOLS, id("bone_crook")), 2, 3.5F));
        STONE_CROOK = registry.registerItem(id("stone_crook"), () -> new BaseCrook(ToolMaterials.STONE, new ExtendSettings().addGroup(DefaultItemGroups.TOOLS, id("stone_crook")), 3, 4.0F));
        BLAZE_ROD_CROOK = registry.registerItem(id("blaze_rod_crook"), () -> new BaseCrook(ToolMaterials.GOLD, new ExtendSettings().addGroup(DefaultItemGroups.TOOLS, id("blaze_rod_crook")), 5, 4.5F));
        WITHERED_BONE_CROOK = registry.registerItem(id("withered_bone_crook"), () -> new BaseCrook(ToolMaterials.IRON, new ExtendSettings().addGroup(DefaultItemGroups.TOOLS, id("withered_bone_crook")), 7, 5.0F));

        registry.allRegister();
    }
}