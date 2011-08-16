package engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Map {
	//Base settings
	public int width = 12;//Base = 16
	public int height = 12;
	public final int minsize = 6;
	public final int maxsize = 64;
	public String auther;
	
	/**A square/rectangular area that you play on. Diamond shaped if isometric.*/
	public int[][]map;
	public List<terrain.Base> tiles = new ArrayList<terrain.Base>();
	public MapFinder finder = new MapFinder();
	public MapParser parse = new MapParser();
	public List<String> MapNames;
	
	public Map() {
		LoadTiles();
		map = new int[height][width];
		MapNames = finder.GrabMaps();
	}
	
	public void MapSetup(int width, int height) {
		//Scale integrity
		if (width<minsize) {width=minsize;}if (width>maxsize) {width=maxsize;}
		if (height<minsize) {height=minsize;}if (height>maxsize) {height=maxsize;}
		this.width=width;
		this.height=height;
		map = new int[height][width];
	}
	public void Randomize() {
		Random rand = new Random();
		for (int y=0; y<height; y++) {
			for (int x=0; x<width; x++) {
				if (rand.nextInt(6)==0) {map[y][x]=1;}
				else if (rand.nextInt(10)==0) {map[y][x]=2;}
				else if (rand.nextInt(16)==0) {map[y][x]=3;}
			}
		}
	}
	
	public boolean Walkable(int i) {if (tiles.get(i).walk()) {return true;}return false;}
	public boolean Swimable(int i) {if (tiles.get(i).swim()) {return true;}return false;}
	public boolean FlyAble(int i) {if (tiles.get(i).fly()) {return true;}return false;}
	
	/**Loads all of the tiles that are used in the map into an array list.*/
	private void LoadTiles() {
		tiles.add(new terrain.Dirt());
		tiles.add(new terrain.Grass());
		tiles.add(new terrain.Road());
		tiles.add(new terrain.Water());
		tiles.add(new terrain.Water());
	}
}
