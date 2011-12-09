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
	
	public void SwitchTiles() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				//TODO: Change the ChangeTiles to support boolean OpenCorners, ClosedCorners, and actual x/y locations of said stuff.
				if (map[y][x].MultiTiled) {
					ChangeTiles(map[y][x],x,y);
				}
			}
		}
	}
	private void ChangeTiles(terrain.Base type, int x, int y){
		boolean T1 = FindCommon(type.name,x-1,y-1);
		boolean T2 = FindCommon(type.name,x,y-1);
		boolean T3 = FindCommon(type.name,x+1,y-1);
		boolean M1 = FindCommon(type.name,x-1,y);
		boolean M2 = FindCommon(type.name,x+1,y);
		boolean B1 = FindCommon(type.name,x-1,y+1);
		boolean B2 = FindCommon(type.name,x,y+1);
		boolean B3 = FindCommon(type.name,x+1,y+1);
		type.x = type.oldx;
		type.y = type.oldy;
		if (!T2&&!M1&&!M2&&!B2) {//None
			type.x+=4;
			type.y-=1;
		}
		//Lines
		else if (T2&&!M1&&!M2&&B2) {type.x+=3;type.y-=1;}// |
		else if (!T2&&M1&&M2&&!B2) {type.x+=2;type.y-=1;}// -
		//Open Corners
		else if (T1&&T2&&T3&&M1&&M2&&B1&&B2&&B3) {//All
			//Nothing Happens
		}
		else if (!T2&&M1&&M2&&B2) {type.y-=1;}//"|"
		else if (T2&&M1&&M2&&!B2) {type.y+=1;}//_|_
		else if (T2&&!M1&&M2&&B2) {type.x-=1;}// |-
		else if (T2&&M1&&!M2&&B2) {type.x+=1;}// -|
		//Corners
		else if (!T2&&!M1&&M2&&B2&&B3) {type.x-=1;type.y-=1;}// |"
		else if (!T2&&M1&&!M2&&B1&&B2) {type.x+=1;type.y-=1;}// "|
		else if (T2&&T3&&!M1&&M2&&!B2) {type.x-=1;type.y+=1;}// |_
		else if (T1&&T2&&M1&&!M2&&!B2) {type.x+=1;type.y+=1;}// _|
		//Closed Corners
		else if (!T2&&!M1&&M2&&B2) {type.x+=2;}// |"
		else if (!T2&&M1&&!M2&&B2) {type.x+=3;}// "|
		else if (T2&&!M1&&M2&&!B2) {type.x+=2;type.y+=1;}// |_
		else if (T2&&M1&&!M2&&!B2) {type.x+=3;type.y+=1;}// _|
		//Ends
		else if (!T2&&!M1&&!M2&&B2) {type.x+=5;}// V
		else if (!T2&&M1&&!M2&&!B2) {type.x+=4;type.y+=1;}// >
		else if (!T2&&!M1&&M2&&!B2) {type.x+=5;type.y+=1;}// <
		else if (T2&&!M1&&!M2&&!B2) {type.x+=4;}// ^
		//else {System.out.println(x + " and " + y + "Uppies!" + T1 + T2 + T3 + M1 + M2 + B1 + B2 + B3);}
	}
	private boolean FindCommon(String type, int x, int y) {
		if (x < 0 || y < 0) {return true;}
		if (x >= width || y >= height) {return true;}
		if (type.equals(map[y][x].name)) {return true;}
		return false;
	}

	/**Loads all of the tiles that are used in the map into an array list.*/
	private void LoadTiles() {
		for (int i=0; i<100 ;i++) {
			tiles.add(getTile(i));
			if (tiles.get(i)==null) {
				tiles.remove(i);
				break;
			}
		}
	}

	public terrain.Base getTile(int i) {
		switch(i) {
		case 0:return new terrain.Dirt();
		case 1:return new terrain.Forest();
		case 2:return new terrain.Mountain();
		case 3:return new terrain.Water();
		case 4:return new terrain.City();
		case 5:return new terrain.Road();
		default:return null;
		}
	}

	public void ResizeMap(int neww, int newh, int oldw, int oldh) {
		System.out.println("Okay it worked?");
		width = neww;
		height = newh;
		terrain.Base[][] newmap = new terrain.Base[height][width];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {//TODO: Fix this as it seems to not be exactly correct.
				if (oldh < y || oldw < x) {
					newmap[y][x] = map[y][x];
				}
				else {
					newmap[y][x] = new terrain.Dirt();
				}
			}
		}
		map = newmap;
	}
}
