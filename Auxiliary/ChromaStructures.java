/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.ChromatiCraft.Auxiliary;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import Reika.ChromatiCraft.Registry.ChromaBlocks;
import Reika.ChromatiCraft.Registry.ChromaTiles;
import Reika.ChromatiCraft.Registry.CrystalElement;
import Reika.DragonAPI.Instantiable.Data.FilledBlockArray;

public class ChromaStructures {

	public static FilledBlockArray getRitualStructure(World world, int x, int y, int z) {
		FilledBlockArray array = new FilledBlockArray(world);
		Block b = ChromaBlocks.PYLONSTRUCT.getBlockInstance();
		for (int i = -5; i <= 5; i++) {
			for (int k = -5; k <= 5; k++) {
				for (int j = 1; j <= 3; j++)
					array.setBlock(x+i, y+j, z+k, Blocks.air);
			}
		}

		for (int i = -5; i <= 5; i++) {
			for (int k = -5; k <= 5; k++) {
				array.setBlock(x+i, y, z+k, b, 0);
			}
		}

		for (int i = -4; i <= 4; i++) {
			for (int k = -4; k <= 4; k++) {
				array.setBlock(x+i, y+1, z+k, b, 0);
			}
		}

		for (int i = -4; i <= 4; i++) {
			array.setBlock(x-4, y+1, z+i, b, 1);
			array.setBlock(x+4, y+1, z+i, b, 1);
			array.setBlock(x+i, y+1, z-4, b, 1);
			array.setBlock(x+i, y+1, z+4, b, 1);
		}

		for (int i = -3; i <= 3; i++) {
			array.setBlock(x-3, y+2, z+i, b, 1);
			array.setBlock(x+3, y+2, z+i, b, 1);
			array.setBlock(x+i, y+2, z-3, b, 1);
			array.setBlock(x+i, y+2, z+3, b, 1);
		}

		array.setBlock(x+2, y+2, z+2, b, 2);
		array.setBlock(x-2, y+2, z+2, b, 2);
		array.setBlock(x+2, y+2, z-2, b, 2);
		array.setBlock(x-2, y+2, z-2, b, 2);

		array.setBlock(x+2, y+3, z+2, b, 7);
		array.setBlock(x-2, y+3, z+2, b, 7);
		array.setBlock(x+2, y+3, z-2, b, 7);
		array.setBlock(x-2, y+3, z-2, b, 7);

		array.setBlock(x+3, y+2, z+3, b, 8);
		array.setBlock(x-3, y+2, z+3, b, 8);
		array.setBlock(x+3, y+2, z-3, b, 8);
		array.setBlock(x-3, y+2, z-3, b, 8);

		array.setBlock(x+4, y+1, z+4, b, 8);
		array.setBlock(x-4, y+1, z+4, b, 8);
		array.setBlock(x+4, y+1, z-4, b, 8);
		array.setBlock(x-4, y+1, z-4, b, 8);

		array.setBlock(x-1, y+1, z-1, ChromaBlocks.CHROMA.getBlockInstance());
		array.setBlock(x, y+1, z-1, ChromaBlocks.CHROMA.getBlockInstance());
		array.setBlock(x+1, y+1, z-1, ChromaBlocks.CHROMA.getBlockInstance());
		array.setBlock(x+1, y+1, z, ChromaBlocks.CHROMA.getBlockInstance());
		array.setBlock(x+1, y+1, z+1, ChromaBlocks.CHROMA.getBlockInstance());
		array.setBlock(x, y+1, z+1, ChromaBlocks.CHROMA.getBlockInstance());
		array.setBlock(x-1, y+1, z+1, ChromaBlocks.CHROMA.getBlockInstance());
		array.setBlock(x-1, y+1, z, ChromaBlocks.CHROMA.getBlockInstance());

		array.setBlock(x, y+2, z, ChromaTiles.RITUAL.getBlock(), ChromaTiles.RITUAL.getBlockMetadata());

		return array;
	}

