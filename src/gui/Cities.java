package gui;

import java.awt.Graphics2D;
import engine.Game;

/**
 * This draws all of the cities currently in the game that are visible.
 * @param g = The Graphics2D to drawn too.
 * @param resize = Size setup of the window to use.
 * @author SergeDavid
 * @version 0.4
 */
public class Cities {
	public static void Draw(Graphics2D g, int resize) {
		int size = (int) Math.pow(2, Game.load.Times_City);
		int xoff = Game.view.ViewX();
		int yoff = Game.view.ViewY();
		
		for (buildings.Base bld : Game.builds) {
			if (Game.view.Viewable(bld.x,bld.y)) {
				int[] loc = bld.DrawMe();
				g.drawImage(Game.img_city,
						(bld.x-xoff)*resize,(bld.y-yoff)*resize,(bld.x-xoff)*resize+resize,(bld.y-yoff)*resize+resize,
						loc[0]*size, loc[1]*size, loc[0]*size+size, loc[1]*size+size, null);
			}
		}
	}
}
