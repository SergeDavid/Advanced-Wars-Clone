package engine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MapParser {
	int terrain = 0;//Keeps track of current row
	boolean Info;//Was the info collected completely?
	boolean Desc;//Was the description collected completely?
	boolean Terr;//Was all the terrain there?
	boolean Blds;//Was there any buildings? (at least capitals)
	public void encode(String mapname) {
		//TODO: Creates a file if it does not exist (opens a prompt if file exists)
		//TODO: Add in all of the elements into the file from info,description,terrain,buildings, and units.
		//TODO: Send any error messages to be handled by the error messaging system.
	} 
	public boolean decode(String mapname) {
		//TODO: Better setup of them being true or not.
		Info=Desc=Terr=Blds=false;//Sets all of them to false
		terrain = 0;
		try {
			BufferedReader in = new BufferedReader(new FileReader("maps/"+mapname));
			String line;
			while ((line = in.readLine()) != null) {
				if (!line.startsWith("#")) {
					if (line.startsWith("1")) {
						ParseInfo(line.substring(2));Info=true;Blds=true;
					}
					if (line.startsWith("2")) {
						//TODO: Use this for the map selection menu
						Desc=true;
					}
					if (line.startsWith("3")) {
						ParseTerrain(line.substring(2));Terr=true;
					}
					if (line.startsWith("4")) {
						ParseBuilding(line.substring(2));
					}
					if (line.startsWith("5")) {
						ParseUnit(line.substring(2));
					}
				}
			}
			if (Info&&Blds) {return true;}
			else {
				System.out.println("Return to login...");
				return false;
			}
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
	}
	/**This is for decoding the terrain, it currently goes with 1 line = 1 row
	 * 2 = Byte
	 * x = Terrain (to be split into xx, maybe xx:x)*/
	private void ParseTerrain(String info) {
		if (info.length()>Game.map.width||info.length()<Game.map.width) {
			Game.error.ShowError("Terrain at row " + terrain + " is corrupt.");
		}
		if (terrain>=Game.map.height) {return;}
		//TODO: Split the terrain info into 2 bytes (ff) instead of (f)
		int total = info.length();
		for (int i=0;i<total&&i<Game.map.width;i++) {
			String using = info.substring(i,i+1);
			Game.map.map[terrain][i]=Game.map.tiles.get(Integer.parseInt(using,16));
		}
		terrain++;
		//TODO: Check out the idea of setting buildings so each line of building data is owner, xx,yy,type*# so it is shorter. D:
		//Or try owner,type*# in a row and every time a building is found in the map, it creates one from the list. (smaller file)
		//TODO: See about making the map an array of objects, and 
	}
	/**Creates a building by owner at x,y and type
	 * f = Owner (0-11 are players, 15 is neutral, 12-14 are unused)
	 * ff = x location
	 * ff = y location
	 * ff = type*/
	private void ParseBuilding(String info) {
		Game.CreateCity(
				Integer.parseInt(info.substring(0,1),16),
				Integer.parseInt(info.substring(1,3),16),
				Integer.parseInt(info.substring(3,5),16),
				Integer.parseInt(info.substring(5,7),16));
	}
	private void ParseUnit(String info) {
		Game.CreateUnit(
				Integer.parseInt(info.substring(5,7),16), 
				Integer.parseInt(info.substring(0,1),16), 
				Integer.parseInt(info.substring(1,3),16), 
				Integer.parseInt(info.substring(3,5),16), 
				true);
	}
}
