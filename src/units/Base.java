package units;

import java.awt.Point;
import java.util.Vector;

import engine.Game;

public class Base {
	public String name = "Missing No.";
	public String nick = "MsNo";
	public String desc = "Unknown description present.";
	public int building;//Which building it can be bought from. TODO: Might turn into an array so I can have multiple buildings / other things can buy them.
	public int owner;
	int img;
	public int cost = 100;
	
	//life settings
	boolean dead;
	int maxhp = 100;
	public int health = maxhp;
	public boolean moved;
	public boolean acted;
	
	//Pathing Stuff
	public Vector<Point> map = new Vector<Point>();//TEST
	public long LastPathed;
	
	//Location
	public int x;
	public int y;
	public int oldx;
	public int oldy;
	public double speed = 2;
	
	//Battle Settings
	public boolean MoveAndShoot = true;//Allows units to move then shoot, or make them stay in the same location in order to attack.
	public double mainatk = 1.0;//Percentage bonus for main weapon.
	public double attack = 100;//Base damage done to others
	public double defense = 60;//Base armor for taking damage
	
	public int MaxAtkRange = 1;//How many squares away from the unit can it attack. (1 being default, 0 being none)
	public int MinAtkRange = 1;//This is used for ranged units such as artillery.
	
	public int Fog = 5;//Fog of war setting.
	public int Fuel = 100;//Total fuel left TODO: Add fuel and refueling at cities
	public int FuelUse = 1;//How much fuel is used each tile the unit moves.
	public int Ammo = 10;//Ammo used for the main weapon (uses alternate when it is gone)
	
	//TODO: Add armor types ()
	//TODO: Add Ammo for main attack (targets units with armor type of listed elements)
	//TODO: Add a list/array of armor types the main weapon fires at
	
	//Move Types
	/**This is the movement type of the unit.
	 * 0 = Infantry, 1 = Vehicle, 2 = Ship, 3 = Aircraft*/
	int MovType = 0;
	final int MovInf = 0;
	final int MovMob = 1;
	final int MovShp = 2;
	final int MovFly = 3;
	
	/** I need to add what kind of unit is being constructed or split it into extended classes.
	 * 
	 * @param owner = What player owns this unit.
	 * @param xx = The X location of the unit.
	 * @param yy = The Y location of the unit.
	 */
	public Base(int owner, int xx, int yy, boolean active) {
		this.owner = owner;
		oldx = x = xx;//Old locations are used when someone changes their minds on moving a unit.
		oldy = y = yy;
		if (!active) {
			acted = true;
			moved = true;	
			Game.pathing.LastChanged = System.currentTimeMillis();
		}
	}
	
	public void move(int destx, int desty) {
		if (moved) {return;}
		if (moveable(destx,desty)) {
			oldx=x;oldy=y;
			x=destx;y=desty;
			moved=true;
			//Path finding stuff
			Game.pathing.LastChanged = System.currentTimeMillis();
		}
	}
	/**This checks to see if the destination is in the map, and then calls the path-finder and returns true if the location is movable.*/
	private boolean moveable(int destx, int desty) {
		if (destx<0||desty<0) {return false;}
		if (destx>=Game.map.width||desty>=Game.map.height) {return false;}
		if (Pathed(destx,desty)) {return true;}
		return false;
	}
	/**If the location is in the movable locations list, the unit will move there.*/
	private boolean Pathed(int destx, int desty) {
		for (Point p : map) {
			if (p.x == destx && p.y == desty) {return true;}
		}
		return false;
	}

	/**This is currently being used by the path finding, the other is so I can have a pretty visual for actually moving it.*/
	public boolean PathCheck(int destx, int desty) {
		if (destx<0||desty<0) {return false;}
		if (destx>=Game.map.width||desty>=Game.map.height) {return false;}
		for (units.Base unit : Game.units) {
			if (unit.x==destx&&unit.y==desty) {return false;}
		}
		switch (MovType) {//This is used to find out if the unit can move to said tile or not.
			case 0: if (Game.map.map[desty][destx].walk()) {return true;} break;
			case 1: if (Game.map.map[desty][destx].drive()) {return true;} break;
			case 2: if (Game.map.map[desty][destx].swim()) {return true;} break;
			case 3: if (Game.map.map[desty][destx].fly()) {return true;} break;
			default: if (Game.map.map[desty][destx].fly()) {return true;} break;
		}
		return false;
	}
	
