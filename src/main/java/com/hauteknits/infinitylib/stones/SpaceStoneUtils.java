package com.hauteknits.infinitylib.stones;

import com.hauteknits.infinitylib.util.RTUtils;
import net.darkhax.bookshelf.util.EntityUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;

/*
 * TODO: Implement the following
 *  [X] Teleport
 *  [ ] Worm hole, create a 3d portal to the cursor location
 *      **Depends on Waypoint Class
 *  [ ] Waypoints
 *      [X] Create Waypoint Class
 *      [ ] Recall
 *      [ ] Create
 *  [ ] Direct Movement: Move in any direction
 */
public class SpaceStoneUtils {

    /**
     * Teleports the user a set distance and adds particle effects at source and target
     * @param user The person to teleport
     * @param range The range to teleport
     * @return Pass/fail ActionResultType
     */

    public static ActionResultType teleport(LivingEntity user, double range){
        BlockPos target = RTUtils.rayTraceBlocks(user, range, false);
        if(target == null) return ActionResultType.CONSUME;
        user.setPos(target.getX(),target.getY()+1, target.getZ());
        return ActionResultType.SUCCESS;
    }
}
