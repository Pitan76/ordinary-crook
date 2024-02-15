package net.pitan76.ordinarycrook.item;

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
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.pitan76.mcpitanlib.api.entity.Player;
import net.pitan76.mcpitanlib.api.item.CompatibleItemSettings;
import net.pitan76.mcpitanlib.api.util.WorldUtil;

import java.util.List;

public class BaseCrook extends ToolItem {

    public int dropMultiple;
    public float speed;

    public BaseCrook(ToolMaterial material, CompatibleItemSettings settings, int dropMultiple, float speed) {
        super(material, settings.build());
        this.dropMultiple = dropMultiple;
        this.speed = speed;
    }

    public static void randomDrop(World level, BlockState state, BlockPos pos) {
        List<ItemStack> droppedStacks = Block.getDroppedStacks(state, (ServerWorld) level, pos, null);
        for (ItemStack stack : droppedStacks) {
            WorldUtil.spawnEntity(level, new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), stack));
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
            return speed;
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

                for (int i = 1; i <= speed; i++) {
                    randomDrop(world, state, pos);
                }
            }
        }

        return true;
    }

    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        Vec3d movePos = user.getPos().subtract(entity.getPos());
        movePos = movePos.subtract(user.getRotationVector());
        movePos = movePos.multiply(0.25);
        entity.setVelocity(movePos);
        entity.fallDistance = 0F;
        entity.velocityModified = true;

        return ActionResult.SUCCESS;
    }
}
