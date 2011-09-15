package gui;

import engine.Game;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * @param g = The Graphics2D to drawn too.
 * @param resize = Size setup of the window to use.
 * @author SergeDavid
 * @version 0.1
 */
public class SmallMenu {
	public static void Draw(Graphics2D g, int resize) {
		int size = (int) Math.pow(2, Game.load.Times_Player+1);
		players.Base ply = Game.player.get(Game.btl.currentplayer);
		int x = 460;
		int y = 280;
		//TODO: Support player image offset.
		g.setColor(new Color(80,80,80));
		g.fillRect(x, y, 160, 64);
		g.drawImage(Game.img_plys,
				x,y,x+size,y+size,
				0,0,size,size,null);
		g.setColor(new Color(255,255,255));
		
		
		g.drawString("Funds: " + ply.money, x+68, y+16);
		g.drawString("Power: " + ply.power, x+68, y+32);
	
	}
}
