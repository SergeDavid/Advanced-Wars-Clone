package gui;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import engine.Game;

/**
 * This draws all of the cities currently in the game that are visible.
 * @author SergeDavid
 * @version 0.2
 */
public class Cities {
	public static Image Draw() {
		int size = Game.load.Times_City;
		BufferedImage img = new BufferedImage(Game.gui.width*size, Game.gui.height*size, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) img.getGraphics();
		
		size *= 32;
		int xoff = Game.view.ViewX();
		int yoff = Game.view.ViewY();
		
		for (buildings.Base bld : Game.builds) {
			if (Game.view.Viewable(bld.x,bld.y)) {
				int[] loc = bld.DrawMe();
				g.drawImage(Game.img_city,
						(bld.x-xoff)*size,(bld.y-yoff)*size,(bld.x-xoff)*size+size,(bld.y-yoff)*size+size,
						loc[0]*size, loc[1]*size, loc[0]*size+size, loc[1]*size+size, null);
			}
		}
		g.dispose();
		return img;
	}
}
