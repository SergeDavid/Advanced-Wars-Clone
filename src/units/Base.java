package units;

import java.awt.Point;
import java.util.Vector;
import engine.Game;

public class Base {
	public String name = "Missing No.";
	public String nick = "MsNo";
	public String desc = "Unknown description present.";
	public int owner;
	int img;

	//Extras
	public int Fog = 5;//Fog of war setting.
	public int cost = 100;
	public String building = "barracks";//Which building it can be bought from.
	public boolean raider = false;
	public int bld = -1;//The building this unit is currently standing on, null or -1 if none.
	public int MaxFuel = 1000;
	public int Fuel = 1000;//Total fuel left
	public int Ammo = 10;//Ammo used for the main weapon, secondary weapon doesn't run out.
	
	//life settings
	boolean dead;
	public int maxhp = 100;
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
	/**This is the movement type of the unit. 0 = Infantry, 1 = Vehicle, 2 = Tank, 3 = Ship, 4 = Aircraft*/
	public enum Move {INF,TIRE,TANK,SHIP,FLY};
	public Move legs = Move.INF;
	
	//Battle Settings 
	//TODO: Add alternate weapon setup
	public boolean MoveAndShoot = true;//Allows units to move then shoot, or make them stay in the same location in order to attack.
	public int MaxAtkRange = 1;//How many squares away from the unit can it attack. (1 being default, 0 being none)
	public int MinAtkRange = 1;//This is used for ranged units such as artillery.
	public int[] MainAttack = {//Base damage to each unit.
			55,50,10,20,//Ground
			20,//Air
			//0,0,0,0,//Water
			50,50,50,50};//Extra (For mods)
	
