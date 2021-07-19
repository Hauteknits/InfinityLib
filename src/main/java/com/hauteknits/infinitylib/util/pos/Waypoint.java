package com.hauteknits.infinitylib.util.pos;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

//TODO: Test functionality
public class Waypoint {
    private Entity creator;
    private BlockPos dest;
    private boolean isPrivate;
    private int usesLeft;
    private int lifeLeft;

    private boolean isActive=false;


    public Waypoint(Entity creator, BlockPos dest){
        this.creator = creator;
        this.dest=dest;
        isPrivate = true;
        usesLeft =  1;
        lifeLeft = 5*20; //to lazy to just enter the ticks
        register();
    }
    public Waypoint(Entity creator, BlockPos dest, int uses){
        this.creator = creator;
        this.dest=dest;
        isPrivate = true;
        usesLeft =  uses;
        lifeLeft = 5*20; //to lazy to just enter the ticks
        register();
    }
    public Waypoint(Entity creator, BlockPos dest, int uses, boolean isPrivate){
        this.creator = creator;
        this.dest=dest;
        isPrivate = true;
        usesLeft =  uses;
        lifeLeft = 5*20; //to lazy to just enter the ticks
        register();
    }
    public Waypoint(Entity creator, BlockPos dest, int uses, boolean isPrivate, int life){
        this.creator = creator;
        this.dest=dest;
        isPrivate = true;
        usesLeft =  uses;
        lifeLeft = life;
        register();
    }
    public void activate(){this.isActive = true;}

    @SubscribeEvent
    public void timer(TickEvent.WorldTickEvent event){
        if(isActive) lifeLeft--;
        if(lifeLeft <= 0) isActive = false;
    }

    private void register(){ MinecraftForge.EVENT_BUS.register(this); }
}
