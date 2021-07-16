package com.hauteknits.infinitylib.stones;

import net.darkhax.bookshelf.util.EntityUtils;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.server.ServerWorld;

/*
 * TODO: Implement the following:
 *  [ ] TNT Drop: Drop a block of TNT at a designated spot
 *  [ ] Power Laser: Self Explanitory
 *  [ ] Stone Contact: Create a radius that eliminates all life and tarnishs trees and randomly ignites wood (Stone touches ground)
 *  [X} Super Jump: Launch yourself in the direction you're looking (Requires to be on ground
 *  [X] Punch: Create explosion at target, or 15 blocks away
 *  [X] Ignite: Ignite block looking at
 *      [ ] Ignite Entities as well
 *
 *
 */
public class PowerStoneUtils {
    /**
     * Simulates a punch by exploding the block at cursor no greater than 15 blocks, otherwise, explodes in air
     * @param user User to start rayTrace from
     * @return A pass/fail ActionResultType
     */
    public static ActionResultType punch(LivingEntity user){
        BlockRayTraceResult rt = (BlockRayTraceResult) (EntityUtils.rayTrace(user, 15, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.ANY));
        BlockPos target = rt.getBlockPos();
        if(target == null){
            Vector3d dir = user.getLookAngle();
            target = user.blockPosition().offset(dir.x*20, dir.y*20, dir.z*20);
        }
        if(user.level instanceof ServerWorld) user.level.explode(null, target.getX(),target.getY(), target.getZ(), 7.0f, true, Explosion.Mode.DESTROY);
        user.level.playSound(null,user.getX(),user.getY(), user.getZ(), SoundEvents.SNOWBALL_THROW, SoundCategory.PLAYERS, 0.5f, 0.2f);
        return ActionResultType.SUCCESS;
    }

    /**
     * Launches the user in the air
     * @param user User to launch
     * @return A pass/fail ActionResultType
     */
    public static ActionResultType launch(LivingEntity user){
        Vector3d dir = user.getLookAngle();
        if(!(user.isOnGround())) return ActionResultType.FAIL;
        user.setDeltaMovement(dir.x*3, dir.y*3, dir.z*3);
        return ActionResultType.SUCCESS;
    }

    /**
     * Sets a block on fire
     * @param user Source of RayTrace to ignite
     * @return A pass/fail ActionResultType
     */
    //TODO: Fix
    public static ActionResultType ignite(LivingEntity user){
        BlockRayTraceResult rt = (BlockRayTraceResult) (EntityUtils.rayTrace(user, 30, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.ANY));
        BlockPos target = rt.getBlockPos();
        if(target == null){
            return ActionResultType.CONSUME;
        }
        if(!(AbstractFireBlock.canBePlacedAt(user.level, target.above(), Direction.UP))){
            return ActionResultType.CONSUME;
        }
        BlockState onFire = AbstractFireBlock.getState(user.level, target.above());
        user.level.setBlockAndUpdate(target.above(), onFire);
        user.level.playSound(null,user.getX(),user.getY(), user.getZ(), SoundEvents.FLINTANDSTEEL_USE, SoundCategory.AMBIENT, 0.5f, (float)(Math.random()/2+0.3));
        return ActionResultType.SUCCESS;

    }
}
