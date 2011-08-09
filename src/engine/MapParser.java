package engine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MapParser {
	int terrain = 0;//Keeps track of current row
	public void Units() {
		
	}
	public void Terrain() {
		
	}
	public void Building() {
		
	}
	
	public void decode(String mapname) {
		try {
			BufferedReader in = new BufferedReader(new FileReader("maps/"+mapname+".txt"));
			String line;
			while ((line = in.readLine()) != null) {
				if (!line.startsWith("#")) {
					if (line.startsWith("1")) {
						ParseInfo(line.substring(2));
					}
					if (line.startsWith("2")) {
						System.out.println("Description");
					}
					if (line.startsWith("3")) {
						ParseTerrain(line.substring(2));
					}
					if (line.startsWith("4")) {
						ParseBuilding(line.substring(2));
					}
					if (line.startsWith("5")) {
						ParseUnit(line.substring(2));
					}
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Map not found.");
			//TODO: Report map not found.
			return;
		} catch (IOException e) {
			System.out.println("Read Line Error in map generation.");
			return;
		}
	}
	/**This is for parsing map details
	 * 1 = Byte
	 * xx = Map Width
	 * yy = Map Height
	 * x = Total players*/
	private void ParseInfo(String info) {
		Game.map.MapSetup(Integer.parseInt(info.substring(0,2),16)+1,Integer.parseInt(info.substring(2,4),16)+1);
	}
	/**This is for decoding the terrain, it currently goes with 1 line = 1 row
	 * 2 = Byte
	 * x = Terrain (to be split into xx, maybe xx:x)*/
	private void ParseTerrain(String info) {
		if (terrain>=Game.map.height) {return;}
		//TODO: Split the terrain info into 2 bytes (ff) instead of (f)
		int total = info.length();
		for (int i=0;i<total&&i<Game.map.width;i++) {
			String using = info.substring(i,i+1);
			Game.map.map[terrain][i]=Integer.parseInt(using,16);
		}
		terrain++;
	}
	private void ParseBuilding(String info) {
		Game.builds.add(
				new Buildings(
						Integer.parseInt(info.substring(0,1),16),
						Integer.parseInt(info.substring(1,3),16), 
						Integer.parseInt(info.substring(3,5),16)));
	}
	private void ParseUnit(String info) {
		System.out.println(Integer.parseInt("ff",16));
		Game.player.get(Integer.parseInt(info.substring(0,1),16)).units.add(
				new Character(
						Integer.parseInt(info.substring(0,1),16),
						Integer.parseInt(info.substring(1,3),16), 
						Integer.parseInt(info.substring(3,5),16)));
	}
}
