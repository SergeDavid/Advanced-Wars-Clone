package gui;

import java.awt.Color;
import java.awt.Graphics2D;
import engine.Game;

/**
 * This draws the in-game menu with all of its parts. It is currently ugly and not very well put together sadly.
 * @author SergeDavid
 * @version 0.1
 */
public class InfoMenu {
	public InfoMenu(Graphics2D gg) {
		int offset = 514;
		switch (Game.btl.currentplayer) {//TODO: Redesign coloring to be taken from the player.
			case 0:gg.setColor(new Color(200,0,0));break;
			case 1:gg.setColor(new Color(0,0,200));break;
			case 2:gg.setColor(new Color(0,200,0));break;
			case 3:gg.setColor(new Color(200,200,0));break;
			default:gg.setColor(new Color(200,200,200));break;
		}
		gg.fillRect(offset-2, 0, Game.gui.width, Game.gui.height);
		gg.setColor(new Color(255,255,255));
		gg.drawImage(Game.img_menu[0], offset-2, 0, Game.gui.width, Game.gui.height, 0, 0, 32, 256, null);
		gg.drawString("Funds = " + Game.player.get(Game.btl.currentplayer).money,offset+4,128);
		
		DrawUnitInfo(gg, offset);
		DrawTerrainInfo(gg);
	}
	private void DrawUnitInfo(Graphics2D gg, int offset) {
		players.Base plyer = Game.player.get(Game.btl.currentplayer);
		if (plyer.unitselected) {
			units.Base unit = Game.units.get(plyer.selectedunit);
			if (unit.acted) {
				gg.drawString("Finished.",offset,64);
			}
			else if (unit.moved&&!unit.acted) {
				gg.drawString("Attacking.",offset,64);
			}
			else if (!unit.moved) {
				gg.drawString("Moving",offset,64);
			}
			//UNIT SELECTION STUFFIES!
			int[] loc = unit.DrawMe();
			gg.drawImage(Game.img_char,
					offset,8,offset+32,8+32,
					loc[0]*32,loc[1]*32,loc[0]*32+32,loc[1]*32+32,null);
			gg.drawString(unit.nick + " = " + unit.health + " HP!",offset,52);
		}
	}
	private void DrawTerrainInfo(Graphics2D gg) {
		int x = Game.player.get(Game.btl.currentplayer).selectx;
		int y = Game.player.get(Game.btl.currentplayer).selecty;
		int xx = Game.map.map[y][x].x();
		int yy = Game.map.map[y][x].y();
		gg.drawImage(Game.img_tile, 520+32, 200, 520+32+32, 232, xx*32, yy*32, xx*32+32, yy*32+32, null);
		gg.drawString(x + " and " + y, 520+32, 190);
		
	}
}
