package com.hauteknits.infinitylib.stones;

import com.hauteknits.infinitylib.util.RTUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;

/*
 * TODO: Implement the following:
 *  [X] Convert all arrows in the active radius to bubbles animation
 *  [ ] Erase: Turn a target into dust
 *  [ ] Form Shift: Take the form of the mob you're looking at
 *  [ ] Disarm: Remove the item in the Targets hands
 *  [ ] Disappear: Turn yourself invisible
 *  [ ] Contact Freeze: Turn all water under you into ice
 *  [ ] Create Dummy: Create a clone of you
 *  [ ] Summon Fireworks: Self Explanitory
 *  [ ] Smoke Screen: Create a linear smoke screen above the target block
 *  [ ] Dimnention Shift: Convert all overworld block in a radius to nether blocks and vise versa
 *
 */
public class RealityStoneUtils {
    //TODO: Test functionality, Add bubbles
    public static ActionResultType removeArrows(LivingEntity source, int radius){
        List<Entity> entities = RTUtils.getEntitiesInRadius(source, source.blockPosition(), radius);
        for(Entity e : entities){
            if(e instanceof ArrowEntity) e.kill();
        }
        return ActionResultType.SUCCESS;
    }
}
