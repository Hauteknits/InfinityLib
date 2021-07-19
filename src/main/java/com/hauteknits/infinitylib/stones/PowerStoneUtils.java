package com.hauteknits.infinitylib.stones;

import com.hauteknits.infinitylib.util.RTUtils;


import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;

import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.server.ServerWorld;

/*
 * TODO: Implement the following:
 *  [X] TNT Drop: Drop a block of TNT at a designated spot
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
     * @param distance The distance to punch
     * @return A pass/fail ActionResultType
     */
    public static ActionResultType punch(LivingEntity user, double distance){
        BlockPos target = RTUtils.rayTraceBlocks(user, distance, false);
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
     * Sets a block or Entity on fire
     * @param user Source of RayTrace to ignite
     * @return A pass/fail ActionResultType
     */
    public static ActionResultType ignite(LivingEntity user, double distance){
        BlockPos target = RTUtils.rayTraceBlocks(user, distance, false);
        Entity entityTarget = RTUtils.rayTraceEntities(user, 30);
        if(target == null && entityTarget == null){
            return ActionResultType.CONSUME;
        }
        if(entityTarget != null){
            if(entityTarget.fireImmune()) return ActionResultType.CONSUME;
            entityTarget.setSecondsOnFire((int)(Math.random()*3+2));
        }else if(!(AbstractFireBlock.canBePlacedAt(user.level, target.above(), Direction.UP))){
            return ActionResultType.CONSUME;
        }else{
            BlockState onFire = AbstractFireBlock.getState(user.level, target.above());
            user.level.setBlockAndUpdate(target.above(), onFire);
        }
        user.level.playSound(null,user.getX(),user.getY(), user.getZ(), SoundEvents.FLINTANDSTEEL_USE, SoundCategory.AMBIENT, 0.5f, (float)(Math.random()/2+0.3));
        return ActionResultType.SUCCESS;

    }

    public static ActionResultType dropTNT(LivingEntity source, double distance, int offsetY){
        BlockPos target = RTUtils.rayTraceBlocks(source, distance, true);
        if(target == null) return ActionResultType.CONSUME;
        TNTEntity e = new TNTEntity(source.level, target.getX(), target.getY()+ offsetY+1, target.getZ(), source);
        source.level.addFreshEntity(e);
        return ActionResultType.SUCCESS;
    }
}
