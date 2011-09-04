package gui;

import java.awt.Graphics2D;
import engine.Game;

/**
 * Draws all of the currently visible tiles on the screen.
 * @author SergeDavid
 * @version 0.1
 */
public class Terrain {
	public Terrain(Graphics2D gg) {
		int xoff = Game.view.ViewX();
		int yoff = Game.view.ViewY();
		for (int y=0; y < Game.map.height; y++) {
			for (int x=0; x < Game.map.width; x++) {
				if (Game.view.Viewable(x,y)) {
					int xx = Game.map.map[y][x].x();
					int yy = Game.map.map[y][x].y();
					gg.drawImage(Game.img_tile, (x-xoff)*32, (y-yoff)*32, 
												(x-xoff)*32+32, (y-yoff)*32+32, 
												 xx*32, yy*32, xx*32+32, yy*32+32, null);
				}
			}
		}
	}
}
