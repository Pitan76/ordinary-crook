package net.pitan76.ordinarycrook.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.pitan76.mcpitanlib.api.entity.Player;
import net.pitan76.mcpitanlib.api.event.item.ItemUseOnEntityEvent;
import net.pitan76.mcpitanlib.api.event.item.PostMineEvent;
import net.pitan76.mcpitanlib.api.item.v2.CompatibleItemSettings;
import net.pitan76.mcpitanlib.api.item.tool.CompatibleToolItem;
import net.pitan76.mcpitanlib.api.item.tool.CompatibleToolMaterial;
import net.pitan76.mcpitanlib.api.util.CompatActionResult;
import net.pitan76.mcpitanlib.api.util.WorldUtil;
import net.pitan76.mcpitanlib.api.util.entity.ItemEntityUtil;
import net.pitan76.mcpitanlib.api.util.math.Vec3dUtil;

import java.util.List;

public class BaseCrook extends CompatibleToolItem {

    public int dropMultiple;
    public float speed;

    public BaseCrook(CompatibleToolMaterial material, CompatibleItemSettings settings, int dropMultiple, float speed) {
        super(material, settings);
        this.dropMultiple = dropMultiple;
        this.speed = speed;
    }

    public static void randomDrop(World level, BlockState state, BlockPos pos) {
        List<ItemStack> droppedStacks = Block.getDroppedStacks(state, (ServerWorld) level, pos, null);
        for (ItemStack stack : droppedStacks) {
            WorldUtil.spawnEntity(level, ItemEntityUtil.create(level, pos.getX(), pos.getY(), pos.getZ(), stack));
        }
    }

    public static boolean isCrook(ItemStack stack) {
        return stack.getItem() instanceof BaseCrook;
    }

    @Override
    public boolean overrideIsSuitableFor(BlockState state) {
        return state.getBlock() instanceof LeavesBlock;
    }

    @Override
    public float overrideGetMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        if (state.getBlock() instanceof LeavesBlock)
            return speed;

        return super.overrideGetMiningSpeedMultiplier(stack, state);
    }

    public boolean postMine(PostMineEvent e) {
        World world = e.world;
        BlockPos pos = e.pos;
        BlockState state = e.state;
        LivingEntity miner = e.miner;

        if (miner instanceof PlayerEntity) {
            if (state.getHardness(world, pos) != 0.0F)
                e.damageStack(1, EquipmentSlot.MAINHAND);

            Player player = new Player((PlayerEntity) miner);
            ItemStack mainHandStack = player.getMainHandStack();
            if (BaseCrook.isCrook(mainHandStack)) {
                if (e.isClient()) return true;
                if (player.isCreative()) return true;

                for (int i = 1; i <= speed; i++) {
                    randomDrop(world, state, pos);
                }
            }
        }

        return true;
    }

    @Override
    public CompatActionResult onRightClickOnEntity(ItemUseOnEntityEvent e, Options options) {
        LivingEntity entity = e.entity;
        Player user = e.user;

        Vec3d movePos = Vec3dUtil.subtract(user.getPos(), entity.getPos());
        movePos = Vec3dUtil.subtract(movePos, user.getEntity().getRotationVector());
        movePos = Vec3dUtil.multiply(movePos, 0.25);

        entity.setVelocity(movePos);
        entity.fallDistance = 0F;
        entity.velocityModified = true;

        return CompatActionResult.SUCCESS;
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        return onRightClickOnEntity(new ItemUseOnEntityEvent(stack, user, entity, hand), new Options()).toActionResult();
    }
}
