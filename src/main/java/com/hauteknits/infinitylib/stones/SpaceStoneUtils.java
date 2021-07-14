package com.hauteknits.infinitylib.stones;

import net.darkhax.bookshelf.util.EntityUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;

public class SpaceStoneUtils {

    /**
     * Teleports the user a set distance and adds particle effects at source and target
     * @param user The person to teleport
     * @param range The range to teleport
     * @return Pass/fail ActionResultType
     */

    //TODO: FIX raytrace
    public static ActionResultType teleport(LivingEntity user, double range){
        BlockRayTraceResult rt = (BlockRayTraceResult) (EntityUtils.rayTrace(user, range, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.ANY));
        BlockPos target = rt.getBlockPos();
        if(target == null) return ActionResultType.CONSUME;
        user.setPos(target.getX(),target.getY()+1, target.getZ());
        return ActionResultType.SUCCESS;
    }
}
