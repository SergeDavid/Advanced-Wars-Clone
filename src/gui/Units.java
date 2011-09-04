package gui;

import java.awt.Graphics2D;
import engine.Game;

/**
 * This will draw all of the currently visible units.
 * @author SergeDavid
 * @version 0.1
 */
public class Units {
	public Units(Graphics2D gg) {
		int xoff = Game.view.ViewX();
		int yoff = Game.view.ViewY();
		for (units.Base chars : Game.units) {
			if (Game.view.Viewable(chars.x,chars.y)) {
				int[] loc = chars.DrawMe();
				gg.drawImage(Game.img_char,
					(chars.x-xoff)*32,(chars.y-yoff)*32,(chars.x-xoff)*32+32,(chars.y-yoff)*32+32,
					loc[0]*32,loc[1]*32,loc[0]*32+32,loc[1]*32+32,null);
			}
		}
	}
}
