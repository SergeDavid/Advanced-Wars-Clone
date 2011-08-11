package engine;

import java.util.ArrayList;

/**Put the game stuff in here so all I have to do is end/start this to make a game work or not.*/
public class Battle {
	int totalplayers = 2;
	int currentplayer = 0;
	final int maxplayers = 2;
	
	//Game settings
	boolean FogOfWar;
	int startingmoney = 100;//How much you start with each round.
	int buildingmoney = 50;//How much each building provides.
	
	//Winning condition settings	
	
	public Battle(String mapname) {
		Game.player = new ArrayList<Player>();
		//TODO: Base the players off of the map and currently selected.
		Game.player.add(new Player(true,1,0));
		Game.player.add(new Player(true,2,0));
		if (!Game.map.parse.decode(mapname)) {
			Game.gui.LoginScreen();
			return;
		}
		FixFirstRound();
	}
	
	/**Hack or not to fix the first player not being able to move pre-deployed units in the first round.
	 * Might change this into a setting on creating units from buildings / map creation.*/
	private void FixFirstRound() {
		for (Character unit : Game.player.get(currentplayer).units) {
			unit.acted=false;
			unit.moved=false;
		}
	}

	public void EndTurn() {
		Player ply = Game.player.get(currentplayer);
		for (Character unit : ply.units) {
			unit.acted=false;
			unit.moved=false;
		}
		currentplayer++;
		if (currentplayer>=totalplayers) {currentplayer=0;}
		ply = Game.player.get(currentplayer);
		ply.money+=buildingmoney*Buildingcount(currentplayer);
	}
	
	/**Grabs the number of buildings a player owns.*/
	private int Buildingcount(int owner) {
		int total = 0;
		for (Buildings bld : Game.builds) {
			if (bld.owner==owner) {total++;}
		}
		return total;
	}
}
