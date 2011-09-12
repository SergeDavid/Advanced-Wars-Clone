package gui;

import java.awt.Graphics2D;
import engine.Game;

/**
 * This draws the quick info for units and buildings. (Health, Capturing, Low Ammo/Fuel)
 * @param g = The Graphics2D to drawn too.
 * @param resize = Size setup of the window to use.
 * @author SergeDavid
 * @version 0.2
 */
public class ActionInfo {
	public static void DrawUnitHP(Graphics2D g, int resize, int x, int y, int hp) {
		int size = (int) Math.pow(2, Game.load.Times_Extras);
		int xoff = Game.view.ViewX();
		int yoff = Game.view.ViewY();
		
		int[] loc = GrabHealth(size,hp);
		g.drawImage(Game.img_exts,
				(x-xoff)*resize+resize/2,(y-yoff)*resize+resize/2,(x-xoff)*resize+resize,(y-yoff)*resize+resize,
				loc[0], loc[1], loc[2], loc[3], null);
	}
	
	private static int[] GrabHealth(int size,int hp) {
		int[] loc = {size+size/2*(hp),0,size+size/2+size/2*(hp),size/2};
		if (hp>5) {
			loc[1]+=size/2;
			loc[3]+=size/2;
			loc[0]-=size/2*6;
			loc[2]-=size/2*6;
		}
		return loc;
	}
}
