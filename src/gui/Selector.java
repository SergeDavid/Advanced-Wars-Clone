package gui;

import java.awt.Graphics2D;
import engine.Game;

/**
 * This draws 4 different images (each corner) of the selector and moves them in/out in connection with the current frame (at 1/2 speed) [Based off 12 max]
 * @param g = The Graphics2D to drawn too.
 * @param resize = Size setup of the window to use.
 * @author SergeDavid
 * @version 0.5
 */
public class Selector {
	
	private static boolean Dev = false;
	
	public static void Draw(int frame, Graphics2D g, int resize, int x, int y) {
		int size = (int) Math.pow(2, Game.load.Times_Extras);
		if (Game.view.Viewable(x,y)) {
			x -= Game.view.ViewX();
			y -= Game.view.ViewY();
			int off = (frame>5) ? frame/2 : 6-frame/2;
			off += 2;
		
			if (Dev==true) {ShowArea(g, x, y, resize, off);}
			
			g.drawImage(Game.img_exts,
					x*resize-off,   y*resize-off,   
					x*resize+(resize/2)-off,   y*resize+(resize/2)-off,
					size*3, size*3, size*3+(size/2), size*3+(size/2),null);
			g.drawImage(Game.img_exts,
					x*resize+(resize/2)+off,   y*resize-off,   
					x*resize+resize+off,   y*resize+(resize/2)-off,
					size*3+(size/2), size*3, size*3+size, size*3+(size/2),null);
			g.drawImage(Game.img_exts,
					x*resize-off,  y*resize+(resize/2)+off,   
					x*resize+(resize/2)-off,   y*resize+resize+off,
					size*3, size*3+(size/2), size*3+(size/2), size*3+size,null);
			g.drawImage(Game.img_exts,
					x*resize+(resize/2)+off,  y*resize+(resize/2)+off,   
					x*resize+resize+off,   y*resize+resize+off,
					size*3+(size/2), size*3+(size/2), size*3+size, size*3+size,null);
		}
	}
	
	private static void ShowArea(Graphics2D gg,int x, int y, int size, int off) {
		gg.drawRect(x*size-off,   y*size-off,   size/2,size/2);
		gg.drawRect(x*size+(size/2)+off,   y*size-off,   size/2,size/2);
		gg.drawRect(x*size-off,  y*size+(size/2)+off,   size/2,size/2);
		gg.drawRect(x*size+(size/2)+off,  y*size+(size/2)+off,   size/2,size/2);
	}
}