	public static FilledBlockArray getCastingLevelOne(World world, int x, int y, int z) {
		FilledBlockArray array = new FilledBlockArray(world);

		for (int i = -6; i <= 6; i++) {
			for (int k = 0; k < 6; k++) {
				int dy = y+k;
				array.setBlock(x-6, dy, z+i, Blocks.air);
				array.setBlock(x+6, dy, z+i, Blocks.air);
				array.setBlock(x+i, dy, z-6, Blocks.air);
				array.setBlock(x+i, dy, z+6, Blocks.air);
			}
		}

		Block b = ChromaBlocks.PYLONSTRUCT.getBlockInstance();
		for (int i = 2; i < 6; i++) {
			ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[i];
			for (int k = 3; k <= 5; k++) {
				int dx = x+k*dir.offsetX;
				int dz = z+k*dir.offsetZ;
				array.setBlock(dx, y, dz, b, 0);
			}

			int dx = x+dir.offsetX*6;
			int dz = z+dir.offsetZ*6;
			for (int k = 1; k <= 5; k++) {
				int meta2 = k == 1 ? 8 : 2;
				int dy = y+k;
				array.setBlock(dx, dy, dz, b, meta2);
			}
		}

		for (int i = -6; i <= 6; i++) {
			array.setBlock(x-6, y, z+i, b, 0);
			array.setBlock(x+6, y, z+i, b, 0);
			array.setBlock(x+i, y, z-6, b, 0);
			array.setBlock(x+i, y, z+6, b, 0);
		}

		for (int k = 1; k <= 4; k++) {
			int meta2 = k == 1 ? 0 : 2;
			int dy = y+k;
			array.setBlock(x+6, dy, z+6, b, meta2);
			array.setBlock(x-6, dy, z+6, b, meta2);
			array.setBlock(x+6, dy, z-6, b, meta2);
			array.setBlock(x-6, dy, z-6, b, meta2);
		}

		for (int k = 1; k <= 6; k++) {
			int meta2 = k == 1 || k == 5 ? 0 : (k == 6 ? 7 : 2);
			int dy = y+k;
			array.setBlock(x+6, dy, z+3, b, meta2);
			array.setBlock(x+6, dy, z-3, b, meta2);
			array.setBlock(x-6, dy, z+3, b, meta2);
			array.setBlock(x-6, dy, z-3, b, meta2);
			array.setBlock(x+3, dy, z-6, b, meta2);
			array.setBlock(x-3, dy, z-6, b, meta2);
			array.setBlock(x-3, dy, z+6, b, meta2);
			array.setBlock(x+3, dy, z+6, b, meta2);
		}

		for (int i = -5; i <= 5; i++) {
			if (i != 3 && i != -3 && i != 0) {
				int dy = Math.abs(i) < 3 ? y+6 : y+5;
				array.setBlock(x-6, dy, z+i, b, 1);
				array.setBlock(x+6, dy, z+i, b, 1);
				array.setBlock(x+i, dy, z-6, b, 1);
				array.setBlock(x+i, dy, z+6, b, 1);
			}
		}

		for (int i = -3; i <= 3; i++) {
			for (int k = 0; k <= 1; k++) {
				if (k == 0 || Math.abs(i)%2 == 1) {
					int dy = y+k;
					array.setBlock(x-3, dy, z+i, b, 0);
					array.setBlock(x+3, dy, z+i, b, 0);
					array.setBlock(x+i, dy, z-3, b, 0);
					array.setBlock(x+i, dy, z+3, b, 0);
				}
			}
		}

		array.setBlock(x-6, y+5, z-6, Blocks.coal_block);
		array.setBlock(x+6, y+5, z-6, Blocks.coal_block);
		array.setBlock(x+6, y+5, z+6, Blocks.coal_block);
		array.setBlock(x-6, y+5, z+6, Blocks.coal_block);

		array.setBlock(x, y+6, z-6, Blocks.lapis_block);
		array.setBlock(x, y+6, z+6, Blocks.lapis_block);
		array.setBlock(x+6, y+6, z, Blocks.lapis_block);
		array.setBlock(x-6, y+6, z, Blocks.lapis_block);
		return array;
	}

