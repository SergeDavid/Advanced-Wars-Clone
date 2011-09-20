package gui;

import engine.Game;

import java.awt.Color;
import java.awt.Graphics2D;

import buildings.Base;

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
		int x = (int) (Game.gui.width * 0.75);
		int y = (int) (Game.gui.height * 0.75);
		if (OtherSide()) {
			x = (int) (Game.gui.width * 0.05);
			y = (int) (Game.gui.height * 0.75);
		}
		//TODO: Support player image offset.
		g.setColor(new Color(80,80,80));
		g.fillRect(x, y, 128, 64);
		g.drawImage(Game.img_plys,
				x,y,x+size,y+size,
				0,0,size,size,null);
		g.setColor(new Color(255,255,255));
		
		g.drawString("$" + ply.money, x+68, y+16);
		TerrainInfo(x,y,g);
		UnitInfo(x,y,g);
	}

	private static boolean OtherSide() {
		int x = Game.player.get(Game.btl.currentplayer).selectx;
		int xx = Game.view.ViewX();
		if (x-xx > Game.view.width/2) {
			return true;
		}
		return false;
	}
	private static void TerrainInfo(int x, int y, Graphics2D g) {
		int xx = Game.player.get(Game.btl.currentplayer).selectx;
		int yy = Game.player.get(Game.btl.currentplayer).selecty;
		if (Game.map.map[yy][xx].building()) {
			CityInfo(x,y,g);
			return;
		}
		int xxx = Game.map.map[yy][xx].x;
		int yyy = Game.map.map[yy][xx].y;
		g.drawImage(Game.img_tile,
				x+64,y+20,x+32+64,y+32+20,
				xxx*32,yyy*32,xxx*32+32,yyy*32+32,null);
	}
	private static void CityInfo(int x, int y, Graphics2D g) {
		int xx = Game.player.get(Game.btl.currentplayer).selectx;
		int yy = Game.player.get(Game.btl.currentplayer).selecty;
		buildings.Base bld = findCity(xx,yy);
		int[] p = bld.DrawMe();
		g.drawImage(Game.img_city,
				x+64,y+20,x+32+64,y+32+20,
				p[0]*32,p[1]*32,p[0]*32+32,p[1]*32+32,null);
	}
	private static Base findCity(int x, int y) {
		for (buildings.Base bld : Game.builds) {
			if (x == bld.x && y == bld.y) {
				return bld;
			}
		}
		return null;
	}

	private static void UnitInfo(int x, int y, Graphics2D g) {
	
	}
}
