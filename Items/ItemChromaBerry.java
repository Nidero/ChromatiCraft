/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.ChromatiCraft.Items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import Reika.ChromatiCraft.Base.ItemCrystalBasic;
import Reika.ChromatiCraft.Magic.PlayerElementBuffer;
import Reika.ChromatiCraft.Registry.CrystalElement;
import Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;

public class ItemChromaBerry extends ItemCrystalBasic {

	public ItemChromaBerry(int tex) {
		super(tex);
	}
	/*
	@Override
	public boolean onEntityItemUpdate(EntityItem ei) {
		int x = MathHelper.floor_double(ei.posX);
		int y = MathHelper.floor_double(ei.posY);
		int z = MathHelper.floor_double(ei.posZ);
		Block b = ei.worldObj.getBlock(x, y, z);
		if (b == ChromaBlocks.CHROMA.getBlockInstance() || b == ChromaBlocks.ACTIVECHROMA.getBlockInstance()) {
			if (ei.worldObj.getBlockMetadata(x, y, z) == 0) {
				if (b == ChromaBlocks.CHROMA.getBlockInstance())
					ei.worldObj.setBlock(x, y, z, ChromaBlocks.ACTIVECHROMA.getBlockInstance());
				TileEntity te = ei.worldObj.getTileEntity(x, y, z);
				if (te instanceof TileEntityChroma) {
					TileEntityChroma tc = (TileEntityChroma)te;
					if (tc.activate(CrystalElement.elements[ei.getEntityItem().getItemDamage()])) {
						ei.setDead();
					}
				}
			}
		}
		return false;
	}
	 */
	/*
	@Override
	public boolean hasCustomEntity(ItemStack is) {
		return true;
	}

	@Override
	public Entity createEntity(World world, Entity location, ItemStack itemstack)
	{
		EntityChromaBerry ei = new EntityChromaBerry(world, location.posX, location.posY, location.posZ, itemstack);
		ei.motionX = location.motionX;
		ei.motionY = location.motionY;
		ei.motionZ = location.motionZ;
		ei.delayBeforeCanPickup = 10;
		return ei;
	}*/

	@Override
	public ItemStack onEaten(ItemStack is, World world, EntityPlayer ep)
	{
		is.stackSize--;
		CrystalElement e = CrystalElement.elements[is.getItemDamage()%16];
		if (ReikaRandomHelper.doWithChance(20)) {
			if (PlayerElementBuffer.instance.hasElement(ep, e) && PlayerElementBuffer.instance.getPlayerFraction(ep, e) < 0.25)
				PlayerElementBuffer.instance.addToPlayer(ep, e, 1);
		}
		return is;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack is)
	{
		return 64;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack p_77661_1_)
	{
		return EnumAction.eat;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer ep)
	{
		ep.setItemInUse(is, this.getMaxItemUseDuration(is));
		return is;
	}

}
