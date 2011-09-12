package engine;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;


public class Game extends JFrame {
	private static final long serialVersionUID = 1L;
	
	//Application Settings
	private static final String build = "0";
	private static final String version = "1";
	public static final String name = "Strategy Game";
	public static int ScreenBase = 32;//Bit size for the screen, 16 / 32 / 64 / 128
	public static boolean dev = true;//Is this a dev copy or not... useless? D:
	
	public static enum State {STARTUP, MENU, PLAYING, EDITOR};
	public static State GameState = State.STARTUP;
		
	//Setup the quick access to all of the other class files.
	public static Map map;
	public static Gui gui;
	public static GameMenus gms;
	public static LoadImages load;
	public static InputHandler input;
	public static Battle btl = new Battle();
	public static ErrorHandler error = new ErrorHandler();
	public static Pathfinding pathing = new Pathfinding();
	public static ListData list;
	public static Save save = new Save();
	public static ComputerBrain brain = new ComputerBrain();
	public static FileFinder finder = new FileFinder();
	public static ViewPoint view = new ViewPoint();
	
	//Image handling settings are as follows
	public int fps;
	public int fpscount;
	public static Image[] img_menu = new Image[5];
	public static Image img_tile;
	public static Image img_char;
	public static Image img_city;
	public static Image img_exts;
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
		setBounds(0,0,20*ScreenBase+6,12*ScreenBase+12);
		setSize(20*ScreenBase+6,12*ScreenBase+12);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setUndecorated(false);
		setResizable(false);
	    setLocationRelativeTo(null);
				
		//Creates all the gui elements and sets them up
		gui = new Gui(this);
		add(gui);
		gui.setFocusable(true);
		gui.requestFocusInWindow();
		
		//load images, initialize the map, and adds the input settings.
		gms = new GameMenus();
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
			if (System.currentTimeMillis() - lastCPSTime > 1000) {
				lastCPSTime = System.currentTimeMillis();
				fpscount = fps;
				fps = 0;
				error.ErrorTicker();
				setTitle(name + " v" + build + "." + version + " : FPS " + fpscount);
				if (GameState == State.PLAYING) {
					if (player.get(btl.currentplayer).npc&&btl.totalplayers>1) {
						brain.ThinkDamnYou(player.get(btl.currentplayer));
					}
				}
			}
			else fps++;
			//Current Logic and frames per second location (capped at 20 I guess?)
			if (System.currentTimeMillis() - lastCPSTime2 > 100) {
				lastCPSTime2 = System.currentTimeMillis();
				logics = 0;
				if (GameState==State.PLAYING) {view.MoveView();}//This controls the view-point on the map
				Game.gui.frame++;//This is controlling the current frame of animation.
				if (Game.gui.frame>=12) {Game.gui.frame=0;}
				gui.repaint();
			}
			else logics++;
			
			//Paints the scene then sleeps for a bit.
			try { Thread.sleep(30);} catch (Exception e) {};
		}
	}
	
	//TODO: More battle settings
	//TODO: Fog of war
	//TODO: Create a map editor
	//TODO: Reorganize sprite sheets to support animations on units
	//TODO: When units are on a building, they gain health at the beginning of their turn (if enough money)
	//TODO: End game setup, someone wins when someone else loses all their units (and can't make more), or loses their capital building.
	//TODO: Support 16x and 8x texture packs.
	//TODO: Resize font to match screen resizes
	//TODO: Resize menu's to support screen resizes
	
	/**Starts a new game when launched.*/
	public static void main(String args[]) throws Exception {new Game();}
}