	public void action(int destx, int desty) {
		if (acted) {return;}//TODO: Switch this area to a menu with wait, attack (any unit in range after moving), capture(if map[y][x]==city)
		if (!attack(destx,desty,true)) {//If there was no unit to attack, the unit checks to see if there is a building there.
			capture(destx,desty);
		}
		acted=true;
	}

	private boolean attack(int destx, int desty, boolean returnfire) {
		//Disables the ability to attack when a unit has already moved positions.
		if (x != oldx && y != oldy && !MoveAndShoot) {return false;}

		units.Base target = FindTarget(destx, desty, true, false);
		if (target!=null) {
			if (inrange(target.x, target.y)) {
				//TODO: Add in commander bonuses as well as main weapon and armor bonuses. And also Terrain defense bonus
				double damage = (attack)-
						(target.defense*Game.map.map[target.y][target.x].defense());
				if (damage<1) {damage=1;}
				target.health-=damage;
				if (target.health<=0) {
					damage+=target.health;
					Game.units.remove(target);
					Game.player.get(owner).kills++;
					Game.player.get(target.owner).loses++;
					Game.pathing.LastChanged = System.currentTimeMillis();
				}
				System.out.println(target.defense*Game.map.map[target.y][target.x].defense());
				//Increases commander power.
				Game.player.get(owner).Powerup(damage, false);
				Game.player.get(target.owner).Powerup(damage, true);
				
				if (returnfire) {target.attack(x,y,false);}//return fire?
				return true;
			}
		}
		return false;
	}
	private void capture(int destx,int desty) {
		if (destx==x&&desty==y) {
			buildings.Base bld = FindBuilding();
			if (bld!=null) {
				if (bld.team!=Game.player.get(owner).team) {
					bld.Capture(health/10,owner);
				}
			}
		}
	}
	
	private buildings.Base FindBuilding() {
		for (buildings.Base bld : Game.builds) {
			if (bld.x==x&&bld.y==y) {return bld;}
		}
		return null;
	}

	public boolean inrange(int xx, int yy) {
		//TODO: Add in calculations for type (line / sphere / cone), it is currently only sphere
		xx = (xx>x) ? xx-x : x-xx;//Finds the total distance.
		yy = (yy>y) ? yy-y : y-yy;
		if (xx+yy>MaxAtkRange) {return false;}
		if (xx+yy<MinAtkRange) {return false;}
		return true;
	}
	/**This finds a unit with the x and y coordinates and returns their data to be used, friendly-fire set to true to find allies, hostile-fire to attack foes.*/
	private units.Base FindTarget(int destx, int desty, boolean hostilefire, boolean friendlyfire) {
		for (units.Base unit : Game.units) {
			if (Game.player.get(unit.owner).team!=Game.player.get(owner).team||friendlyfire) {
				if (unit.x==destx&&unit.y==desty) {return unit;}
			}
		}
		return null;
	}
	
	/**
	 * Returns the image location of the sprite sheet where this unit is located.
	 * Type = Left / Right (image)
	 * Team = Up / Down (color)
	 * */
	public int[] DrawMe() {
		int[] loc = {img,owner*2};
		if (acted) {loc[1]++;}
		return loc;
	}

	/**Undoes what the unit has done so far.*/
	public void cancle() {
		if (moved&&!acted) {
			moved=false;
			x=oldx;
			y=oldy;
		}
	}

	public void Pathing() {
		if (LastPathed<Game.pathing.LastChanged) {
			int distance = (int) ((Fuel < speed) ? Fuel : speed);
			map = Game.pathing.FindPath(this, distance);	
		}
	}
}
