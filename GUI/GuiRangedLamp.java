/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.ChromatiCraft.GUI;

import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import Reika.ChromatiCraft.ChromatiCraft;
import Reika.ChromatiCraft.Block.BlockRangeLamp.TileEntityRangedLamp;
import Reika.ChromatiCraft.Registry.ChromaPackets;
import Reika.DragonAPI.Base.CoreContainer;
import Reika.DragonAPI.Instantiable.GUI.ImagedGuiButton;
import Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;

public class GuiRangedLamp extends GuiContainer {

	private int channel;
	private GuiTextField input;

	private TileEntityRangedLamp lamp;

	public GuiRangedLamp(EntityPlayer ep, TileEntityRangedLamp te) {
		super(new CoreContainer(ep, te));

		lamp = te;

		ySize = 46;
		channel = te.getChannel();
	}

	@Override
	public void initGui() {
		super.initGui();
		int j = (width - xSize) / 2+8;
		int k = (height - ySize) / 2 - 12;
		input = new GuiTextField(fontRendererObj, j+xSize/2-6, k+33, 26, 16);
		input.setFocused(false);
		input.setMaxStringLength(3);
	}

	@Override
	public void keyTyped(char c, int i){
		super.keyTyped(c, i);
		input.textboxKeyTyped(c, i);
	}

	@Override
	public void mouseClicked(int i, int j, int k){
		super.mouseClicked(i, j, k);
		input.mouseClicked(i, j, k);
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		if (input.getText().isEmpty()) {
			return;
		}
		if (!(input.getText().matches("^[0-9 ]+$"))) {
			channel = 0;
			input.deleteFromCursor(-1);
			ReikaPacketHelper.sendDataPacket(ChromatiCraft.packetChannel, ChromaPackets.LAMPCHANNEL.ordinal(), lamp, channel);
			return;
		}
		channel = Integer.parseInt(input.getText());
		if (channel >= 0) {
			ReikaPacketHelper.sendDataPacket(ChromatiCraft.packetChannel, ChromaPackets.LAMPCHANNEL.ordinal(), lamp, channel);
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		ReikaTextureHelper.bindFontTexture();

		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		ReikaGuiAPI.instance.drawCenteredStringNoShadow(fontRendererObj, StatCollector.translateToLocal("chroma.lampblock"), xSize/2, 5, 4210752);

		fontRendererObj.drawString("Channel:", xSize/2-72, 25, 4210752);
		if (!input.isFocused()) {
			fontRendererObj.drawString(String.format("%d", lamp.getChannel()), xSize/2+6, 25, 0xffffffff);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int a, int b) {
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		String i = this.getFullTexturePath();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		ReikaTextureHelper.bindTexture(ChromatiCraft.class, i);
		this.drawTexturedModalRect(j, k, 0, 0, xSize, ySize);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		ReikaTextureHelper.bindTexture(ChromatiCraft.class, i);

		input.drawTextBox();
		int color = 4210752;
		ImagedGuiButton.drawCenteredStringNoShadow(fontRendererObj, String.format("(%d)", lamp.getChannel()), j+xSize/2+58, k+25, color);
	}

	public final String getFullTexturePath() {
		return "/Reika/ChromatiCraft/Textures/GUIs/rangelamp.png";
	}

}
