package units;

import java.util.Random;

import engine.Game;

public class Base {
	public String name = "Missing No.";
	public String nick = "MsNo";
	public String desc = "Unknown description present.";
	public int owner;
	int img;
	

	//life settings
	boolean dead;
	int maxhp = 100;
	public int health = maxhp;
	public boolean moved;
	public boolean acted;
	
	//Location
	public int x;
	public int y;
	public int oldx;
	public int oldy;
	public int speed = 2;
	
	//Ranges TODO: Change this to something better. (Move = A* path-finding, Attack = anything better)
	public boolean[] atkrange = {false,true,false,true,false,true,false,true,false};
	public boolean[] movrange = {
			false,false,true,false,false,
			false,true,true,true,false,
			true,true,false,true,true,
			false,true,true,true,false,
			false,false,true,false,false};
	
	//Battle Settings
	public double attack = 100;//Base damage done to others
	public double defense = 60;//Base armor for taking damage
	
	/** I need to add what kind of unit is being constructed or split it into extended classes.
	 * 
	 * @param owner = What player owns this unit.
	 * @param xx = The X location of the unit.
	 * @param yy = The Y location of the unit.
	 */
	public Base(int owner, int xx, int yy, boolean active) {
		this.owner=owner;
		oldx = x = xx;//Old locations are used when someone changes their minds on moving a unit.
		oldy = y = yy;
		if (!active) {
			acted=true;
			moved=true;	
		}
	}
	
	public void move(int destx, int desty) {
		//TODO: Add in path finding.
		if (moved) {return;}
		if (moveable(destx,desty)) {
			oldx=x;oldy=y;
			x=destx;y=desty;
			moved=true;
		}
	}
	/**Returns true if the x,y location is a place your unit can walk on.*/
	private boolean moveable(int destx, int desty) {
		//TODO: Either put path finding here, or use this as a tool for path finding.
		if (destx<0||desty<0) {return false;}
		int movex = (destx>x) ? destx-x : x-destx;//Finds the total distance traveled.
		int movey = (desty>y) ? desty-y : y-desty;
		if (movex+movey>speed) {return false;}//Hack for use since I don't have the path finding yet.
		for (units.Base unit : Game.units) {
			if (unit.x==x&&unit.y==y) {}//HACK: So you can stand still instead of being forced to move.
			else if (unit.x==destx&&unit.y==desty) {return false;}
		}
		if (Game.map.Walkable(Game.map.map[desty][destx])) {return true;}
		return false;
	}
	public void attack(int destx, int desty) {
		Random rand = new Random();
		if (acted) {return;}
		units.Base target = FindTarget(destx, desty, true, false);
		if (target!=null) {
			if (!inrange(target)) {acted=true;return;}
			double damage = (attack-target.defense)+(rand.nextInt(20)-10)/10;
			if (damage<1) {damage=1;}
			target.health-=damage;
			if (target.health<=0) {
				Game.units.remove(target);
				Game.player.get(owner).kills++;
				Game.player.get(target.owner).loses++;
				System.out.println("Enemy unit has been destroyed!");
			}
			else {
			  System.out.println("Enemy unit now has " + target.health + " hp left!");
			}
		}
		if (destx==x&&desty==y) {
			buildings.Base bld = FindBuilding();
			if (bld!=null) {
				if (bld.team!=Game.player.get(owner).team) {
					bld.Capture(health/10,owner);
				}
			}
		}
		acted=true;
	}
	
	private buildings.Base FindBuilding() {
		for (buildings.Base bld : Game.builds) {
			if (bld.x==x&&bld.y==y) {return bld;}
		}
		return null;
	}
	/**Finds out if the character is in an attackable location by sweeping through the atkrange list.*/
	private boolean inrange(units.Base target) {//TODO: Unhacks with a sweep through attackable locations and units.
		if (target.x==x+1 && target.y==y) {return true;}
		if (target.x==x-1 && target.y==y) {return true;}
		if (target.x==x && target.y==y+1) {return true;}
		if (target.x==x && target.y==y-1) {return true;}
		return false;
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
	
	/**Returns the image location of the sprite sheet where this unit is located.
	 * Type = Left / Right (image)
	 * Team = Up / Down (color)*/
	public int[] DrawMe() {
		int[] loc = {img,0};
		if (acted) {loc[1]++;}
		if (owner%2==1) {loc[1]+=2;}
		return loc;
	}

	/**Undoes what the unit has done so far.*/
	public void cancle() {
		if (moved&&!acted) {//If you moved, but haven't acted yet.
			moved=false;
			x=oldx;
			y=oldy;
		}
		else if (!moved) {//Can't remember exactly how this is different from the above.
			move(oldx,oldy);
			moved=false;
		}
	}
}
