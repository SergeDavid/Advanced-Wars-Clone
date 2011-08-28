package engine;

import java.util.ArrayList;

/**Put the game stuff in here so all I have to do is end/start this to make a game work or not.*/
public class Battle {
	int totalplayers = 2;
	public int currentplayer = 0;
	
	//Game settings
	boolean FogOfWar;
	int startingmoney = 100;//How much you start with each round.
	int buildingmoney = 50;//How much each building provides.
	int day = 1;
	
	//Winning condition settings	
	
	public Battle() {
		//Moved this to NewGame() so I can reference things in this class before I finish adding a new game.
	}
	
	public void NewGame(String mapname) {
		Game.player = new ArrayList<players.Base>();
		Game.builds = new ArrayList<buildings.Base>();
		Game.units = new ArrayList<units.Base>();
		Game.view.Loc.x = 0;
		Game.view.Loc.y = 0;
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
		if (currentplayer>=totalplayers) {currentplayer=0;day++;}
		ply = Game.player.get(currentplayer);
		if (day!=1) {
			ply.money+=buildingmoney*Buildingcount(currentplayer);
		}
		Game.pathing.LastChanged++;
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
					Game.units.get(ply.selectedunit).action(ply.selectx,ply.selecty);
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
					//Not sure what to do with this yet.
				}
			}
		}
	}	
	/**This will be redone when I set up the unit buying menu.*/
	public void Buyunit(int type, int x, int y) {
		double cost = Game.displayU.get(type).cost*Game.player.get(currentplayer).CostBonus;
		if (Game.player.get(currentplayer).money>=cost) {
			Game.units.add(Game.list.CreateUnit(type, currentplayer, x, y, false));
			Game.player.get(currentplayer).money-=cost;
		}
	}

	public void MaxUsers(int max) {
		//Setup for max players
		if (max<2) {totalplayers = 2;}
		else if (max>12) {totalplayers = 12;}
		else {totalplayers = max;}
		//HACK: Change when I add more images to support more players.
		if (max>4) {totalplayers = 4;
			Game.error.ShowError("The game currently supports only 4 players, not " + max + ".");
		}
		//Adds players, current hacked version. TODO: Unhack this
		for (int i = 0;i<totalplayers;i++) {
			//TODO: Load commander info from the different gui settings somewhere.
			Game.player.add(Game.list.CreateCommander(0,i+1,startingmoney,false));
		}
	}
}
