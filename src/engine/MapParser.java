package engine;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**encodes and decodes mapfiles. 
 * The encoder is used solely by the map editor, (save is used to save battles)
 * while the decode is used to load a map for the editor and battles via battle.newgame()
 * @author SergeDavid
 * @version 0.2
 */
public class MapParser {
	int terrain = 0;//Keeps track of current row
	
	//Handles building construction after the decoding loop is finished.
	String CityString;
	Vector<Point> CityPoint;
	String path = "maps";
	
	public void encode(String mapname) {
		//If the folder doesn't exist, create it so we can save our save files inside it.
		File directory = new File(path);
		if (!directory.exists()) {
			if (directory.mkdir()) {Game.error.ShowError("The " + path + " directory has been created.");} 
	    	else {Game.error.ShowError("Cannot create the maps directory. " + path + "");return;}
		}
		//Saves the game in a property file (since it is easy)
		try {
			FileWriter fstream = new FileWriter(path + "/" + mapname + ".txt");
		    BufferedWriter out = new BufferedWriter(fstream);
		    out.write(encodeInfo() + encodeDesc() + encodeCities() + encodeTerrain() + encodeUnits());
		    out.close();
		} 
		catch (Exception e) {Game.error.ShowError("The map failed to save:" + e.getMessage());}
	}
	private String encodeInfo() {//TODO: Total players. (start with 2, and add more depending on unit and building owners.)
		String w = Integer.toHexString(Game.map.width-1);if (w.length()<=1) {w = "0" + w;}
		String h = Integer.toHexString(Game.map.height-1);if (h.length()<=1) {h = "0" + h;}
		return "1 " + w + h + "2";
	}
	/**Adds the current player's name with a description of the map to the map file separated by the first space*/
	private String encodeDesc() {
		//TODO: Replace name with current players name, and leave description for manliness?
		return "\n2 " + "PlayerName" + " " + "Description of Map";
	}
	/**Turns the building list into a string. (and sorts them into proper order)*/
	private String encodeCities() {
		String cities = "";
		Game.builds = FormatCities(Game.builds);
		if (!Game.builds.isEmpty()) {
			cities+="\n3 ";
			for (buildings.Base bld : Game.builds) {
				cities+=Integer.toHexString(bld.owner);
				cities+=CityID(bld);
			}
		}
		return cities;
	}
	/**Turns the map into a series of strings.*/
	private String encodeTerrain() {
		String tiles = "";
		for (int y = 0; y < Game.map.height; y++) {
			tiles+="\n4 ";
			for (int x = 0; x < Game.map.width; x++) {
				tiles+=TerrainID(x,y);
			}
		}
		return tiles;
	}
	/**Turns the unit list into a string.*/
	private String encodeUnits() {
		String units = "";
		if (!Game.units.isEmpty()) {
			units+="\n5 ";
			for (units.Base unit : Game.units) {
				units+=UnitID(unit);
				units+=Integer.toHexString(unit.owner);
				String x = Integer.toHexString(unit.x);if (x.length()<=1) {x = "0" + x;}
				String y = Integer.toHexString(unit.y);if (y.length()<=1) {y = "0" + y;}
				units+=x;
				units+=y;
			}
		}
		return units;
	}
	
	private String CityID(buildings.Base bld) {
		String a = "00";
		for (int i = 0; i < Game.displayB.size(); i++) {
			if (bld.name.equals( Game.displayB.get(i).name)) {a = Integer.toHexString(i);break;}
		}
		if (a.length()<=1) {a = "0"+a;}
		return a;
	}
	private String UnitID(units.Base unit) {
		String a = "00";
		for (int i = 0; i < Game.displayU.size(); i++) {
			if (unit.name.equals(Game.displayU.get(i).name)) {a = Integer.toHexString(i);break;}
		}
		if (a.length()<=1) {a = "0"+a;}
		return a;
	}
	private String TerrainID(int x, int y) {
		for (int i = 0; i < Game.map.tiles.size(); i++) {
			if (Game.map.map[y][x].name.equals(Game.map.tiles.get(i).name)) {
				return Integer.toHexString(i);
			}
		}
		return "0";
	}
	private List<buildings.Base> FormatCities(List<buildings.Base> oldblds) {
		List<buildings.Base> newblds = new ArrayList<buildings.Base>();
		while (!oldblds.isEmpty()) {
			int lowest = 0;
			int lowid = oldblds.get(0).editorid;
			for (int i = 0; i < oldblds.size(); i++) {
				if (oldblds.get(i).editorid < lowid) {
					lowest = i;
					lowid = oldblds.get(i).editorid;
				}
			}
			newblds.add(oldblds.get(lowest));
			oldblds.remove(lowest);
		}
		System.out.println("Begin Debugging!!!");
		for (int i = 0; i < newblds.size() ; i++) {
			System.out.println("id : " + i + " with an editorid : " + newblds.get(i).editorid);
		}
		return newblds;
	}
	
