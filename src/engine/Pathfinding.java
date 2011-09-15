package engine;

import java.awt.Point;
import java.util.Vector;

//Future place of the A-star path finding algorithm
public class Pathfinding {
	//The lists used for finding things.
	private Vector<PathNode> openlist = new Vector<PathNode>();
	public Vector<PathNode> closedlist = new Vector<PathNode>();
	
	//Used for tracking the currently selected node and a shortcut to the currently selected unit.
	private units.Base unit;
	private PathNode current;
	private int distance;
	
	//Output information
	private Vector<Point> map = new Vector<Point>();
	public int[][]maphits;
	
	//View Tile Cost/Hits and when the last time something was updated.
	public boolean ShowCost;
	public boolean ShowHits;
	public long LastChanged = 1;
	
	public Vector<Point> FindPath(units.Base unit, int range) {
		maphits = new int[Game.map.height][Game.map.width];
		openlist = new Vector<PathNode>();
		closedlist = new Vector<PathNode>();
		map = new Vector<Point>();
		
		distance = range;
		this.unit = unit;
		openlist.add(new PathNode(unit.x, unit.y, 0));
		current = openlist.get(0);

		while (true) {
			if (openlist.isEmpty()) {break;}
			FindNodes();
			
			closedlist.add(current);
			openlist.remove(current);
			
			maphits[current.loc.y][current.loc.x]++;
			
			current=LowestCostOpen();
		}
		for (PathNode node : closedlist) {
			map.add(new Point(node.loc.x,node.loc.y));
		}
		unit.LastPathed = System.currentTimeMillis();
		RemoveOccupied();
		return map;
	}
	
	private PathNode LowestCostOpen() {
		if (openlist.isEmpty()) {return null;}
		PathNode lowest = openlist.get(0);
		for (PathNode node : openlist) {
			if (node.cost>lowest.cost) {
				lowest = node;
			}
		}
		return lowest;
	}
	
	private void FindNodes() {
		if (current.loc.x-1>=0&&unit.PathCheck(current.loc.x-1, current.loc.y)) {
			AddNode(current.loc.x-1,current.loc.y);
		}
		if (current.loc.y-1>=0&&unit.PathCheck(current.loc.x, current.loc.y-1)) {
			AddNode(current.loc.x,current.loc.y-1);
		}
		if (current.loc.x+1<Game.map.width&&unit.PathCheck(current.loc.x+1, current.loc.y)) {
			AddNode(current.loc.x+1,current.loc.y);
		}
		if (current.loc.y+1<Game.map.height) {
			if (unit.PathCheck(current.loc.x, current.loc.y+1)) {
				AddNode(current.loc.x,current.loc.y+1);
			}
		}
	}
	private void AddNode(int x, int y) {
		if (!InClosed(x,y)) {//Ignores any node that is already in the closed list.
			if (!InOpen(x,y)) {//Switches parents if it is in the list, adds it if it isn't
				double cost = Math.round(FindCost(x,y,current)*100.0) / 100.0;
				if (cost<=distance) {
					openlist.add(new PathNode(x,y,cost));
				}
			}
		}
	}

	private boolean InOpen(int x, int y) {
		for (PathNode node : openlist) {
			if (node.loc.x == x && node.loc.y == y) {
				SwitchParent(node);
				return true;
			}
		}
		return false;
	}
	private boolean InClosed(int x, int y) {
		for (PathNode node : closedlist) {
			if (node.loc.x == x && node.loc.y == y) {
				SwitchParentClosed(node);
				return true;
			}
		}
		return false;
	}
	
	private void SwitchParent(PathNode node) {
		double cost = Game.map.map[node.loc.y][node.loc.x].speed() + current.cost;
		if (cost<node.cost) {
			node.cost = Math.round(cost*100.0) / 100.0;
		}
	}
	private void SwitchParentClosed(PathNode node) {
		double cost = Game.map.map[node.loc.y][node.loc.x].speed() + current.cost;
		if (cost<node.cost) {
			node.cost = Math.round(cost*100.0) / 100.0;
			closedlist.remove(node);
			openlist.add(node);
		}
	}
	
	private double FindCost(int x, int y, PathNode parent) {
		double cost = Game.map.map[y][x].speed();
		if (parent!=null) {
			cost+=parent.cost;
		}
		return Math.round(cost*100.0) / 100.0;
	}
	private void RemoveOccupied() {
		if (map.size()<=1) {return;}
		for (int i = 1; i<map.size(); i++) {
			if (Occupied(map.get(i).x,map.get(i).y)) {
				map.remove(i);
			}
		}
	}
	private boolean Occupied(int x, int y) {
		for (units.Base unit : Game.units) {
			if (unit.x == x && unit.y == y) {
				return true;
			}
		}
		return false;
	}

	public class PathNode {
		public double cost;
		public Point loc;
		
		PathNode(int x, int y, double cost) {
			loc = new Point(x,y);
			this.cost = cost;
		}
	}
}
