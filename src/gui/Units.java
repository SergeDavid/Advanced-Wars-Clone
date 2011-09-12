package gui;

import java.awt.Graphics2D;
import engine.Game;

/**
 * This will draw all of the currently visible units. 
 * Units not owned by the current player are turned around. (Simple way of telling units with the same color apart)
 * @param g = The Graphics2D to drawn too.
 * @param resize = Size setup of the window to use.
 * @author SergeDavid
 * @version 0.5
 */
public class Units {
	public static void Draw(Graphics2D g, int resize) {
		int size = (int) Math.pow(2, Game.load.Times_Unit);
		int xoff = Game.view.ViewX();
		int yoff = Game.view.ViewY();
		
		for (units.Base chars : Game.units) {
			if (Game.view.Viewable(chars.x,chars.y)) {
				int[] loc = chars.DrawMe();
				if (chars.owner == Game.btl.currentplayer) {
					g.drawImage(Game.img_char,
							(chars.x-xoff)*resize,(chars.y-yoff)*resize,(chars.x-xoff)*resize+resize,(chars.y-yoff)*resize+resize,
							loc[0]*size,loc[1]*size,loc[0]*size+size,loc[1]*size+size,null);
				}
				else {
					g.drawImage(Game.img_char,
							(chars.x-xoff)*resize,(chars.y-yoff)*resize,(chars.x-xoff)*resize+resize,(chars.y-yoff)*resize+resize,
							loc[0]*size+size,loc[1]*size,loc[0]*size,loc[1]*size+size,null);
				}
				if (chars.health<chars.maxhp) {
					ActionInfo.DrawUnitHP(g, resize, chars.x, chars.y, chars.health/10);
				}
			}
		}
	}
}
