package engine;

import java.util.ArrayList;
import java.util.List;

public class Map {
	//Base settings
	public int width = 12;
	public int height = 12;
	public final int minsize = 6;
	public final int maxsize = 64;
	public String auther;
	public String desc;
	
	/**A square/rectangular area that you play on. Diamond shaped if isometric.*/
	public terrain.Base[][] map;
	public List<terrain.Base> tiles = new ArrayList<terrain.Base>();
	public MapParser parse = new MapParser();
	
	public Map() {
		LoadTiles();
		map = new terrain.Base[height][width];
	}
	
	public void MapSetup(int width, int height) {
		//Scale integrity
		if (width<minsize) {width=minsize;}if (width>maxsize) {width=maxsize;}
		if (height<minsize) {height=minsize;}if (height>maxsize) {height=maxsize;}
		this.width=width;
		this.height=height;
		map = new terrain.Base[height][width];
		//TODO: Current way of keeping the map clean, I should find a way of doing this with a smaller startup cost.
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				map[y][x] = new terrain.Dirt();
			}
		}
	}
	
	public boolean Walkable(int i) {if (tiles.get(i).walk()) {return true;}return false;}
	public boolean Driveable(int i) {if (tiles.get(i).drive()) {return true;}return false;}
	public boolean Swimable(int i) {if (tiles.get(i).swim()) {return true;}return false;}
	public boolean Flyable(int i) {if (tiles.get(i).fly()) {return true;}return false;}
	
	/**Loads all of the tiles that are used in the map into an array list.*/
	private void LoadTiles() {
		tiles.add(new terrain.Dirt());
		tiles.add(new terrain.Forest());
		tiles.add(new terrain.Mountain());
		tiles.add(new terrain.Water());
		tiles.add(new terrain.City());
		tiles.add(new terrain.Forest());
		//tiles.add(new terrain.Road());
	}
}
