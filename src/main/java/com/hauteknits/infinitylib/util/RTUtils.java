package com.hauteknits.infinitylib.util;

import javafx.scene.chart.Axis;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Predicate;

public class RTUtils {

    public static BlockPos rayTraceBlocks(LivingEntity source, double distance, boolean ignoreFluids){
        RayTraceContext.FluidMode fluidMode = ignoreFluids ? RayTraceContext.FluidMode.NONE : RayTraceContext.FluidMode.ANY;

        RayTraceContext rayTraceContext = new RayTraceContext(source.getEyePosition(1), source.getEyePosition(1).add(source.getLookAngle().scale(distance)), RayTraceContext.BlockMode.COLLIDER, fluidMode, source);
        BlockRayTraceResult blockHit = source.level.clip(rayTraceContext);
        if(blockHit.getType() == RayTraceResult.Type.MISS) return null;
        return blockHit.getBlockPos();
    }

    public static Entity rayTraceEntities(LivingEntity source, double distance){
        distance *= 10;
        Predicate<Entity> filter = e ->  !e.isSpectator();
        EntityRayTraceResult entityTrace = ProjectileHelper.getEntityHitResult(source, source.getEyePosition(1), source.getEyePosition(1).add(source.getLookAngle().scale(distance)), source.getBoundingBox().expandTowards(source.getLookAngle().scale(distance)).inflate(1.0d), filter, distance);
        if(entityTrace == null) return null;
        return entityTrace.getEntity();
    }
    public static List<Entity> getEntitiesInRadius(LivingEntity init, BlockPos source, int radius){
        AxisAlignedBB bb = new AxisAlignedBB(source.getX()+radius, source.getY()+radius, source.getZ()+30,source.getX()-radius, source.getY()-radius, source.getZ()-30);
        Predicate<Entity> filter = e-> !(e instanceof PlayerEntity);
        return init.level.getEntities(init, bb, filter);
    }
}
