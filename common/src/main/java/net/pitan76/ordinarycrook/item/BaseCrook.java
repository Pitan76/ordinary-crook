package net.pitan76.ordinarycrook.item;

import net.minecraft.block.LeavesBlock;
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
import net.pitan76.mcpitanlib.midohra.block.BlockState;
import net.pitan76.mcpitanlib.midohra.block.entity.BlockEntityWrapper;
import net.pitan76.mcpitanlib.midohra.entity.EntityWrapper;
import net.pitan76.mcpitanlib.midohra.item.ItemStack;
import net.pitan76.mcpitanlib.midohra.util.math.BlockPos;
import net.pitan76.mcpitanlib.midohra.util.math.Vector3d;
import net.pitan76.mcpitanlib.midohra.world.ServerWorld;
import net.pitan76.mcpitanlib.midohra.world.World;

import java.util.List;
import java.util.stream.Collectors;

public class BaseCrook extends CompatibleMiningToolItem {

    public int dropMultiple;
    public float speed;

    public BaseCrook(CompatibleToolMaterial material, CompatibleItemSettings settings, int dropMultiple, float speed) {
        super(material, 0, 0f, BlockTagKey.of(CompatIdentifier.of("leaves")), settings);
        this.dropMultiple = dropMultiple;
        this.speed = speed;
    }

    public static void randomDrop(World world, BlockState state, BlockPos pos) {
        ServerWorld serverWorld = world.toServerWorld().get();
        List<ItemStack> droppedStacks = getDroppedStacksOnBlock(state, serverWorld, pos);
        for (ItemStack stack : droppedStacks) {
            ItemEntityUtil.createWithSpawn(world, stack, pos);
        }
    }

    public static List<ItemStack> getDroppedStacksOnBlock(BlockState state, ServerWorld world, BlockPos pos) {
        return ServerWorldUtil.getDroppedStacksOnBlock(
                state.toMinecraft(), world.toMinecraft(), pos.toMinecraft(), (BlockEntityWrapper) null)
                .stream().map(ItemStack::of).collect(Collectors.toList());
    }

    public static boolean isCrook(ItemStack stack) {
        return stack.getRawItem() instanceof BaseCrook;
    }

    @Override
    public boolean overrideIsSuitableFor(net.minecraft.block.BlockState state) {
        return BlockStateUtil.getBlock(state) instanceof LeavesBlock;
    }

    @Override
    public float overrideGetMiningSpeedMultiplier(net.minecraft.item.ItemStack stack, net.minecraft.block.BlockState state) {
        return overrideIsSuitableFor(state) ? speed : 0.0F;
    }

    public boolean postMine(PostMineEvent e) {
        World world = World.of(e.world);
        BlockPos pos = BlockPos.of(e.pos);
        BlockState state = BlockState.of(e.state);

        if (e.isClient() || !e.isPlayer() || e.isCreative()) return true;

        if (state.getHardness(world, pos) != 0.0F)
            e.damageStack(1);

        ItemStack mainHandStack = ItemStack.of(e.getMainHandStack());
        if (BaseCrook.isCrook(mainHandStack)) {
            for (int i = 1; i <= speed; i++) {
                randomDrop(world, state, pos);
            }
        }


        return true;
    }

    @Override
    public CompatActionResult onRightClickOnEntity(ItemUseOnEntityEvent e) {
        EntityWrapper entity = EntityWrapper.of(e.entity);
        Player user = e.user;

        Vector3d userPos = user.getPosM();
        Vector3d entityPos = entity.getPos();

        Vector3d movePos = userPos.sub(entityPos);
        Vector3d rotatedMovePos = Vector3d.of(EntityUtil.getRotationVector(user.getEntity()));

        movePos = movePos.sub(rotatedMovePos).mul(0.25);

        entity.setVelocity(movePos.x, movePos.y, movePos.z);
        EntityUtil.setFallDistance(entity.get(), 0F);
        EntityUtil.setVelocityModified(entity.get(), true);

        return e.success();
    }
}
