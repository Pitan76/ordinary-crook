package net.pitan76.ordinarycrook.item;

import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.pitan76.mcpitanlib.api.entity.Player;
import net.pitan76.mcpitanlib.api.event.item.ItemUseOnEntityEvent;
import net.pitan76.mcpitanlib.api.event.item.PostMineEvent;
import net.pitan76.mcpitanlib.api.item.tool.CompatibleMiningToolItem;
import net.pitan76.mcpitanlib.api.item.v2.CompatibleItemSettings;
import net.pitan76.mcpitanlib.api.item.tool.CompatibleToolMaterial;
import net.pitan76.mcpitanlib.api.tag.v2.typed.BlockTagKey;
import net.pitan76.mcpitanlib.api.util.*;
import net.pitan76.mcpitanlib.api.util.entity.ItemEntityUtil;
import net.pitan76.mcpitanlib.api.util.world.ServerWorldUtil;
import net.pitan76.mcpitanlib.midohra.util.math.Vector3d;

import java.util.List;

public class BaseCrook extends CompatibleMiningToolItem {

    public int dropMultiple;
    public float speed;

    public BaseCrook(CompatibleToolMaterial material, CompatibleItemSettings settings, int dropMultiple, float speed) {
        super(material, 0, 0f, BlockTagKey.of(CompatIdentifier.of("leaves")), settings);
        this.dropMultiple = dropMultiple;
        this.speed = speed;
    }

    public static void randomDrop(World world, BlockState state, BlockPos pos) {
        List<ItemStack> droppedStacks = ServerWorldUtil.getDroppedStacksOnBlock(state, (ServerWorld) world, pos, (BlockEntity) null);
        for (ItemStack stack : droppedStacks) {
            WorldUtil.spawnEntity(world, ItemEntityUtil.create(world, pos, stack));
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
        if (overrideIsSuitableFor(state))
            return speed;

        return 0.0F;
    }

    public boolean postMine(PostMineEvent e) {
        World world = e.world;
        BlockPos pos = e.pos;
        BlockState state = e.state;
        LivingEntity miner = e.miner;

        if (miner instanceof PlayerEntity) {
            if (e.isClient() || e.isCreative()) return true;
            if (BlockStateUtil.getHardness(state, world, pos) != 0.0F)
                e.damageStack(1);

            if (BaseCrook.isCrook(e.getMainHandStack())) {
                for (int i = 1; i <= speed; i++) {
                    randomDrop(world, state, pos);
                }
            }
        }

        return true;
    }

    @Override
    public CompatActionResult onRightClickOnEntity(ItemUseOnEntityEvent e) {
        LivingEntity entity = e.entity;
        Player user = e.user;

        Vector3d userPos = Vector3d.of(user.getPos());
        Vector3d entityPos = EntityUtil.getPosM(entity);

        Vector3d movePos = userPos.sub(entityPos);
        Vector3d rotatedMovePos = Vector3d.of(EntityUtil.getRotationVector(user.getEntity()));

        movePos = movePos.sub(rotatedMovePos).mul(0.25);

        EntityUtil.setVelocity(entity, movePos.x, movePos.y, movePos.z);
        EntityUtil.setFallDistance(entity, 0F);
        EntityUtil.setVelocityModified(entity, true);

        return CompatActionResult.SUCCESS;
    }
}
