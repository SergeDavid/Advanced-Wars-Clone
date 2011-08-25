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
	
	//Image handling settings are as follows
	public int fps;
	public int fpscount;
	public static Image[] img_menu = new Image[5];
	public static Image img_tile;
	public static Image img_char;
	public static Image img_city;
	public static Boolean readytopaint;
	
	/**This handles the different players and also is used to speed logic arrays (contains a list of all characters they own)*/
	public static List<players.Base> player = new ArrayList<players.Base>();
	public static List<buildings.Base> builds = new ArrayList<buildings.Base>();
	public static List<units.Base> units = new ArrayList<units.Base>();
	
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
				if (Game.gui.frame>=12) {Game.gui.frame=0;}
			}
			else logics++;
			
			//Paints the scene then sleeps for a bit.
			gui.repaint();
			try { Thread.sleep(20);} catch (Exception e) {};
		}
	}
	
	//TODO: Might move these three to their own class, might not.
	public static void CreateCommander(int which,int team, int money, boolean npc) {
		switch (which) {
			case 0:Game.player.add(new players.Andy(npc,team,money));break;
			default:Game.player.add(new players.Base(npc,team,money));break;
		}
	}
	public static void CreateCity(int owner, int xx, int yy, int type) {//15 = Neutral, 12, 13, 14 are unused. (12 max players)
		int team = 0;
		if (owner>btl.totalplayers) {
			owner=15;
			if (type == 0) {type = 1;}
		}
		else {team = Game.player.get(owner).team;}
		switch (type) {
			case 0:Game.builds.add(new buildings.Capital(owner, team, xx, yy));break;
			case 1:Game.builds.add(new buildings.Town(owner, team, xx, yy));break;
			case 2:Game.builds.add(new buildings.Barracks(owner, team, xx, yy));break;
			case 3:Game.builds.add(new buildings.Shipyard(owner, team, xx, yy));break;
			case 4:Game.builds.add(new buildings.Airport(owner, team, xx, yy));break;
			default:Game.builds.add(new buildings.Town(owner, team, xx, yy));break;
		}
	}
	public static void CreateUnit(int type, int owner, int xx, int yy, boolean active) {
		switch (type) {
			case 0:Game.units.add(new units.Infantry(owner, xx, yy, active));break;
			case 1:Game.units.add(new units.Mechanic(owner, xx, yy, active));break;
			case 2:Game.units.add(new units.SmallTank(owner, xx, yy, active));break;
			default:Game.units.add(new units.Base(owner, xx, yy, active));break;
		}
		
	}
	//TODO: Find out how to display units, buildings, and the map before entering a battle / in a menu
		//Load all the units/building/Commanders into an array (Sort by some property they have [Order=1])
		//Compare unit cost/attack/defense with current commander's cost bonus and atk/def bonus
	//TODO: More battle settings
	//TODO: Fog of war
	//TODO: Force buildings owned by none existing players / teams to turn neutral.
	//TODO: Create a map editor
	//TODO: Reorganize sprite sheets to support animations on units
	//TODO: When units are on a building, they gain health at the beginning of their turn (if enough money)
	//TODO: End game setup, someone wins when someone else loses all their units (and can't make more), or loses their capital building.
	
	/**Starts a new game when launched.*/
	public static void main(String args[]) throws Exception {new Game();}
}
