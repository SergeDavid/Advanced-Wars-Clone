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
				System.out.println(bld.team);
				System.out.println(Game.player.get(owner).team);
				if (bld.team!=Game.player.get(owner).team) {
					bld.Capture(health/10,owner);
				}
			}
		}
		acted=true;
	}
	
	private buildings.Base FindBuilding() {
		for (buildings.Base bld : Game.builds) {
			if (bld.x==x&&bld.y==y) {
				return bld;
			}
		}
		return null;
	}

	/**Finds out if the character is in an attackable location by sweeping through the atkrange list.*/
	private boolean inrange(units.Base target) {
		//TODO: Unhacks with a sweep through attackable locations and units.
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
				if (unit.x==destx&&unit.y==desty) {
					return unit;
				}
			}
		}
		return null;
	}

}
