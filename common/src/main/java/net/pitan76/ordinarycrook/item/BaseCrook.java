package net.pitan76.ordinarycrook.item;

import net.minecraft.block.LeavesBlock;
import net.pitan76.mcpitanlib.api.entity.Player;
import net.pitan76.mcpitanlib.api.event.item.ItemUseOnEntityEvent;
import net.pitan76.mcpitanlib.api.event.item.PostMineEvent;
import net.pitan76.mcpitanlib.api.item.args.tool.MiningSpeedMultiplierArgs;
import net.pitan76.mcpitanlib.api.item.args.tool.SuitableForArgs;
import net.pitan76.mcpitanlib.api.item.v2.CompatibleItemSettings;
import net.pitan76.mcpitanlib.api.item.tool.CompatibleToolMaterial;
import net.pitan76.mcpitanlib.api.item.v3.tool.CompatMiningToolItem;
import net.pitan76.mcpitanlib.api.tag.v2.typed.BlockTagKey;
import net.pitan76.mcpitanlib.api.util.*;
import net.pitan76.mcpitanlib.api.util.entity.ItemEntityUtil;
import net.pitan76.mcpitanlib.midohra.block.BlockState;
import net.pitan76.mcpitanlib.midohra.entity.EntityWrapper;
import net.pitan76.mcpitanlib.midohra.item.ItemStack;
import net.pitan76.mcpitanlib.midohra.util.math.BlockPos;
import net.pitan76.mcpitanlib.midohra.util.math.Vector3d;
import net.pitan76.mcpitanlib.midohra.world.ServerWorld;
import net.pitan76.mcpitanlib.midohra.world.World;

import java.util.List;

public class BaseCrook extends CompatMiningToolItem {

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
        return world.getDroppedStacksOnBlock(state, pos, null);
    }

    public static boolean isCrook(ItemStack stack) {
        return stack.getItem().instanceOf(BaseCrook.class);
    }

    @Override
    public boolean isSuitableFor(SuitableForArgs args) {
        return args.state.getBlock().instanceOf(LeavesBlock.class);
    }

    @Override
    public float getMiningSpeedMultiplier(MiningSpeedMultiplierArgs args) {
        return isSuitableFor(new SuitableForArgs(args.state)) ? speed : 0.0F;
    }

    public boolean postMine(PostMineEvent e) {
        World world = e.getWorldM();
        BlockPos pos = e.getPosM();
        BlockState state = e.getStateM();

        if (e.isClient() || !e.isPlayer() || e.isCreative()) return true;

        if (state.getHardness(world, pos) != 0.0F)
            e.damageStack(1);

        ItemStack mainHandStack = e.getMainHandStackM();
        if (BaseCrook.isCrook(mainHandStack)) {
            for (int i = 1; i <= speed; i++) {
                randomDrop(world, state, pos);
            }
        }

        return true;
    }

    @Override
    public CompatActionResult onRightClickOnEntity(ItemUseOnEntityEvent e) {
        EntityWrapper entity = e.getEntityWrapper();
        Player user = e.user;

        Vector3d userPos = user.getPosM();
        Vector3d entityPos = entity.getPos();

        Vector3d movePos = userPos.sub(entityPos);
        Vector3d rotatedMovePos = user.wrap().getRotationVector();

        movePos = movePos.sub(rotatedMovePos).mul(0.25);

        entity.setVelocity(movePos.x, movePos.y, movePos.z);
        entity.setFallDistance(0F);
        entity.setVelocityModified(true);

        return e.success();
    }
}