	public boolean decode(String mapname) {
		terrain = 0;
		CityString = "";
		CityPoint = new Vector<Point>();
		try {
			BufferedReader in = new BufferedReader(new FileReader(path + "/" + mapname + ".txt"));
			String line;
			while ((line = in.readLine()) != null) {
				if (line.startsWith("1")) {
					ParseInfo(line.substring(2));
				}
				else if (line.startsWith("2")) {//Splits the creators name / map description from the first included space.
					ParseDesc(line.substring(2).split(" ",2));
				}
				else if (line.startsWith("3")) {//Adds line to the string handling build data to be used in the next loop.
					CityString += line.substring(2);
				}
				else if (line.startsWith("4")) {
					ParseTerrain(line.substring(2));
				}
				else if (line.startsWith("5")) {
					ParseUnit(line.substring(2));
				}
			}
			if (terrain<Game.map.height) {
				Game.error.ShowError("Terrain is corrupt, short " + (Game.map.height-terrain) + " rows.");
			}
			for (Point p : CityPoint) {
				if (CityString.length()>=3) {
					ParseBuilding(CityString.substring(0,3), p.x, p.y);
					CityString = CityString.substring(3);
				}
			}
			Game.map.SwitchTiles();//Tile test for switching water tiles.
			return true;
		} catch (FileNotFoundException e) {
			Game.error.ShowError("Map not found.");
			return false;
		} catch (IOException e) {
			Game.error.ShowError("Read Line error in Map Generation.");
			return false;
		}
	}
	/**This is for parsing map details
	 * 1 = Byte
	 * xx = Map Width
	 * yy = Map Height
	 * x = Total players*/
	private void ParseInfo(String info) {
		if (info.length()>5||info.length()<5) {
			Game.error.ShowError("Map info is corrupt.");
			return;
		}
		Game.map.MapSetup(Integer.parseInt(info.substring(0,2),16)+1,Integer.parseInt(info.substring(2,4),16)+1);
		Game.btl.MaxUsers(Integer.parseInt(info.substring(4,5),16));
	}
	private void ParseDesc(String[] info) {
		Game.map.auther = info[0];
		if (info.length>1) {
			Game.map.desc = info[1];
		}
	}
	/**This is for decoding the terrain, it currently goes with 1 line = 1 row
	 * 2 = Byte
	 * x = Terrain (to be split into xx, maybe xx:x)*/
	private void ParseTerrain(String info) {
		if (info.length()>Game.map.width) {
			Game.error.ShowError("Terrain at row " + terrain + " is corrupt. (Too Long)");
		}
		else if (info.length()<Game.map.width) {
			Game.error.ShowError("Terrain at row " + terrain + " is corrupt. (Too Short)");
		}
		if (terrain>=Game.map.height) {return;}
		int total = info.length();
		for (int i=0;i<total&&i<Game.map.width;i++) {
			String using = info.substring(i,i+1);
			Game.map.map[terrain][i]=Game.map.getTile(Integer.parseInt(using,16));
			if (Game.map.map[terrain][i].building()) {CityPoint.add(new Point(i, terrain));}
		}
		terrain++;
	}
	/**Creates a building by owner at x/y and type
	 * f = Owner (0-11 are players, 15 is neutral, 12-14 are unused)
	 * ff = type
	 * x location (x)
	 * y location (y)*/
	private void ParseBuilding(String info, int x, int y) {
		Game.builds.add(Game.list.CreateCity(
				Integer.parseInt(info.substring(0,1),16),
				x, y,
				Integer.parseInt(info.substring(1,3),16)));
	}
	/**(xx) type
	 * (x) owner
	 * (xx) x
	 * (xx) y
	 */
	private void ParseUnit(String info) {
		if (info.length()<7) {return;}
		Game.units.add(Game.list.CreateUnit(
				Integer.parseInt(info.substring(5,7),16), 
				Integer.parseInt(info.substring(0,1),16), 
				Integer.parseInt(info.substring(1,3),16), 
				Integer.parseInt(info.substring(3,5),16), 
				true));
	}
}
