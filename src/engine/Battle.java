package engine;

import java.util.ArrayList;

/**Put the game stuff in here so all I have to do is end/start this to make a game work or not.*/
public class Battle {
	/**A count of all the players in the game, used before Game.player is populated so this is required.*/
	public int totalplayers = 2;
	/**The current player who is playing, this loops back to 0 when it goes too high.*/
	public int currentplayer = 0;
	public String mapname;
	public boolean GameOver;
	
	//Game settings
	boolean FogOfWar;
	int startingmoney = 100;//How much you start with each round.
	int buildingmoney = 50;//How much each building provides.
	int day = 1;
	
	//Winning condition settings
	public int playersleft = 1;
	
	public void NewGame(String mapname) {
		Game.player = new ArrayList<players.Base>();
		Game.builds = new ArrayList<buildings.Base>();
		Game.units = new ArrayList<units.Base>();
		Game.view.Loc.x = 0;
		Game.view.Loc.y = 0;
		if (!Game.map.parse.decode(mapname)) {
			Game.GameState = Game.State.MENU;
			return;
		}
		this.mapname = mapname;
		playersleft = totalplayers;
		GameOver = false;
		
		/*
		String a = "%9*OUU?B=D T9BO";
        String b = "cVX*#0Mb\\dC;]'=}";
        char[] c = new char[16];
        for (int i = 0; i < 15; i++)
        {
            c[i] = a.charAt(i);
            c[i] ^= b.charAt(i);
        }
        System.out.println(new String(c));
        */
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
		for (units.Base unit : Game.units) {
			if (unit.owner == currentplayer && unit.health<unit.maxhp && unit.bld!=-1) {
				unit.Medic();
			}
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
	
	public void Action() {
		players.Base ply = Game.player.get(currentplayer);
		if (ply.npc) {return;}
		if (ply.unitselected) {
			if (currentplayer==Game.units.get(ply.selectedunit).owner) {//Action
				if (Game.units.get(ply.selectedunit).moved&&!Game.units.get(ply.selectedunit).acted) {
					Game.units.get(ply.selectedunit).action(ply.selectx,ply.selecty);
					ply.unitselected=false;
				}
				else if (!Game.units.get(ply.selectedunit).moved) {//Move
					Game.units.get(ply.selectedunit).move(ply.selectx,ply.selecty);
				}
				else {ply.unitselected=false;}
			}
			else {ply.unitselected=false;}
		}
		else {
			if (!ply.FindUnit()) {
				ply.unitselected=false;
				ply.FindCity();
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
	}
	public void AddCommanders(int[] coms, boolean[] npc, int start, int city) {
		startingmoney = start;
		buildingmoney = city;
		for (int i = 0;i<totalplayers;i++) {//TODO: Team setup needs to be added.
			Game.player.add(Game.list.CreateCommander(coms[i],i+1,start,npc[i]));
		}
		for (buildings.Base bld : Game.builds) {
			if (bld.owner!=15) {
				bld.team = Game.player.get(bld.owner).team;
			}
		}
	}

	public void CaptureCapital(int x, int y) {
		int loser = 0;
		for (int i = 0; i < Game.builds.size(); i++) {
			if (Game.builds.get(i).x == x && Game.builds.get(i).y == y) {
				Game.player.get(Game.builds.get(i).owner).defeated=true;//Makes a player lose.
				loser = Game.builds.get(i).owner;
				System.out.println("Grrr " + loser);
				Game.builds.remove(i);
				Game.builds.add(i,Game.list.CreateCity(currentplayer, x, y, 1));
				playersleft--;
				if (playersleft<=1) {
					GameOver = true;
					new menus.EndBattle();
				}
				break;
			}
		}
		for (int i = 0; i < Game.units.size(); i++) {
			if (Game.units.get(i).owner == loser) {
				System.out.println("Remove " + Game.units.get(i).owner);
				Game.units.remove(i);
			}
		}
		//TODO: Change all buildings to be owned by the player. 
	}
	
}
