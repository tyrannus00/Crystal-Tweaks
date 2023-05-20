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

        if (!super.canPlace(context, state)) {
            return false;
        }

        if (!Config.bedsStrictEntities) {
            return true;
        }

        // Checking if there are entities in the way of the head piece

        PlayerEntity player = context.getPlayer();
        ShapeContext shapeContext = player == null ? ShapeContext.absent() : ShapeContext.of(player);

        Direction direction = context.getHorizontalPlayerFacing();
        BlockPos headPos = context.getBlockPos().offset(direction);
        BlockState headState = getBlock().getDefaultState().with(HorizontalFacingBlock.FACING, direction);

        return context.getWorld().canPlace(headState, headPos, shapeContext);
    }
}