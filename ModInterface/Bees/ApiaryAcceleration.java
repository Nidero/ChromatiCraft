/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.ChromatiCraft.ModInterface.Bees;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import Reika.ChromatiCraft.ChromatiCraft;
import Reika.ChromatiCraft.TileEntity.AOE.Effect.TileEntityAccelerator.Acceleration;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Auxiliary.Trackers.ReflectiveFailureTracker;
import Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
import Reika.DragonAPI.ModInteract.Bees.ReikaBeeHelper;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IBeeHousingInventory;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.apiculture.IBeekeepingLogic;

public class ApiaryAcceleration extends Acceleration {

	public static final ApiaryAcceleration instance = new ApiaryAcceleration();

	private Field beeLogicModifiers;
	private Field beeLogicProgress;
	private Method beeLogicMate;

	private Field tileProgress;

	private static final int TICK_LENGTH = 550;

	public void register() {
		this.registerClass("forestry.apiculture.tiles.TileApiary");
		this.registerClass("forestry.apiculture.tiles.TileBeehouse");
		this.registerClass("net.bdew.gendustry.machines.apiary.TileApiary");
		this.registerClass("magicbees.tileentity.TileEntityMagicApiary");

		try {
			Class c = Class.forName("forestry.apiculture.BeekeepingLogic");
			beeLogicMate = c.getDeclaredMethod("tickBreed");
			beeLogicMate.setAccessible(true);
			beeLogicModifiers = c.getDeclaredField("beeModifier");
			beeLogicModifiers.setAccessible(true);
			beeLogicProgress = c.getDeclaredField("beeProgress");
			beeLogicProgress.setAccessible(true);

			c = Class.forName("forestry.apiculture.tiles.TileBeeHousingBase");
			tileProgress = c.getDeclaredField("breedingProgressPercent");
			tileProgress.setAccessible(true);
		}
		catch (Exception e) {
			e.printStackTrace();
			ReflectiveFailureTracker.instance.logModReflectiveFailure(ModList.FORESTRY, e);
			ChromatiCraft.logger.logError("Could not find BeeKeepingLogic internal members!");
		}
	}

	@Override
	protected void tick(TileEntity te, int factor) throws Exception {
		if (te.worldObj.isRemote)
			return;
		IBeeHousing ibh = (IBeeHousing)te;
		IBeeHousingInventory ibhi = ibh.getBeeInventory();
		ItemStack queen = ibhi.getQueen();
		if (queen != null) {
			IBeekeepingLogic lgc = ibh.getBeekeepingLogic();
			EnumBeeType type = ReikaBeeHelper.getBeeRoot().getType(queen);
			if (type == EnumBeeType.QUEEN) {
				if (lgc.canWork()) {
					IBee bee = ReikaBeeHelper.getBee(queen);
					if (bee.getHealth() <= 0)
						return;
					float f = ((IBeeModifier)beeLogicModifiers.get(lgc)).getLifespanModifier(bee.getGenome(), bee.getMate(), 1.0F);
					double c = factor/(double)TICK_LENGTH;
					//ReikaJavaLibrary.pConsole(c);
					float fac = 1;
					float step = 1.05F;
					while (c*fac*step < 1) {
						fac *= step;
					}
					//ReikaJavaLibrary.pConsole(f+", "+fac+", "+c);
					if (ReikaRandomHelper.doWithChance(c*fac))
						ReikaBeeHelper.ageBee(te.worldObj, queen, f*fac); //not divide, is a lifespan factor, not a how much to age!
					beeLogicProgress.set(lgc, bee.getHealth());
					tileProgress.set(te, (int)(bee.getHealth()*2.25));
					//lgc.syncToClient();
					//te.worldObj.markBlockForUpdate(te.xCoord, te.yCoord, te.zCoord);
				}
			}
			else if (type == EnumBeeType.PRINCESS) {
				for (int i = 0; i < factor; i++)
					beeLogicMate.invoke(lgc);
			}
			else {

			}
		}
	}

}