	//What can and can't be shot at.
	public enum Armor {FOOT,VEHICLE,TANK,AIR,SHIP,ALL,NONE};//TODO: Hook this up to attacking.
	public Armor ArmorType = Armor.FOOT;
	public Armor[] MainATK = {Armor.ALL};//The main attack to be used.
	
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
		CityPointer();
		if (!active) {//On building option.
			acted = true;
			moved = true;
			Game.pathing.LastChanged = System.currentTimeMillis();
		}
	}
	
	/**This is currently being used by the path finding, the other is so I can have a pretty visual for actually moving it.*/
	public boolean PathCheck(int destx, int desty) {
		if (destx<0||desty<0) {return false;}
		if (destx>=Game.map.width||desty>=Game.map.height) {return false;}
		for (units.Base unit : Game.units) {//Allows units in the same team to walk past each other.
			if (unit.x==destx&&unit.y==desty&&Game.player.get(unit.owner).team!=Game.player.get(owner).team) {
				return false;
			}
		}
		switch (legs) {//This is used to find out if the unit can move to said tile or not.
			case INF: if (Game.map.map[desty][destx].walk) {return true;} break;
			case TIRE: if (Game.map.map[desty][destx].drive) {return true;} break;
			case TANK: if (Game.map.map[desty][destx].drive) {return true;} break;
			case SHIP: if (Game.map.map[desty][destx].swim) {return true;} break;
			case FLY: if (Game.map.map[desty][destx].fly) {return true;} break;
			default: if (Game.map.map[desty][destx].fly) {return true;} break;
		}
		return false;
	}
	
	/**Use this method instead of attack or capture for ai (m)*/
	public void action(int destx, int desty) {
		if (acted) {return;}
		if (!attack(destx,desty,true)) {//If there was no unit to attack, the unit checks to see if there is a building there.
			capture(destx,desty);
		}
		acted=true;
	}
	/**
	 * Attacks someone at X,Y and either returns fire or not.
	 * @param destx = Attacked Y location
	 * @param desty = Attacked X location
	 * @param returnfire = Allows units to shoot back and not cause a never ending loop until someone dies.
	 * @return Returns true if the unit attacked something, returns false if it didn't. (so it can see if it can capture a building)
	 */
	public boolean attack(int destx, int desty, boolean returnfire) {
		//Disables the ability to attack when a unit has already moved positions.
		if ((x != oldx && y != oldy) && !MoveAndShoot) {return false;}

		units.Base target = FindTarget(destx, desty, true, false);
		if (target!=null) {
			if (inrange(target.x, target.y)) {		
				
				double damage = DamageFormula(target);
				target.health-=damage;
				
				if (target.health<=0) {
					damage+=target.health;
					if (target.bld!=-1) {
						Game.builds.get(target.bld).Locked=false;
					}
					Game.units.remove(target);
					Game.player.get(owner).kills++;
					Game.player.get(target.owner).loses++;
					Game.pathing.LastChanged = System.currentTimeMillis();
				}
				else if (returnfire) {target.attack(x,y,false);}
				//Increases commander power.
				Game.player.get(owner).Powerup(damage, false);
				Game.player.get(target.owner).Powerup(damage, true);
				
				return true;
			}
		}
		return false;
	}
	private void capture(int destx,int desty) {
		if (!raider) {return;}
		if (destx==x&&desty==y) {
			if (bld!=-1) {
				buildings.Base city = Game.builds.get(bld);
				if (city.team!=Game.player.get(owner).team) {
					city.Capture((int) (health/10*Game.player.get(owner).CaptureBonus),owner);
				}
			}
		}
	}
	public void move(int destx, int desty) {
		if (moved) {return;}
		if (moveable(destx,desty)) {
			Fuel -= FuelCost(destx,desty);
			moved=true;
			oldx=x;oldy=y;
			x=destx;y=desty;
			CityPointer();
			//Path finding update
			Game.pathing.LastChanged = System.currentTimeMillis();
		}
	}

	public boolean inrange(int xx, int yy) {
		//TODO: Add in calculations for type (line / sphere / cone), it is currently only sphere
		xx = (xx>x) ? xx-x : x-xx;//Finds the total distance.
		yy = (yy>y) ? yy-y : y-yy;
		if (xx+yy>MaxAtkRange) {return false;}
		if (xx+yy<MinAtkRange) {return false;}
		return true;
	}
	/**Undoes what the unit has done so far.*/
	public void cancle() {
		if (moved&&!acted) {
			moved=false;
			x=oldx;
			y=oldy;
		}
	}
	/**Hits the Path-finding to get a list of reachable points on the map.*/
	public void Pathing() {
		if (LastPathed<Game.pathing.LastChanged) {
			int distance = (int) ((Fuel < speed) ? Fuel : speed);
			map = Game.pathing.FindPath(this, distance);	
		}
	}
	
	//==================================================
	
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
	/**Returns the ID# of the building the unit is currently standing on to "bld". 
	 * This is updated on creation and move.
	 * This also resets the city health if a unit moves off of it.*/
	private void CityPointer() {
		if (bld!=-1) {
			if (oldx != x || oldy !=y) {
				Game.builds.get(bld).health = Game.builds.get(bld).maxhealth;
			} 
			Game.builds.get(bld).Locked = false;
		}
		bld = -1;
		for (int i = 0; i < Game.builds.size(); i++) {
			if (Game.builds.get(i).x==x && Game.builds.get(i).y==y) {
				bld = i;
				Game.builds.get(bld).Locked = true;
				return;
			}
		}
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
	/**Returns the cost of fuel for traveling to your desired destination.*/
	private int FuelCost(int x2, int y2) {
		//TODO: Use destination cost instead of distance.
		int used = (x>x2) ? x - x2 : x2 - x ;
		used += (y>y2) ? y - y2 : y2 - y ;
		return used;
	}
	private double DamageFormula(Base target) {
		double dmg = MainAttack[FindUnitID(target)]; //Base Attack
		dmg *= health*0.01; //Health Effectiveness
		dmg *= Game.player.get(owner).WeaponBonus; //Commander Bonus
		dmg -= (target.health*0.01)*Game.player.get(target.owner).ArmorBonus; //Commander Defense Bonus
		dmg -= (target.health*0.01)*Game.map.map[target.y][target.x].defense(); //Terrain Defense Bonus

		//Returns the outcome, with a minimum damage of 0%
		if (dmg < 0) {dmg = 0;}
		return dmg;
	}
	private int FindUnitID(Base target) {
		for (int i = 0; i < Game.displayU.size(); i++) {
			if (Game.displayU.get(i).name.equals(target.name)) {
				return i;
			}
		}
		return 0;
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

	public void Medic() {
		if (Game.builds.get(bld).team != Game.player.get(owner).team) {return;}
		//Health Stuff
		int hp = health+20;
		if (hp>maxhp) {hp=maxhp;}
		//Money Stuff
		double money = Math.floor((hp-health)*5);
		if (money<=Game.player.get(Game.btl.currentplayer).money) {
			Game.player.get(Game.btl.currentplayer).money-=money;
			health = hp;
			//Fuel is free just because
			Fuel = MaxFuel;
		}
	}
}
 