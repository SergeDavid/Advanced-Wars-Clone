package gui;

import java.awt.Graphics2D;
import engine.Game;

/**
 * This draws all of the cities currently in the game that are visible.
 * @author SergeDavid
 * @version 0.1
 */
public class Cities {
	public Cities(Graphics2D gg) {
		int xoff = Game.view.ViewX();
		int yoff = Game.view.ViewY();
		for (buildings.Base bld : Game.builds) {
			if (Game.view.Viewable(bld.x,bld.y)) {
				int[] loc = bld.DrawMe();
				gg.drawImage(Game.img_city,
						(bld.x-xoff)*32,(bld.y-yoff)*32,(bld.x-xoff)*32+32,(bld.y-yoff)*32+32,
						loc[0]*32, loc[1]*32, loc[0]*32+32, loc[1]*32+32, null);
			}
		}
	}
}
