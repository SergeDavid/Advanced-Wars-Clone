package menus;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
import engine.Game;

/**
 * This shares the common methods shared between all of the menu classes.
 * @author SergeDavid
 * @version 0.4
 */
public class MenuHandler {
	/**
	 * This returns the top 
	 * @param width = Width of the menu area
	 * @param height = Height of the menu area
	 */
	public static Point PrepMenu(int width, int height) {
		Game.gui.removeAll();
		if (Game.error.showing) {Game.gui.add(Game.error);}
		
		Insets insets = Game.gui.getInsets();
		Dimension size = Game.gui.getPreferredSize();
		Point me = new Point(//The two points that make up the top corner of the screen
				insets.left + size.width/2 - width/2,
				insets.top + size.height/2 - height/2
				);
		
		SetBackground(me.x, me.y, width, height);
		return me;
	}

	private static void SetBackground(int x, int y, int width, int height) {
		Game.gui.MenuBackground = true;
		Game.gui.MenuSize[0] = x;
		Game.gui.MenuSize[1] = y;
		Game.gui.MenuSize[2] = width;
		Game.gui.MenuSize[3] = height;
	}
	public static void HideBackground() {
		Game.gui.MenuBackground = false;
	}
	
	public static void CloseMenu() {
		Game.gui.MenuBackground = false;
		Game.gui.removeAll();
		Game.gui.requestFocusInWindow();
		if (Game.error.showing) {Game.gui.add(Game.error);}
	}
	
	public static void ResizeFont() {
		
	}
}
