package engine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JPanel;

/**
 * This is the game's universal menu, the actual menu items are handled in the menus package classes, but are displayed here.
 * @author SergeDavid
 * @version 0.3
 */
public class GameMenus extends JPanel {
	private static final long serialVersionUID = -7953759133984304287L;

	public GameMenus() {
		setBackground(new Color(80,80,80));
		//Setup Unit menu
		//Setup Yes/No menu
		//Add Commander Powers to the Pause menu
		//Move StartMenu to menu
	}

	/**
	 * This adds the menu JPanel to the game, as well as sets its in the middle of the game.
	 * @param width = Width of the menu area
	 * @param height = Height of the menu area
	 */
	public void OpenMenu(int width, int height) {
		removeAll();
		Insets insets = Game.gui.getInsets();
		Dimension size = Game.gui.getPreferredSize();
		//0 -> 600/300 -> 100->50 = Middle of the screen.
		setBounds(insets.left + size.width/2 - width/2,insets.top + size.height/2 - height/2, width, height);
		Game.gui.add(this);
		Game.input.MenuHack=true;
		requestFocusInWindow();
	}

	public void CloseMenu() {
		Game.gui.remove(this);
		Game.input.MenuHack=false;
		Game.gui.requestFocusInWindow();
		setOpaque(true);
	}
}
