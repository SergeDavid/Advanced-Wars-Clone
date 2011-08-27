package engine;

/**This class stores the lists of the */
public class ListData {
	/**When adding a new Commander, Building, or Unit. Add that to this list so they show up on menu's.*/
	public ListData() {
		//TODO: Set up the other things and turn each of these into a loop so when it hits default (return false) it removes said class and stops.
		Game.displayC.add(new players.Andy(true, 0, 0));
		Game.displayC.add(new players.Colin(true, 0, 0));
		
		Game.displayB.add(new buildings.Capital(0, 0, 0, 0));
		Game.displayB.add(new buildings.Town(0, 0, 0, 0));
		Game.displayB.add(new buildings.Barracks(0, 0, 0, 0));
		Game.displayB.add(new buildings.Shipyard(0, 0, 0, 0));
		Game.displayB.add(new buildings.Airport(0, 0, 0, 0));
		
		Game.displayU.add(new units.Infantry(0, 0, 0, true));
		Game.displayU.add(new units.Mechanic(0, 0, 0, true));
		Game.displayU.add(new units.SmallTank(0, 0, 0, true));
	}
	/**This loads a playable commander into the game.
	 * 
	 * @param which = Which commander to add (list must match order of LoadLists)
	 * @param team = Which team the commander is joined too (A,B,C,D,Etc.)
	 * @param money = How much money the player starts with //TODO: remove this as I changed the way Battle is handled.
	 * @param npc = If true, this commander is not playable by the player (controlled by the computer)
	 */
	public void CreateCommander(int which,int team, int money, boolean npc) {
		switch (which) {
			case 0:Game.player.add(new players.Andy(npc,team,money));break;
			case 1:Game.player.add(new players.Colin(npc,team,money));break;
			default:Game.player.add(new players.Base(npc,team,money));break;
		}
	}
	
	public void CreateCity(int owner, int xx, int yy, int type) {//15 = Neutral, 12, 13, 14 are unused. (12 max players)
		int team = 0;
		if (owner>Game.btl.totalplayers) {
			owner=15;
			if (type == 0) {type = 1;}
		}
		else {team = Game.player.get(owner).team;}
		switch (type) {
			case 0:Game.builds.add(new buildings.Capital(owner, team, xx, yy));break;
			case 1:Game.builds.add(new buildings.Town(owner, team, xx, yy));break;
			case 2:Game.builds.add(new buildings.Barracks(owner, team, xx, yy));break;
			case 3:Game.builds.add(new buildings.Shipyard(owner, team, xx, yy));break;
			case 4:Game.builds.add(new buildings.Airport(owner, team, xx, yy));break;
			default:Game.builds.add(new buildings.Town(owner, team, xx, yy));break;
		}
	}
	
	public void CreateUnit(int type, int owner, int xx, int yy, boolean active) {
		switch (type) {
			case 0:Game.units.add(new units.Infantry(owner, xx, yy, active));break;
			case 1:Game.units.add(new units.Mechanic(owner, xx, yy, active));break;
			case 2:Game.units.add(new units.SmallTank(owner, xx, yy, active));break;
			default:Game.units.add(new units.Base(owner, xx, yy, active));break;
		}
	}
}
