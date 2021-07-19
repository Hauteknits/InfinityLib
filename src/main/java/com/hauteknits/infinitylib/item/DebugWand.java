package com.hauteknits.infinitylib.item;

import com.hauteknits.infinitylib.BaseMod;
import com.hauteknits.infinitylib.stones.PowerStoneUtils;
import com.hauteknits.infinitylib.stones.SpaceStoneUtils;
import com.hauteknits.infinitylib.util.RTUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class DebugWand extends Item {
    private static Properties p= new Properties().tab(ItemGroup.TAB_TOOLS).stacksTo(1);
    public static final Item INSTANCE = new DebugWand(p).setRegistryName(BaseMod.MODID, "debug_wand_tester");

    public DebugWand(Properties p) {
        super(p);
    }

    @Override
    public ActionResult<ItemStack> use(World p_77659_1_, PlayerEntity player, Hand p_77659_3_) {
        ItemStack is = player.getItemInHand(p_77659_3_);
        ActionResultType r = PowerStoneUtils.dropTNT(player, 40, 10);
        return new ActionResult(r, is);
    }
}