	public static FilledBlockArray getCastingLevelTwo(World world, int x, int y, int z) {
		FilledBlockArray array = getCastingLevelOne(world, x, y, z);

		Block b = ChromaBlocks.PYLONSTRUCT.getBlockInstance();
		for (int i = -5; i <= 5; i++) {
			for (int k = -5; k <= 5; k++) {
				int dx = x+i;
				int dz = z+k;
				array.setBlock(dx, y, dz, b, 0);
			}
		}

		for (int i = -5; i <= 5; i++) {
			if (i != 0 && Math.abs(i) != 3) {
				array.setBlock(x-6, y, z+i, Blocks.quartz_block, 0);
				array.setBlock(x+6, y, z+i, Blocks.quartz_block, 0);
				array.setBlock(x+i, y, z-6, Blocks.quartz_block, 0);
				array.setBlock(x+i, y, z+6, Blocks.quartz_block, 0);
			}
		}

		for (int i = -3; i <= 3; i++) {
			int dy = y+1;
			array.remove(x-3, dy, z+i);
			array.remove(x+3, dy, z+i);
			array.remove(x+i, dy, z-3);
			array.remove(x+i, dy, z+3);
		}

		for (int i = -2; i <= 2; i++) {
			array.remove(x-2, y, z+i);
			array.remove(x+2, y, z+i);
			array.remove(x+i, y, z-2);
			array.remove(x+i, y, z+2);
		}

		array.setBlock(x-6, y+5, z-6, Blocks.redstone_block);
		array.setBlock(x+6, y+5, z-6, Blocks.redstone_block);
		array.setBlock(x+6, y+5, z+6, Blocks.redstone_block);
		array.setBlock(x-6, y+5, z+6, Blocks.redstone_block);

		array.setBlock(x, y+6, z-6, Blocks.gold_block);
		array.setBlock(x, y+6, z+6, Blocks.gold_block);
		array.setBlock(x+6, y+6, z, Blocks.gold_block);
		array.setBlock(x-6, y+6, z, Blocks.gold_block);
		return array;
	}

	public static FilledBlockArray getCastingLevelThree(World world, int x, int y, int z) {
		FilledBlockArray array = getCastingLevelTwo(world, x, y, z);
		Block b = ChromaBlocks.PYLONSTRUCT.getBlockInstance();

		for (int i = -7; i <= 7; i++) {
			array.setBlock(x-7, y, z+i, b, 0);
			array.setBlock(x+7, y, z+i, b, 0);
			array.setBlock(x+i, y, z-7, b, 0);
			array.setBlock(x+i, y, z+7, b, 0);
		}

		for (int i = -8; i <= 8; i++) {
			array.setBlock(x-8, y, z+i, Blocks.obsidian);
			array.setBlock(x+8, y, z+i, Blocks.obsidian);
			array.setBlock(x+i, y, z-8, Blocks.obsidian);
			array.setBlock(x+i, y, z+8, Blocks.obsidian);
		}

		for (int i = 1; i <= 4; i++) {
			int dy = y+i;
			if (i == 3) {
				Block b2 = ChromaBlocks.RUNE.getBlockInstance();
				array.setBlock(x-2, dy, z-8, b2);
				array.setBlock(x-6, dy, z-8, b2);
				array.setBlock(x+2, dy, z-8, b2);
				array.setBlock(x+6, dy, z-8, b2);

				array.setBlock(x-2, dy, z+8, b2);
				array.setBlock(x-6, dy, z+8, b2);
				array.setBlock(x+2, dy, z+8, b2);
				array.setBlock(x+6, dy, z+8, b2);

				array.setBlock(x-8, dy, z-2, b2);
				array.setBlock(x-8, dy, z-6, b2);
				array.setBlock(x-8, dy, z+2, b2);
				array.setBlock(x-8, dy, z+6, b2);

				array.setBlock(x+8, dy, z+6, b2);
				array.setBlock(x+8, dy, z+2, b2);
				array.setBlock(x+8, dy, z-6, b2);
				array.setBlock(x+8, dy, z-2, b2);
			}
			else {
				Block b2 = i == 4 ? ChromaTiles.REPEATER.getBlock() : b;
				int meta2 = i == 4 ? ChromaTiles.REPEATER.getBlockMetadata() : 0;
				array.setBlock(x-2, dy, z-8, b2, meta2);
				array.setBlock(x-6, dy, z-8, b2, meta2);
				array.setBlock(x+2, dy, z-8, b2, meta2);
				array.setBlock(x+6, dy, z-8, b2, meta2);

				array.setBlock(x-2, dy, z+8, b2, meta2);
				array.setBlock(x-6, dy, z+8, b2, meta2);
				array.setBlock(x+2, dy, z+8, b2, meta2);
				array.setBlock(x+6, dy, z+8, b2, meta2);

				array.setBlock(x-8, dy, z-2, b2, meta2);
				array.setBlock(x-8, dy, z-6, b2, meta2);
				array.setBlock(x-8, dy, z+2, b2, meta2);
				array.setBlock(x-8, dy, z+6, b2, meta2);

				array.setBlock(x+8, dy, z+6, b2, meta2);
				array.setBlock(x+8, dy, z+2, b2, meta2);
				array.setBlock(x+8, dy, z-6, b2, meta2);
				array.setBlock(x+8, dy, z-2, b2, meta2);
			}
		}

		for (int i = 1; i <= 3; i++) {
			int dy = y+i;
			int meta = i == 1 ? 0 : i == 2 ? 2 : 7;
			array.setBlock(x-8, dy, z-8, b, meta);
			array.setBlock(x+8, dy, z-8, b, meta);
			array.setBlock(x-8, dy, z+8, b, meta);
			array.setBlock(x+8, dy, z+8, b, meta);
		}

		array.setBlock(x-6, y+5, z-6, Blocks.glowstone);
		array.setBlock(x+6, y+5, z-6, Blocks.glowstone);
		array.setBlock(x+6, y+5, z+6, Blocks.glowstone);
		array.setBlock(x-6, y+5, z+6, Blocks.glowstone);

		array.setBlock(x, y+6, z-6, Blocks.diamond_block);
		array.setBlock(x, y+6, z+6, Blocks.diamond_block);
		array.setBlock(x+6, y+6, z, Blocks.diamond_block);
		array.setBlock(x-6, y+6, z, Blocks.diamond_block);
		return array;
	}

