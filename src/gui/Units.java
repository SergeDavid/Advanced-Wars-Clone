package gui;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import engine.Game;

/**
 * This will draw all of the currently visible units. 
 * Units not owned by the current player are turned around. (Simple way of telling units with the same color apart)
 * @author SergeDavid
 * @version 0.3
 */
public class Units {
	public static Image Draw() {
		int size = Game.load.Times_Unit;
		BufferedImage img = new BufferedImage(Game.gui.width*size, Game.gui.height*size, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) img.getGraphics();
		
		size *= 32;
		int xoff = Game.view.ViewX();
		int yoff = Game.view.ViewY();
		
		for (units.Base chars : Game.units) {
			if (Game.view.Viewable(chars.x,chars.y)) {
				int[] loc = chars.DrawMe();
				if (chars.owner == Game.btl.currentplayer) {
					g.drawImage(Game.img_char,
							(chars.x-xoff)*size,(chars.y-yoff)*size,(chars.x-xoff)*size+size,(chars.y-yoff)*size+size,
							loc[0]*size,loc[1]*size,loc[0]*size+size,loc[1]*size+size,null);
				}
				else {
					g.drawImage(Game.img_char,
							(chars.x-xoff)*size,(chars.y-yoff)*size,(chars.x-xoff)*size+size,(chars.y-yoff)*size+size,
							loc[0]*size+size,loc[1]*size,loc[0]*size,loc[1]*size+size,null);
				}
			}
		}
		g.dispose();
		return img;
	}
}
