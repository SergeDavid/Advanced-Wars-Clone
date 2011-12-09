package engine;

/**
 * When adding a new commander, building, or unit to the game, just add the class to the Create#### switch.
 * Load#### creates a unit, commander, city and then sets its properties to match its state when you saved the game.
 * For finding type when Loading, it takes the units name, and compares it to the Display# list (reference list).
 * The reference lists will be automatically updated thanks to ListData() constructor.
 * @author SergeDavid
 * @version 0.2
 */
public class ListData {
	/**Starting ListData() will load all of the commanders, buildings, and units into reference lists (display#) for use in menu's.*/
	public ListData() {
		//These loop for a max of 100 items (shouldn't hit that high), a null return from Create###(); will end the loop.
		for (int i=0; i<100 ;i++) {
			Game.displayC.add(CreateCommander(i, 0, 0, false));
			if (Game.displayC.get(i)==null) {
				Game.displayC.remove(i);
				break;
			}
		}
		for (int i=0; i<100 ;i++) {
			Game.displayB.add(CreateCity(-1, 0, 0, i));
			if (Game.displayB.get(i)==null) {
				Game.displayB.remove(i);
				break;
			}
		}
		for (int i=0; i<100 ;i++) {
			Game.displayU.add(CreateUnit(i, 0, 0, 0, false));
			if (Game.displayU.get(i)==null) {
				Game.displayU.remove(i);
				break;
			}
		}
	}
	
	/**
	 * This loads a playable commander into the game for the reference and in use commanders. [Battle]
	 * @param which = Which commander to add (list must match order of LoadLists)
	 * @param team = Which team the commander is joined too (A,B,C,D,Etc.)
	 * @param money = How much money the player starts with
	 * @param npc = If true, this commander is not playable by the player (controlled by the computer)
	 */
	public players.Base CreateCommander(int which,int team, int money, boolean npc) {
		switch (which) {
			case 0:return new players.Andy(npc,team,money);
			case 1:return new players.Colin(npc,team,money);
			case 2:return new players.Max(npc,team,money);
			case 3:return new players.Sammi(npc,team,money);
		}
		return null;
	}
	/**
	 * This creates a building (town/barracks/seaport) [MapParser]
	 * @param owner = The player that owns the building (-1 to skip formatting) (15 is neutral, 12~14 are unused (and more if I lower max players from 12))
	 * @param xx
	 * @param yy
	 * @param type = The building to be created in question.
	 * @returnIt should never return null except for the reference list [ListData();]
	 * 
	 * @exception WARNING: removed the filter for proper owner buildings.
	 */
	public buildings.Base CreateCity(int owner, int xx, int yy, int type) {//15 = Neutral, 12, 13, 14 are unused. (12 max players)
		//TODO: Warning: I removed the check to make sure each building is legal.
		System.out.println("List level owner: " + owner);
		switch (type) {
			case 0:return new buildings.Capital(owner, xx, yy);
			case 1:return new buildings.Town(owner, xx, yy);
			case 2:return new buildings.Barracks(owner, xx, yy);
			case 3:return new buildings.Airport(owner, xx, yy);
			case 4:return new buildings.Shipyard(owner, xx, yy);
		}
		return null;
	}
	/**
	 * This creates a unit from a city and for reference. [Battle]
	 * @param type = The unit in question being loaded from the switch.
	 * @param owner = The player that the unit is commanded by.
	 * @param xx
	 * @param yy
	 * @param active
	 * @return It should never return null except for the reference list [ListData();]
	 */
	public units.Base CreateUnit(int type, int owner, int xx, int yy, boolean active) {
		switch (type) {
			case 0:return new units.Infantry(owner, xx, yy, active);
			case 1:return new units.Mechanic(owner, xx, yy, active);
			case 2:return new units.SmallTank(owner, xx, yy, active);
			case 3:return new units.Artillery(owner, xx, yy, active);
			case 4:return new units.Helecopter(owner, xx, yy, active);
		}
		return null;
	}
	
	
	/**
	 * This method is for creating a commander from a saved file.
	 * @param Name
	 * @param Defeated
	 * @param Team
	 * @param Money
	 * @param Kills
	 * @param Loses
	 * @param Power
	 * @param Using
	 * @param npc
	 */
	public void LoadCommander(String Name, boolean Defeated, int Team, int Money, int Kills, int Loses, int Power, int Using, boolean npc) {
		for (int i = 0; i < Game.displayC.size(); i++) {
			if (Game.displayC.get(i).name.equals(Name)) {
				Game.player.add(CreateCommander(i, Team, Money, npc));
				players.Base ply = Game.player.get(Game.player.size()-1);
				ply.defeated = Defeated;
				ply.kills = Kills;
				ply.loses = Loses;
				ply.power = Power;
				//TODO: Add in currently using power to player class and here.
			}
		}
	}
	/**
	 * This adds / changes a city from a saved file.
	 * @param Name
	 * @param Owner
	 * @param Health
	 * @param id
	 */
	public void LoadCity(String Name, int Owner, int Health, int id) {
		//If the building changed (capital to town as an example) it will switch it.
		if (!Name.equals(Game.builds.get(id).name)) {
			for (int i = 0; i < Game.displayB.size(); i++) {
				if (Game.displayB.get(i).name.equals(Name)) {
					Game.builds.add(id, CreateCity(Owner, Game.builds.get(id).x, Game.builds.get(id).y, i));
				}
			}
		}
		//Changes owner and health of building to match it's current standings
		buildings.Base bld = Game.builds.get(id);
		bld.owner = Owner;
		bld.health = Health;
	}

	public void LoadUnit(String Name, int Owner, int Health, int Ammo, int Fuel, int X, int Y, boolean Acted) {
		for (int i = 0; i < Game.displayU.size(); i++) {
			if (Game.displayU.get(i).name.equals(Name)) {
				Game.units.add(CreateUnit(i, Owner, X, Y, Acted));
				units.Base unit = Game.units.get(Game.units.size()-1);
				unit.health = Health;
				unit.Ammo = Ammo;
				unit.Fuel = Fuel;
			}
		}
	}
}
