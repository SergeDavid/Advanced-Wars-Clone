package gui;

import java.awt.Graphics2D;
import engine.Game;

/**
 * Draws all of the currently visible tiles on the screen.
 * @param g = The Graphics2D to drawn too.
 * @param resize = Size setup of the window to use.
 * @author SergeDavid
 * @version 0.4
 */
public class Terrain {
	public static void Draw(Graphics2D g, int resize) {
		int size = (int) Math.pow(2, Game.load.Times_Terrain);
		int xoff = Game.view.ViewX();
		int yoff = Game.view.ViewY();
		
		for (int y=0; y < Game.map.height; y++) {
			for (int x=0; x < Game.map.width; x++) {
				if (Game.view.Viewable(x,y)) {
					int xx = Game.map.map[y][x].x;
					int yy = Game.map.map[y][x].y;
					g.drawImage(Game.img_tile, (x-xoff)*resize, (y-yoff)*resize, 
												(x-xoff)*resize+resize, (y-yoff)*resize+resize, 
												 xx*size, yy*size, xx*size+size, yy*size+size, null);
				}
			}
		}
	}
}
