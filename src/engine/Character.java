package engine;

import java.util.Random;

public class Character {
	//TODO: Find out if it is just easier to reference the base image sprite array instead of saving one for each sprite.
	int sprite = 32;
	String name = "Missing No.";
	String desc = "Unknown description present.";
	int owner;//What player owns it.
	int type;
	

	//life settings
	boolean dead;
	int maxhp = 100;
	int health = maxhp;
	boolean moved;
	boolean acted;
	
	//Location
	int x;
	int y;
	int oldx;
	int oldy;
	boolean[] atkrange = {false,true,false,true,false,true,false,true,false};
	boolean[] movrange = {
			false,false,true,false,false,
			false,true,true,true,false,
			true,true,false,true,true,
			false,true,true,true,false,
			false,false,true,false,false};
	int speed = 2;
	//TODO: Generate an array of available destinations to use in finding range.
	
	//Battle Settings
	double attack = 100;//Base damage done to others
	double defense = 60;//Base armor for taking damage
	//TODO: Add in type modifiers for damage and attack.
	//TODO: Generate an array of attackable locations.
	/** I need to add what kind of unit is being constructed or split it into extended classes.
	 * 
	 * @param owner = What player owns this unit.
	 * @param xx = The X location of the unit.
	 * @param yy = The Y location of the unit.
	 */
	public Character(int owner, int xx,int yy) {
		this.owner=owner;
		Game.player.get(owner).totalunits++;
		oldx = x = xx;
		oldy = y = yy;
		name = "Infantry";
		acted=true;moved=true;
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
	
	public void attack(int destx, int desty) {
		Random rand = new Random();
		if (acted) {return;}
		Character target = FindTarget(destx, desty, true, false);
		if (target!=null) {
			if (!inrange(target)) {acted=true;return;}
			double damage = (attack-target.defense)+(rand.nextInt(20)-10)/10;
			if (damage<1) {damage=1;}
			target.health-=damage;
			if (target.health<=0) {
				Game.player.get(target.owner).units.remove(target);
				Game.player.get(owner).kills++;
				Game.player.get(target.owner).loses++;
				System.out.println("Enemy unit has been destroyed!");
			}
			else {
			  System.out.println("Enemy unit now has " + target.health + " hp left!");
			}
		}
		if (destx==x&&desty==y) {
			Buildings bld = FindBuilding();
			if (bld!=null) {
				System.out.println(bld.team);
				System.out.println(Game.player.get(owner).team);
				if (bld.team!=Game.player.get(owner).team) {
					bld.Capture(health/10,owner);
				}
			}
		}
		acted=true;
	}
	
	private Buildings FindBuilding() {
		for (Buildings bld : Game.builds) {
			if (bld.x==x&&bld.y==y) {
				return bld;
			}
		}
		return null;
	}

	/**Finds out if the character is in an attackable location by sweeping through the atkrange list.*/
	private boolean inrange(Character target) {
		//TODO: Unhacks with a sweep through attackable locations and units.
		if (target.x==x+1 && target.y==y) {return true;}
		if (target.x==x-1 && target.y==y) {return true;}
		if (target.x==x && target.y==y+1) {return true;}
		if (target.x==x && target.y==y-1) {return true;}
		return false;
	}

	/**This finds a unit with the x and y coordinates and returns their data to be used, friendlyfire set to true to find allies, hostilefire to attack foes.*/
	private Character FindTarget(int destx, int desty, boolean hostilefire, boolean friendlyfire) {
		for (Player ply : Game.player) {
			if (ply.team!=Game.player.get(owner).team||friendlyfire) {
				for (Character unit : ply.units) {
					if (unit.x==destx&&unit.y==desty) {
						return unit;
					}
				}
			}
		}
		return null;
	}

	/**Returns true if the x,y location is a place your unit can walk on.*/
	private boolean moveable(int destx, int desty) {
		//TODO: Either put path finding here, or use this as a tool for path finding.
		if (destx<0||desty<0) {return false;}
		//Finds the distance in x and y
		int movex = (destx>x) ? destx-x : x-destx;
		int movey = (desty>y) ? desty-y : y-desty;
		//Hack for use since I don't have the pathfinding yet.
		if (movex+movey>speed) {return false;}
		for (Player ply : Game.player) {
			for (Character unit : ply.units) {
				//HACK: So you can stand still instead of being forced to move.
				if (unit.x==x&&unit.y==y) {}
				else if (unit.x==destx&&unit.y==desty) {return false;}
			}
		}
		if (Game.map.Walkable(Game.map.map[desty][destx])) {return true;}
		System.out.println("Map not walkable!" + Game.map.map[destx][desty]);
		return false;
	}

	/**Returns the image location of the sprite sheet where this unit is located.*/
	public int[] DrawMe() {
		int[] loc = {type,0};
		if (acted) {loc[1]++;}//TODO: Change to acted;
		if (owner%2==1) {loc[1]+=2;}//TODO: Change it to better show owner's colors;
		return loc;
	}
}
