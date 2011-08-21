package engine;

import java.awt.Point;
import java.util.Vector;

//Future place of the A-star path finding algorithm
public class Pathfinding {
	private Vector<PathNode> openlist = new Vector<PathNode>();
	Vector<PathNode> closedlist = new Vector<PathNode>();
	units.Base unit;
	PathNode current;
	public int[][] map;
	public boolean finished;
	
	public Pathfinding() {
		//TODO: Change it so units call FindPath when they are going to move.
		//TODO: Do a list on completion and use that for validating valid locations to move as well as drawing the movable locations
		//TODO: Add in some settings so that any time a unit moves (actually changes locations) / dies / created the unit's path has to be constructed again.
		//I don't want to construct an exact same thing twice in a row if I can't help it.
	}
	
	public void FindPath(units.Base unit) {
		finished=false;
		map = new int[Game.map.width][Game.map.height];
		openlist = new Vector<PathNode>();
		closedlist = new Vector<PathNode>();
		this.unit = unit;
		openlist.add(new PathNode(unit.x, unit.y, 0));
		current = openlist.get(0);
		for (int i = 0; i<100; i++) { // Max number of passes currently is 100.
			if (openlist.isEmpty()) {
				break;
			}
			current=LowestCostOpen();
			map[current.loc.x][current.loc.y]++;
			FindNodes();
			closedlist.add(current);
			openlist.remove(current);
			if (i>=99) {
				System.out.println("We reached 100!!!");
			}
			
		}
		finished=true;
		
	}
	
	private PathNode LowestCostOpen() {
		PathNode lowest = openlist.get(0);
		for (PathNode node : openlist) {
			if (node.cost>lowest.cost) {
				lowest = node;
			}
		}
		return lowest;
	}
	
	private void FindNodes() {
		if (current.loc.x-1>=0&&unit.moveable2(current.loc.x-1, current.loc.y)) {
			AddNode(current.loc.x-1,current.loc.y);
		}
		if (current.loc.y-1>=0&&unit.moveable2(current.loc.x, current.loc.y-1)) {
			AddNode(current.loc.x,current.loc.y-1);
		}
		if (current.loc.x+1<Game.map.width&&unit.moveable2(current.loc.x+1, current.loc.y)) {
			AddNode(current.loc.x+1,current.loc.y);
		}
		if (current.loc.y+1<Game.map.height) {
			if (unit.moveable2(current.loc.x, current.loc.y+1)) {
				AddNode(current.loc.x,current.loc.y+1);
			}
		}
	}
	
	private void AddNode(int x, int y) {
		if (!InClosed(x,y)) {//Ignores any node that is already in the closed list.
			if (!InOpen(x,y)) {//Switches parents if it is in the list, adds it if it isn't
				double cost = Math.round(FindCost(x,y,current)*100.0) / 100.0;
				if (cost<=unit.speed) {
					openlist.add(new PathNode(x,y,cost));
				}
			}
		}
	}

	private boolean InOpen(int x, int y) {
		for (PathNode node : openlist) {
			if (node.loc.x == x && node.loc.y == y) {
				SwitchParent(node,false);
				return true;
			}
		}
		return false;
	}

	private boolean InClosed(int x, int y) {
		for (PathNode node : closedlist) {
			if (node.loc.x == x && node.loc.y == y) {
				SwitchParent(node,true);
				return true;
			}
		}
		return false;
	}
	
	private void SwitchParent(PathNode node, boolean readd) {
		double cost = Game.map.tiles.get(Game.map.map[node.loc.y][node.loc.x]).speed() + current.cost;
		if (cost<node.cost) {
			node.cost = cost;
			if (readd) {
				openlist.add(node);
				closedlist.remove(node);
			}
		}
	}
	
	private double FindCost(int x, int y, PathNode parent) {
		double cost = Game.map.tiles.get(Game.map.map[y][x]).speed();
		if (parent!=null) {cost+=parent.cost;}
		return cost;
	}
		
	public class PathNode {
		double cost;
		Point loc;
		
		PathNode(int x, int y, double cost) {
			loc = new Point(x,y);
			this.cost = cost;
		}
	}
}
