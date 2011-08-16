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
		Game.player = new ArrayList<players.Base>();
		//TODO: Base the players off of the map and currently selected.
		Game.player.add(new players.Andy(true,1,startingmoney));
		Game.player.add(new players.Andy(true,2,startingmoney));
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
		players.Base ply = Game.player.get(currentplayer);
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
	
	//TODO: Just fix everything under this line!
	//TODO: Fix this up to work better and look prettier.
	public void Action() {
		players.Base ply = Game.player.get(currentplayer);
		//if (NPC) {return;}//Remove comment to make npc players unplayable.
		if (ply.unitselected) {
			//Handles a unit that has moved but not acted (capture / attacked)
			if (ply.units.get(ply.selectedunit).moved&&!ply.units.get(ply.selectedunit).acted) {
				ply.units.get(ply.selectedunit).attack(ply.selectx,ply.selecty);
				ply.unitselected=false;
			}
			//Handles units that move
			else if (!ply.units.get(ply.selectedunit).moved) {
				ply.units.get(ply.selectedunit).move(ply.selectx,ply.selecty);
			}
			//De-selects the unit if it doesn't move or act.
			else {ply.unitselected=false;}
		}
		else {
			//TODO: Calculate what building and unit the curser is over and grab their id's from that.
			//TODO: Maybe combine the units into a single list? =/
			//Finds a unit and selects its ID from the list then it turns the unitselected boolean to true, or else no unit is selected and it goes to false.
			boolean found = false;
			for (int i=0; i<ply.units.size(); i++) {
				if (ply.units.get(i).x==ply.selectx&&ply.units.get(i).y==ply.selecty) {
					ply.selectedunit=i;
					ply.unitselected=true;
					found=true;
				}
			}
			if (found==false) {ply.unitselected=false;}
			for (Buildings bld : Game.builds) {
				if (bld.x==ply.selectx&&bld.y==ply.selecty&&bld.owner==Game.btl.currentplayer) {
					found = false;
					for (int i=0; i<ply.units.size(); i++) {
						if (ply.units.get(i).x==ply.selectx&&ply.units.get(i).y==ply.selecty) {
							ply.selectedunit=i;
							ply.unitselected=true;
							found=true;
						}
					}
					if (!found) Buyunit(bld.x, bld.y);
				}
			}
		}
	}	
	public void Cancle() {
		players.Base ply = Game.player.get(currentplayer);
		if (ply.unitselected) {
			if (ply.units.get(ply.selectedunit).moved&&!ply.units.get(ply.selectedunit).acted) {
				ply.units.get(ply.selectedunit).moved=false;
				ply.units.get(ply.selectedunit).x=ply.units.get(ply.selectedunit).oldx;
				ply.units.get(ply.selectedunit).y=ply.units.get(ply.selectedunit).oldy;
				ply.unitselected=false;
			}
			else if (!ply.units.get(ply.selectedunit).moved) {
				ply.units.get(ply.selectedunit).move(ply.units.get(ply.selectedunit).oldx,ply.units.get(ply.selectedunit).oldy);
				ply.units.get(ply.selectedunit).moved=false;
				ply.unitselected=false;
			}
			else {ply.unitselected=false;}
		}
	}
	public void Buyunit(int x, int y) {
		System.out.println("BLARGH!!! " + Game.player.get(currentplayer).money);
		//TODO: Compare money to the prices of each unit.
		if (Game.player.get(currentplayer).money>=20) {
			Game.player.get(currentplayer).units.add(new Character(Game.btl.currentplayer, x, y));
			Game.player.get(currentplayer).money-=20;
		}
	}
}