	public static FilledBlockArray getPylonStructure(World world, int x, int y, int z, CrystalElement e) {
		FilledBlockArray array = new FilledBlockArray(world);
		Block b = ChromaBlocks.PYLONSTRUCT.getBlockInstance();
		for (int n = 0; n <= 9; n++) {
			int dy = y+n;
			Block b2 = n == 0 ? b : Blocks.air;
			for (int i = 2; i < 6; i++) {
				ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[i];
				for (int k = 0; k <= 3; k++) {
					int dx = x+dir.offsetX*k;
					int dz = z+dir.offsetZ*k;
					array.setBlock(dx, dy, dz, b2, 0);
					if (dir.offsetX == 0) {
						array.setBlock(dx+dir.offsetZ, dy, dz, b2, 0);
						array.setBlock(dx-dir.offsetZ, dy, dz, b2, 0);
					}
					else if (dir.offsetZ == 0) {
						array.setBlock(dx, dy, dz+dir.offsetX, b2, 0);
						array.setBlock(dx, dy, dz-dir.offsetX, b2, 0);
					}
				}
			}
		}

		for (int i = 1; i <= 5; i++) {
			int dy = y+i;
			Block b2 = i < 5 ? b : ChromaBlocks.RUNE.getBlockInstance();
			int meta = (i == 2 || i == 3) ? 2 : (i == 4 ? 7 : 8);
			if (i == 5) //rune
				meta = e.ordinal();
			array.setBlock(x-3, dy, z+1, b2, meta);
			array.setBlock(x-3, dy, z-1, b2, meta);

			array.setBlock(x+3, dy, z+1, b2, meta);
			array.setBlock(x+3, dy, z-1, b2, meta);

			array.setBlock(x-1, dy, z+3, b2, meta);
			array.setBlock(x-1, dy, z-3, b2, meta);

			array.setBlock(x+1, dy, z+3, b2, meta);
			array.setBlock(x+1, dy, z-3, b2, meta);
		}

		for (int n = 1; n <= 7; n++) {
			int dy = y+n;
			for (int i = -1; i <= 1; i += 2) {
				int dx = x+i;
				for (int k = -1; k <= 1; k += 2) {
					int dz = z+k;
					int meta = n == 5 ? 3 : (n == 7 ? 5 : 2);
					array.setBlock(dx, dy, dz, b, meta);
				}
			}
		}

		array.setBlock(x-3, y+4, z, b, 4);
		array.setBlock(x+3, y+4, z, b, 4);
		array.setBlock(x, y+4, z-3, b, 4);
		array.setBlock(x, y+4, z+3, b, 4);


		array.setBlock(x-2, y+3, z+1, b, 1);
		array.setBlock(x-2, y+3, z-1, b, 1);

		array.setBlock(x+2, y+3, z+1, b, 1);
		array.setBlock(x+2, y+3, z-1, b, 1);

		array.setBlock(x-1, y+3, z+2, b, 1);
		array.setBlock(x-1, y+3, z-2, b, 1);

		array.setBlock(x+1, y+3, z+2, b, 1);
		array.setBlock(x+1, y+3, z-2, b, 1);

		array.remove(x, y+9, z);

		array.remove(x-3, y+6, z-1);
		array.remove(x-1, y+6, z-3);

		array.remove(x+3, y+6, z-1);
		array.remove(x+1, y+6, z-3);

		array.remove(x-3, y+6, z+1);
		array.remove(x-1, y+6, z+3);

		array.remove(x+3, y+6, z+1);
		array.remove(x+1, y+6, z+3);

		return array;
	}

}