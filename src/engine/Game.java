package engine;

import java.awt.Image;
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
	/**The built in map editor.*/
	public static final int Editor = 3;
		
	//Setup the quick access to all of the other class files.
	public static Map map;
	public static Gui gui;
	public static LoadImages load;
	public static InputHandler input;
	public static Battle btl = new Battle();
	public static ErrorHandler error = new ErrorHandler();
	public static Pathfinding pathing = new Pathfinding();
	public static ListData list;
	public static ViewPoint view = new ViewPoint();
	
	//Image handling settings are as follows
	public int fps;
	public int fpscount;
	public static Image[] img_menu = new Image[5];
	public static Image img_tile;
	public static Image img_char;
	public static Image img_city;
	public static Boolean readytopaint;
	
	//This handles the different players and also is used to speed logic arrays (contains a list of all characters they own)
	public static List<players.Base> player = new ArrayList<players.Base>();
	public static List<buildings.Base> builds = new ArrayList<buildings.Base>();
	public static List<units.Base> units = new ArrayList<units.Base>();
	//These are the lists that will hold commander, building, and unit data to use in the menu's
	public static List<players.Base> displayC = new ArrayList<players.Base>();
	public static List<buildings.Base> displayB = new ArrayList<buildings.Base>();
	public static List<units.Base> displayU = new ArrayList<units.Base>();
	
	public Game() {super (name);
		//Default Settings of the JFrame
		setBounds(0,0,width,height);
		setSize(width,height);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setUndecorated(false);
		setResizable(false);
				
		//Creates all the gui elements and sets them up
		gui = new Gui(this);
		gui.setFocusable(true);
		gui.requestFocusInWindow();
		
		//load images, initialize the map, and adds the input settings.
		load = new LoadImages();
		map = new Map();
		input = new InputHandler();
		list = new ListData();
		
		setVisible(true);//This has been moved down here so that when everything is done, it is shown.
		GameLoop();
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
			if (System.currentTimeMillis() - lastCPSTime > 1000) {//20 Sleep = 50 FPS 30 Sleep = 33 FPS
				lastCPSTime = System.currentTimeMillis();
				fpscount = fps;
				fps = 0;
				error.ErrorTicker();
				setTitle(name + " v" + build + "." + version + " : FPS " + fpscount);
			}
			else fps++;
			//Current Logic location
			if (System.currentTimeMillis() - lastCPSTime2 > 100) {
				lastCPSTime2 = System.currentTimeMillis();
				logics = 0;
				Game.gui.frame++;
				if (GameState==Playing) {view.MoveView();}
				if (Game.gui.frame>=12) {Game.gui.frame=0;}
			}
			else logics++;
			
			//Paints the scene then sleeps for a bit.
			gui.repaint();
			try { Thread.sleep(20);} catch (Exception e) {};
		}
	}
	
	//TODO: Find out how to display units, buildings, and the map before entering a battle / in a menu
		//Load all the units/building/Commanders into an array (Sort by some property they have [Order=1])
		//Compare unit cost/attack/defense with current commander's cost bonus and atk/def bonus
	//TODO: More battle settings
	//TODO: Fog of war
	//TODO: Create a map editor
	//TODO: Reorganize sprite sheets to support animations on units
	//TODO: When units are on a building, they gain health at the beginning of their turn (if enough money)
	//TODO: End game setup, someone wins when someone else loses all their units (and can't make more), or loses their capital building.
	
	/**Starts a new game when launched.*/
	public static void main(String args[]) throws Exception {new Game();}
}
