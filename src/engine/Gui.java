
package engine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
	
public class Gui extends JPanel {
	private static final long serialVersionUID = 3457450162330022096L;
	
	/**The width and height of the content box.*/
	public int width = Game.ScreenBase*20;
	public int height = (int) (Game.ScreenBase*12);
	private Game ThisMadeMeWantToRageQuit; // Use for resizing the window.
	
	/**The base frame to keep animations in sync (1 frame = 100ms) Remember to reset this to zero when it hits 12.*/
	public int frame = 0; //counts up to 12 (resets to zero)

	//Main Menu
	public JButton Join = new JButton("New Game");
	public JButton Load = new JButton("Continue");
	public JButton Options = new JButton("Options");
	public JButton Exit = new JButton("Exit");

	JList maps_list = new JList();
	DefaultListModel maps_model = new DefaultListModel();
	JList packs_list = new JList();
	DefaultListModel packs_model = new DefaultListModel();
		
	public Gui(Game game) {
		setPreferredSize(new Dimension(width,height));
		setBounds(0, 0, width, height);
		setLayout(null);
		ThisMadeMeWantToRageQuit = game;
		
		LoginScreen();
	}

	/**Creates the Login screen layout*/
	public void LoginScreen() {
		Game.GameState=Game.State.MENU;
		removeAll();
		
		add(Join);
		add(Load);
		add(Options);
		add(Exit);
		
		Insets insets = getInsets();
		Dimension size = Join.getPreferredSize();
		Join.setBounds(220 + insets.left, 200 + insets.top, 
				size.width, size.height);
		size = Load.getPreferredSize();
		Load.setBounds(220 + insets.left, 230 + insets.top, 
				size.width, size.height);
		size = Options.getPreferredSize();
		Options.setBounds(220 + insets.left, 260 + insets.top, 
				size.width, size.height);
		size = Exit.getPreferredSize();
		Exit.setBounds(220 + insets.left, 290 + insets.top, 
				size.width, size.height);
		
		maps_model = Game.finder.GrabMaps();
		JScrollPane maps_pane = new JScrollPane(maps_list = new JList(maps_model));
		maps_pane.setBounds(400 + insets.left, 40 + insets.top, 140, 300);
		add(maps_pane);
		size = maps_list.getPreferredSize();
		maps_list.setBounds(insets.left, insets.top, size.width, size.height);
		maps_list.setSelectedIndex(0);
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
			gui.Selector.Draw(frame, gg, Game.ScreenBase);
			//System.out.println("Time: " + (System.currentTimeMillis() - start));
			
			if (Game.input.MenuHack) {new gui.InfoMenu(gg);}//This displays the menu only when it is paused.
			else {/*Include a mini menu that floats around the map*/}
		break;
		case EDITOR:
			gui.Terrain.Draw(gg, Game.ScreenBase);
			gui.Cities.Draw(gg, Game.ScreenBase);
			gui.Units.Draw(gg, Game.ScreenBase);
		break;
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
			case 1: size=16; break; // 320 x 192
			case 2: size=24; break; // 480 x 288
			case 3: size=32; break; // 640 x 384
			case 4: size=48; break; // 960 x 
			case 5: size=64; break; // 1280 x 800
			default: size=32; break;
		}
		//Window
		ThisMadeMeWantToRageQuit.setSize(new Dimension(20*size+12,12*size+6));
		ThisMadeMeWantToRageQuit.setBounds(0,0,20*size+12,12*size+6);
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
