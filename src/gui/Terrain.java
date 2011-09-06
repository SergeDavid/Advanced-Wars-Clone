package gui;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import engine.Game;

/**
 * Draws all of the currently visible tiles on the screen.
 * @author SergeDavid
 * @version 0.2
 */
public class Terrain {
	public static Image Draw() {
		int size = Game.load.Times_Terrain;
		BufferedImage img = new BufferedImage(Game.gui.width*size, Game.gui.height*size, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) img.getGraphics();
		
		size *= 32;
		int xoff = Game.view.ViewX();
		int yoff = Game.view.ViewY();
		
		for (int y=0; y < Game.map.height; y++) {
			for (int x=0; x < Game.map.width; x++) {
				if (Game.view.Viewable(x,y)) {
					int xx = Game.map.map[y][x].x();
					int yy = Game.map.map[y][x].y();
					g.drawImage(Game.img_tile, (x-xoff)*size, (y-yoff)*size, 
												(x-xoff)*size+size, (y-yoff)*size+size, 
												 xx*size, yy*size, xx*size+size, yy*size+size, null);
				}
			}
		}
		g.dispose();
		return img;
	}
}
