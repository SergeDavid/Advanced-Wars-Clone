package gui;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import engine.Game;

/**
 * This draws 4 different images (each corner) of the selector and moves them in/out in connection with the current frame (at 1/2 speed) [Based off 12 max]
 * @author SergeDavid
 * @version 0.1
 */
public class Selector {
	
	private static boolean Dev = false;
	
	public static Image Draw(int frame) {
		int size = Game.load.Times_Unit;
		BufferedImage img = new BufferedImage(Game.gui.width*size, Game.gui.height*size, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) img.getGraphics();
		size *= 32;
		int x = Game.player.get(Game.btl.currentplayer).selectx;
		int y = Game.player.get(Game.btl.currentplayer).selecty;
		if (Game.view.Viewable(x,y)) {
			x -= Game.view.ViewX();
			y -= Game.view.ViewY();
			int off = (frame>5) ? frame/2 : 6-frame/2;
			off += 2;
		
			if (Dev==true) {ShowArea(g, x, y, size, off);}
			
			g.drawImage(Game.img_tile,
					x*size-off,   y*size-off,   
					x*size+(size/2)-off,   y*size+(size/2)-off,
					size*7, size, size*7+(size/2), size+(size/2),null);
			g.drawImage(Game.img_tile,
					x*size+(size/2)+off,   y*size-off,   
					x*size+size+off,   y*size+(size/2)-off,
					size*7+(size/2), size, size*7+size, size+(size/2),null);
			g.drawImage(Game.img_tile,
					x*size-off,  y*size+(size/2)+off,   
					x*size+(size/2)-off,   y*size+size+off,
					size*7, size+(size/2), size*7+(size/2), size+size,null);
			g.drawImage(Game.img_tile,
					x*size+(size/2)+off,  y*size+(size/2)+off,   
					x*size+size+off,   y*size+size+off,
					size*7+(size/2), size+(size/2), size*7+size, size+size,null);
		}
		g.dispose();
		return img;
	}
	
	private static void ShowArea(Graphics2D gg,int x, int y, int size, int off) {
		gg.drawRect(x*size-off,   y*size-off,   size/2,size/2);
		gg.drawRect(x*size+(size/2)+off,   y*size-off,   size/2,size/2);
		gg.drawRect(x*size-off,  y*size+(size/2)+off,   size/2,size/2);
		gg.drawRect(x*size+(size/2)+off,  y*size+(size/2)+off,   size/2,size/2);
	}
}
