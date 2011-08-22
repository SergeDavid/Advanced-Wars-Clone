package engine;

import java.util.ArrayList;
import java.util.List;

public class Map {
	//Base settings
	public int width = 12;//Base = 16
	public int height = 12;
	public final int minsize = 6;
	public final int maxsize = 64;
	public String auther;
	
	/**A square/rectangular area that you play on. Diamond shaped if isometric.*/
	public terrain.Base[][] map;
	public List<terrain.Base> tiles = new ArrayList<terrain.Base>();
	public MapFinder finder = new MapFinder();
	public MapParser parse = new MapParser();
	public List<String> MapNames;
	
	public Map() {
		LoadTiles();
		map = new terrain.Base[height][width];
		MapNames = finder.GrabMaps();
	}
	
	public void MapSetup(int width, int height) {
		//Scale integrity
		if (width<minsize) {width=minsize;}if (width>maxsize) {width=maxsize;}
		if (height<minsize) {height=minsize;}if (height>maxsize) {height=maxsize;}
		this.width=width;
		this.height=height;
		map = new terrain.Base[height][width];
	}
	
	public boolean Walkable(int i) {if (tiles.get(i).walk()) {return true;}return false;}
	public boolean Driveable(int i) {if (tiles.get(i).drive()) {return true;}return false;}
	public boolean Swimable(int i) {if (tiles.get(i).swim()) {return true;}return false;}
	public boolean Flyable(int i) {if (tiles.get(i).fly()) {return true;}return false;}
	
	/**Loads all of the tiles that are used in the map into an array list.*/
	private void LoadTiles() {
		//TODO: Maybe change this into a setter where you send it an int, and it returns the object and remove the arraylist all together.
		tiles.add(new terrain.Dirt());
		tiles.add(new terrain.Grass());
		tiles.add(new terrain.Mountain());
		tiles.add(new terrain.Water());
		tiles.add(new terrain.City());
		tiles.add(new terrain.Forest());
	}
}
