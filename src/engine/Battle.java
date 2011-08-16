package engine;

import java.util.ArrayList;

/**Put the game stuff in here so all I have to do is end/start this to make a game work or not.*/
public class Battle {
	int totalplayers = 2;
	public int currentplayer = 0;
	final int maxplayers = 2;
	
	//Game settings
	boolean FogOfWar;
	int startingmoney = 100;//How much you start with each round.
	int buildingmoney = 50;//How much each building provides.
	
	//Winning condition settings	
	
	public Battle(String mapname) {
		Game.player = new ArrayList<players.Base>();
		//TODO: Base the players off of the map and currently selected.
		Game.CreateCommander(0,1,startingmoney,false);
		Game.CreateCommander(0,2,startingmoney,false);
		if (!Game.map.parse.decode(mapname)) {
			Game.gui.LoginScreen();
			return;
		}
	}

	public void EndTurn() {
		players.Base ply = Game.player.get(currentplayer);
		for (units.Base unit : Game.units) {
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
		for (buildings.Base bld : Game.builds) {
			if (bld.owner==owner) {total++;}
		}
		return total;
	}
	
	//TODO: Work on the unitselected version to work better along with the FindCity part.
	public void Action() {
		players.Base ply = Game.player.get(currentplayer);
		//if (NPC) {return;}//Remove comment to make npc players unplayable.
		if (ply.unitselected) {
			if (currentplayer==Game.units.get(ply.selectedunit).owner) {
				if (Game.units.get(ply.selectedunit).moved&&!Game.units.get(ply.selectedunit).acted) {
					Game.units.get(ply.selectedunit).attack(ply.selectx,ply.selecty);
					ply.unitselected=false;
				}
				else if (!Game.units.get(ply.selectedunit).moved) {
					Game.units.get(ply.selectedunit).move(ply.selectx,ply.selecty);
				}
				else {ply.unitselected=false;}
			}
			else {ply.unitselected=false;}
		}
		else {
			if (!ply.FindUnit()) {//Improved version
				ply.unitselected=false;
				if (ply.FindCity()) {
					//Derp Herp Worp? D:
				}
			}
		}
	}	
	/**This will be redone when I set up the unit buying menu.*/
	public void Buyunit(int x, int y) {
		//TODO: Compare money to the prices of each unit.
		if (Game.player.get(currentplayer).money>=20) {
			Game.CreateUnit(0, currentplayer, x, y, false);
			Game.player.get(currentplayer).money-=20;
		}
	}
}
