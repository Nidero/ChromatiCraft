package Reika.ChromatiCraft.TileEntity;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import Reika.ChromatiCraft.ChromatiCraft;
import Reika.ChromatiCraft.Base.TileEntity.InventoriedCrystalReceiver;
import Reika.ChromatiCraft.Registry.ChromaPackets;
import Reika.ChromatiCraft.Registry.ChromaTiles;
import Reika.ChromatiCraft.Registry.CrystalElement;
import Reika.ChromatiCraft.Render.Particle.EntityBlurFX;
import Reika.DragonAPI.Instantiable.Data.Immutable.Coordinate;
import Reika.DragonAPI.Interfaces.CropType;
import Reika.DragonAPI.Interfaces.CropType.CropMethods;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaCropHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.DragonAPI.ModRegistry.ModCropList;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityFarmer extends InventoriedCrystalReceiver {

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateEntity(world, x, y, z, meta);

		int n = this.getNumberAttempts();
		for (int i = 0; i < n; i++) {
			if (!world.isRemote && this.getEnergy(CrystalElement.GREEN) >= 200) {
				Coordinate c = this.getRandomPosition(world, x, y, z);
				CropType crop = this.getCropAt(world, c);
				if (crop != null && crop.isRipe(world, c.xCoord, c.yCoord, c.zCoord)) {
					int fortune = this.getFortune();
					ArrayList<ItemStack> li = crop.getDrops(world, c.xCoord, c.yCoord, c.zCoord, fortune);
					if (fortune < 3) {
						CropMethods.removeOneSeed(crop, li);
					}
					if (ReikaInventoryHelper.countEmptySlots(inv) >= li.size()) {
						ReikaInventoryHelper.addItems(this, li);
						crop.setHarvested(world, c.xCoord, c.yCoord, c.zCoord);
						ReikaSoundHelper.playBreakSound(world, x, y, z, c.getBlock(world));
						this.drainEnergy(CrystalElement.GREEN, 200);
						this.drainEnergy(CrystalElement.PURPLE, 50);
						this.sendParticles(c);
					}
				}
			}
		}
	}

	private int getNumberAttempts() {
		return this.hasSpeedUpgrade() ? 4 : 1;
	}

	private boolean hasSpeedUpgrade() {
		return false;
	}

	private void sendParticles(Coordinate c) {
		ReikaPacketHelper.sendDataPacket(ChromatiCraft.packetChannel, ChromaPackets.FARMERHARVEST.ordinal(), this, c.xCoord, c.yCoord, c.zCoord);
	}

	@SideOnly(Side.CLIENT)
	public void doParticles(int tx, int ty, int tz) {
		double v = 0.15;
		double vx = v*(tx-xCoord);
		double vy = v*(ty-yCoord);
		double vz = v*(tz-zCoord);
		EntityFX fx = new EntityBlurFX(worldObj, xCoord+0.5, yCoord+0.5, zCoord+0.5, vx, vy, vz).setColor(0, 192, 0).setScale(4).setLife(10);
		Minecraft.getMinecraft().effectRenderer.addEffect(fx);
	}

	private int getFortune() {
		return this.getEnergy(CrystalElement.PURPLE)/1000;
	}

	private CropType getCropAt(World world, Coordinate c) {
		Block b = c.getBlock(world);
		int meta = c.getBlockMetadata(world);
		CropType type = ReikaCropHelper.getCrop(b);
		if (type == null)
			type = ModCropList.getModCrop(b, meta);
		return type;
	}

	private Coordinate getRandomPosition(World world, int x, int y, int z) {
		int r = 16;
		int dx = ReikaRandomHelper.getRandomPlusMinus(x, r);
		int dz = ReikaRandomHelper.getRandomPlusMinus(z, r);
		int dy = 1+ReikaWorldHelper.findTopBlockBelowY(world, dx, y, dz);//Math.min(y, world.getTopSolidOrLiquidBlock(x, z));
		return new Coordinate(dx, dy, dz);
	}

	@Override
	public void onPathBroken(CrystalElement e) {

	}

	@Override
	public int getReceiveRange() {
		return 24;
	}

	@Override
	public boolean isConductingElement(CrystalElement e) {
		return e == CrystalElement.GREEN || e == CrystalElement.PURPLE;
	}

	@Override
	public int maxThroughput() {
		return 500;
	}

	@Override
	public boolean canConduct() {
		return true;
	}

	@Override
	public int getMaxStorage(CrystalElement e) {
		switch(e) {
		case GREEN:
			return 40000;
		case PURPLE:
			return 5000;
		default:
			return 0;
		}
	}

	@Override
	public ChromaTiles getTile() {
		return ChromaTiles.FARMER;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public boolean canExtractItem(int slot, ItemStack is, int side) {
		return true;
	}

	@Override
	public int getSizeInventory() {
		return 54;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return true;
	}

}