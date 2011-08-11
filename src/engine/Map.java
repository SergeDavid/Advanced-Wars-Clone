package engine;

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
	Tiles tile = new Tiles();
	public MapFinder finder = new MapFinder();
	public MapParser parse = new MapParser();
	public List<String> MapNames;
	
	public Map() {
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
	
	public boolean Walkable(int i) {if (tile.t.get(i).walk()) {return true;}return false;}
	public boolean Swimable(int i) {if (tile.t.get(i).swim()) {return true;}return false;}
	public boolean FlyAble(int i) {if (tile.t.get(i).fly()) {return true;}return false;}
	
}
