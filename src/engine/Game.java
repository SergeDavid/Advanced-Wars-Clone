package engine;

import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;


public class Game extends JFrame {
	private static final long serialVersionUID = 1L;
	
	//Application Settings
	private static final String build = "0";
	private static final String version = "0";
	public static final String name = "Strategy Game";
	public static final int width = 640+6;//Offset for borders.
	public static final int height = 400+12;
	public static final boolean dev = true;//Is this a dev copy or not... useless? D:
	
	//Game state
	public static int GameState;
	/**The game is loading something, so disable all user input except for closing the application.*/
	public static final int Loading = 0;
	/**Pause any in game logic as to not have something time sensitive happen when you die.*/
	public static final int TheMenu = 1;
	/**This enables things with controlling pieces and logic that happen during gameplay to work.*/
	public static final int Playing = 2;
	/**Which menu is to be displayed*/
	public static int GameMenu;
	public static final int MainMenu = 0;
	public static final int Options = 1;
	public static final int Credit = 2;
	public static final int Pause = 3;
		
	//Setup the quick access to all of the other class files.
	public static Map map;
	public static Gui gui;
	public static LoadImages load;
	public static InputHandler input;
	public static Battle btl;
	
	//Image handling settings are as follows
	/**The buffer strategy that holds then transfers the images from off screen to on screen so there are no flickering.*/
	static BufferStrategy strategy;
	public int fps;
	public int fpscount;
	public static Image[] img_menu = new Image[5];
	public static Image img_tile;
	public static Image img_char;
	public static Boolean readytopaint;
	
	/**This handles the different players and also is used to speed logic arrays (contains a list of all characters they own)*/
	public static List<players.Base> player = new ArrayList<players.Base>();
	public static List<Buildings> builds = new ArrayList<Buildings>();
	
	public Game() {super (name);
	
		//Default Settings of the JFrame
		setBounds(0,0,width,height);
		setSize(width,height);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setUndecorated(false);
		setResizable(false);
		setVisible(true);
				
		//Creates all the gui elements and sets them up
		gui = new Gui(this);
		gui.setFocusable(true);
		gui.requestFocusInWindow();
		
		//load images, initialize the map, and adds the input settings.
		StartupScreen();
		load = new LoadImages();
		map = new Map();
		input = new InputHandler();
		
		GameLoop();
	}

	private void StartupScreen() {
		// TODO Auto-generated method stub
	}

	private void GameLoop() {
		boolean loop=true;
		long last = System.nanoTime();
		long lastCPSTime = 0;
		long lastCPSTime2 = 0;
		@SuppressWarnings("unused")
		int logics = 0;
		logics++;
		while (loop) {
			//Used for logic stuff
			@SuppressWarnings("unused")
			long delta = (System.nanoTime() - last) / 1000000;
			delta++;
			last = System.nanoTime();
			
			//FPS settings
			if (System.currentTimeMillis() - lastCPSTime > 1000) {
				lastCPSTime = System.currentTimeMillis();
				fpscount = fps;
				fps = 0;
				setTitle(name + " v" + build + "." + version + " : FPS " + fpscount);
			}
			else fps++;
			//Current Logic location
			if (System.currentTimeMillis() - lastCPSTime2 > 100) {
				lastCPSTime2 = System.currentTimeMillis();
				logics = 0;
			}
			else logics++;
			
			//Paints the scene then sleeps for a bit.
			gui.repaint();
			try { Thread.sleep(30);} catch (Exception e) {};
		}
	}
	
	public static void MenuButton() {
		if (GameState==Playing) {
		
		}
	}
	//TODO: Create in game menu's
	//TODO: Combine units into a single list for less looping
	//TODO: Re-do the building code for use with multiple building classes
	//TODO: Selecting a working building without a unit on top brings up it's menu.
	//TODO: Rework the map so that it works better
	//TODO: Create a map editor
	//TODO: Move the maps folder outside of the to be constructed jar area
	//TODO: Actually make some sprites to use.
	//TODO: Reorganize sprite sheets to support animations on units
	//TODO: Add more tiles
	//TODO: Add in capital, factories, and other buildings
	//TODO: Include buildings offering units health
	//TODO: End game setup, someone wins when someone else loses all their units, or loses their capital building.
	//TODO: Add in more TODO lists.
	
	/**Starts a new game when launched.*/
	public static void main(String args[]) throws Exception {new Game();}
}
