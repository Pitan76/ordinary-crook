package ml.pkom.ordinarycrook.item;

import ml.pkom.mcpitanlibarch.api.entity.Player;
import ml.pkom.ordinarycrook.OrdinaryCrook;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class BaseCrook extends ToolItem {
    public BaseCrook(ToolMaterial material, Settings settings) {
        super(material, settings);
    }

    public static void randomDrop(World level, BlockState state, BlockPos pos) {
        List<ItemStack> droppedStacks = Block.getDroppedStacks(state, (ServerWorld) level, pos, null);
        for (ItemStack stack : droppedStacks) {
            level.spawnEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), stack));
        }
    }

    public static boolean isCrook(ItemStack stack) {
        return stack.getItem() instanceof BaseCrook;
    }

    public boolean isSuitableFor(BlockState state) {
        return state.getBlock() instanceof LeavesBlock;
    }

    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        if (state.getBlock() instanceof LeavesBlock) {
            if (stack.getItem() == OrdinaryCrook.BONE_CROOK.get()) return 4.5F;
            return 3.5F;
        }
        return super.getMiningSpeedMultiplier(stack, state);
    }

    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (state.getHardness(world, pos) != 0.0F) {
            stack.damage(1, miner, (e) -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        }
        if (miner instanceof PlayerEntity) {
            Player player = new Player((PlayerEntity) miner);
            ItemStack mainHandStack = miner.getMainHandStack();
            if (BaseCrook.isCrook(mainHandStack)) {
                if (world.isClient) return true;
                if (player.isCreative()) return true;

                randomDrop(world, state, pos);
                randomDrop(world, state, pos);
                if (mainHandStack.getItem() == OrdinaryCrook.BONE_CROOK.get()) {
                    randomDrop(world, state, pos);
                    randomDrop(world, state, pos);
                    randomDrop(world, state, pos);
                }

            }
        }

        return true;
    }
}
