package de.tyrannus.crystaltweaks.mixins;

import de.tyrannus.crystaltweaks.Config;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BedItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BedItem.class)
public class BedItemMixin extends BlockItem {

    public BedItemMixin(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    protected boolean canPlace(ItemPlacementContext context, BlockState state) {
        // Vanilla place check first

        PlayerEntity player = context.getPlayer();
        ShapeContext shapeContext = player == null ? ShapeContext.absent() : ShapeContext.of(player);

        boolean bottomPieceChecks = (!checkStatePlacement() || state.canPlaceAt(context.getWorld(), context.getBlockPos()))
                && context.getWorld().canPlace(state, context.getBlockPos(), shapeContext);

        if (!bottomPieceChecks || !Config.bedsStrictEntities) {
            return bottomPieceChecks;
        }

        // Checking if there are entities in the way of the head piece

        Direction direction = context.getPlayerFacing();
        BlockPos headPos = context.getBlockPos().offset(direction);
        BlockState headState = getBlock().getDefaultState().with(HorizontalFacingBlock.FACING, direction);

        return context.getWorld().canPlace(headState, headPos, shapeContext);
    }
}