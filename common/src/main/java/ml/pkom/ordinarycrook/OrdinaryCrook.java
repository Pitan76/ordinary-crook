package ml.pkom.ordinarycrook;

import ml.pkom.mcpitanlibarch.api.event.registry.RegistryEvent;
import ml.pkom.mcpitanlibarch.api.registry.ArchRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
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

    public static void init() {
        WOODEN_CROOK = registry.registerItem(id("wooden_crook"), () -> new BaseCrook(ToolMaterials.WOOD, new Item.Settings().group(ItemGroup.TOOLS)));
        BONE_CROOK = registry.registerItem(id("bone_crook"), () -> new BaseCrook(ToolMaterials.STONE, new Item.Settings().group(ItemGroup.TOOLS)));

        registry.allRegister();
    }
}