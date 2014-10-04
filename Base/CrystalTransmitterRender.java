/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.ChromatiCraft.Base;

import java.util.Collection;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import Reika.ChromatiCraft.ChromatiCraft;
import Reika.ChromatiCraft.Base.TileEntity.CrystalTransmitterBase;
import Reika.ChromatiCraft.Magic.CrystalTarget;
import Reika.DragonAPI.Instantiable.Data.WorldLocation;
import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;

public abstract class CrystalTransmitterRender extends ChromaRenderBase {

	@Override
	public final String getImageFileName(RenderFetcher te) {
		return "";
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8) {
		if (tile.worldObj != null) {
			CrystalTransmitterBase te = (CrystalTransmitterBase)tile;
			Collection<CrystalTarget> li = te.getTargets();
			if (!li.isEmpty()) {

				double t = (System.currentTimeMillis()/600D)%360;
				t /= 30D;

				byte sides = 6;
				double r = 0.35;//+0.025*Math.sin(t*12);

				GL11.glPushMatrix();
				GL11.glTranslated(par2, par4, par6);

				WorldLocation src = new WorldLocation(te);
				ReikaRenderHelper.disableLighting();
				GL11.glDisable(GL11.GL_CULL_FACE);
				//GL11.glEnable(GL11.GL_BLEND);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				Tessellator v5 = Tessellator.instance;
				//BlendMode.ADDITIVEDARK.apply();
				GL11.glTranslated(src.xCoord-te.xCoord+0.5, src.yCoord-te.yCoord+0.5, src.zCoord-te.zCoord+0.5);

				ReikaTextureHelper.bindTexture(ChromatiCraft.class, "/Reika/ChromatiCraft/Textures/beam.png");
				for (CrystalTarget ct : li) {

					WorldLocation tgt = ct.location;


					//v5.setColorRGBA_I(te.getColor().color.getJavaColor().brighter().getRGB(), te.renderAlpha+255);
					//v5.addVertex(src.xCoord-te.xCoord+0.5, src.yCoord-te.yCoord+0.5, src.zCoord-te.zCoord+0.5);
					double dx = tgt.xCoord-src.xCoord+ct.offsetX;
					double dy = tgt.yCoord-src.yCoord+ct.offsetY;
					double dz = tgt.zCoord-src.zCoord+ct.offsetZ;

					GL11.glPushMatrix();
					double f7 = Math.sqrt(dx*dx+dz*dz);
					double f8 = Math.sqrt(dx*dx+dy*dy+dz*dz);
					double ang1 = -Math.atan2(dz, dx) * 180 / Math.PI - 90;
					double ang2 = -Math.atan2(f7, dy) * 180 / Math.PI - 90;
					GL11.glRotated(ang1, 0, 1, 0);
					GL11.glRotated(ang2, 1, 0, 0);

					v5.startDrawing(GL11.GL_TRIANGLE_STRIP);
					v5.setColorOpaque_I(ct.color.getColor());
					for (int i = 0; i <= sides; i++) {
						double f11 = r*Math.sin(i % sides * Math.PI * 2 / sides) * 0.75;
						double f12 = r*Math.cos(i % sides * Math.PI * 2 / sides) * 0.75;
						double f13 = i % sides * 1 / sides;
						v5.addVertexWithUV(f11, f12, 0, t, t+1);
						v5.addVertexWithUV(f11, f12, f8, t+1, t);
					}

					v5.draw();
					/*
				GL11.glEnable(GL11.GL_BLEND);
				BlendMode.ADDITIVEDARK.apply();
				GL11.glPushMatrix();
				double rx = -RenderManager.instance.playerViewX+ang2;
				GL11.glRotated(rx, 0, 0, 1);
				GL11.glTranslated(-0.5, -0.5, 0);
				ReikaTextureHelper.bindTexture(ChromatiCraft.class, "/Reika/ChromatiCraft/Textures/haze2.png");
				v5.startDrawingQuads();
				v5.addVertexWithUV(0, 0.5, 0, 1, 0);
				v5.addVertexWithUV(1, 0.5, 0, 1, 1);
				v5.addVertexWithUV(1, 0.5, f8, 0, 1);
				v5.addVertexWithUV(0, 0.5, f8, 0, 0);
				v5.draw();
				GL11.glPopMatrix();
				BlendMode.DEFAULT.apply();
				GL11.glDisable(GL11.GL_BLEND);
					 */
					GL11.glPopMatrix();
				}

				//BlendMode.DEFAULT.apply();
				//GL11.glDisable(GL11.GL_BLEND);
				GL11.glShadeModel(GL11.GL_FLAT);
				GL11.glEnable(GL11.GL_CULL_FACE);
				ReikaRenderHelper.enableLighting();
				GL11.glPopMatrix();
			}
		}
	}

}
