package engine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.JPanel;
	
public class Gui extends JPanel {
	private static final long serialVersionUID = 3457450162330022096L;
	
	//The width and height of the content box.
	public int width = Game.ScreenBase*20;
	public int height = Game.ScreenBase*12;
	private Game ThisMadeMeWantToRageQuit; // Use for resizing the window.
	
	/**The base frame to keep animations in sync (1 frame = 100ms) Remember to reset this to zero when it hits 12.*/
	public int frame = 0; //counts up to 12 (resets to zero)
	
	//For menu's
	public boolean MenuBackground;
	public int[] MenuSize = {0,0,0,0};//X, Y, Width, Height
		
	public Gui(Game game) {
		setPreferredSize(new Dimension(width,height));
		setBounds(0, 0, width, height);
		setLayout(null);
		ThisMadeMeWantToRageQuit = game;
	}

	/**Creates the Login screen layout*/
	public void LoginScreen() {
		Game.GameState=Game.State.MENU;
		removeAll();
		new menus.StartMenu();
		if (Game.error.showing) {add(Game.error);}
	}
	/**Creates the InGame screen layout*/
	public void InGameScreen() {
		removeAll();
		Game.GameState=Game.State.PLAYING;
		if (Game.error.showing) {add(Game.error);}
	}

	public void paintComponent(Graphics g) {
		//Creates the buffer image and graphic settings.
		Image buffimage = createImage(width, height);
		Graphics2D gg = (Graphics2D) buffimage.getGraphics();
		
		switch (Game.GameState) {
		case MENU:
			gg.setColor(new Color(0,0,60));
			gg.fillRect(0, 0, width, height);
			gg.setColor(new Color(255,255,255));
		break;
		case PLAYING:
			gg.setColor(GrabColor());
			gg.fillRect(0, 0, width, height);
			gg.drawImage(Game.img_menu[0], 0, 0, width, height, 0, 0, 32, 256, null);
			//long start = System.currentTimeMillis();
			gui.Terrain.Draw(gg, Game.ScreenBase);
			gui.Ranges.Draw(gg, Game.ScreenBase);
			gui.Cities.Draw(gg, Game.ScreenBase);
			gui.Units.Draw(gg, Game.ScreenBase);
			gui.Selector.Draw(frame, gg, Game.ScreenBase,
					Game.player.get(Game.btl.currentplayer).selectx,
					Game.player.get(Game.btl.currentplayer).selecty);
			gui.SmallMenu.Draw(gg,frame);
		break;
		case EDITOR:
			gg.setColor(new Color(0,0,60));
			gg.fillRect(0, 0, width, height);
			gg.setColor(new Color(255,255,255));
			gui.Terrain.Draw(gg, Game.ScreenBase);
			gui.Cities.Draw(gg, Game.ScreenBase);
			gui.Units.Draw(gg, Game.ScreenBase);
			gui.Selector.Draw(frame, gg, Game.ScreenBase,
					Game.edit.selectx,
					Game.edit.selecty);
		break;
		}
		
		if (MenuBackground) {
			gg.setColor(new Color(150,150,150));
			gg.fillRect(MenuSize[0], MenuSize[1], MenuSize[2], MenuSize[3]);
		}
		
		g.drawImage(buffimage, 0, 0, this);
		buffimage.flush();
		gg.dispose();
	}

	private Color GrabColor() {
		switch (Game.btl.currentplayer) {//TODO: Redesign coloring to be taken from the player.
			case 0:return new Color(200,0,0);
			case 1:return new Color(0,0,200);
			case 2:return new Color(0,200,0);
			case 3:return new Color(200,200,0);
			default:return new Color(200,200,200);
		}
	}

	/**
	 * Changes the size of the game screens, the setup is 5:3 ratio.
	 */
	public void ResizeScreen(int size) {
		switch  (size) {//Ratio is 5:3 (20:12)
			//case 1: size=16; break; // 320 x 192
			case 1: size=24; break; // 480 x 288
			case 2: size=32; break; // 640 x 384
			case 3: size=48; break; // 960 x 
			case 4: size=64; break; // 1280 x 800
			default: size=32; break;
		}
		//Window
		ThisMadeMeWantToRageQuit.setSize(new Dimension(20*size+6,12*size+12));
		ThisMadeMeWantToRageQuit.setBounds(0,0,20*size+6,12*size+12);
		ThisMadeMeWantToRageQuit.setLocationRelativeTo(null);
		Game.ScreenBase = size;
		//GUI
		width = size*20;
		height = size*12;
		setPreferredSize(new Dimension(width,height));
		setBounds(0, 0, width, height);
		//TODO: Resize menu's based on current size.
	}
}
