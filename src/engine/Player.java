package engine;

import java.util.ArrayList;
import java.util.List;


public class Player {

	int team;
	boolean NPC;
	boolean defeated;
	List<Character> units = new ArrayList<Character>();
	int totalunits;//How many units does the player have in total
	int usedunits;//How many units does the player have left until turn ends.
	int money;
	
	//TODO: Add in powers and character traits/images.
	int power;//Current Power Level
	int maxpower;//Maximum power level
	boolean unitselected;//If the character has selected a unit or not. (using instead of selectedunit=-1)
	int selectedunit = 0;//Which unit is currently selected to use.

	//Total unit kills and loses, part of end game statistics.
	int kills;
	int loses;
	
	/**
	 * 
	 * @param ai = Is the player controlled by the computer or a player?
	 * @param team = What team does the player play on?
	 * @param who = What character is this player? [Andy,Jack,Etc.]
	 */
	public Player(boolean ai,int team,int who) {
		this.team=team;
		NPC = ai;
		switch (who) {
			case 0:LoadAndy();break;//Change settings to suit the player at hand.
			default:LoadAndy();break;
		}
	}
	
	public void Action() {
		//if (NPC) {return;}//Remove comment to make npc players unplayable.
		if (unitselected) {
			//Handles a unit that has moved but not acted (capture / attacked)
			if (units.get(selectedunit).moved&&!units.get(selectedunit).acted) {
				units.get(selectedunit).attack(Game.selectx,Game.selecty);
				unitselected=false;
			}
			//Handles units that move
			else if (!units.get(selectedunit).moved) {
				units.get(selectedunit).move(Game.selectx,Game.selecty);
			}
			//De-selects the unit if it doesn't move or act.
			else {unitselected=false;}
		}
		else {
			//TODO: Calculate what building and unit the curser is over and grab their id's from that.
			//TODO: Maybe combine the units into a single list? =/
			//Finds a unit and selects its ID from the list then it turns the unitselected boolean to true, or else no unit is selected and it goes to false.
			boolean found = false;
			for (int i=0; i<units.size(); i++) {
				if (units.get(i).x==Game.selectx&&units.get(i).y==Game.selecty) {
					selectedunit=i;
					unitselected=true;
					found=true;
				}
			}
			if (found==false) {unitselected=false;}
			for (Buildings bld : Game.builds) {
				if (bld.x==Game.selectx&&bld.y==Game.selecty&&bld.owner==Game.btl.currentplayer) {
					found = false;
					for (int i=0; i<units.size(); i++) {
						if (units.get(i).x==Game.selectx&&units.get(i).y==Game.selecty) {
							selectedunit=i;
							unitselected=true;
							found=true;
						}
					}
					if (!found) Buyunit(bld.x, bld.y);
				}
			}
		}
	}	
	
	/**Handles what happens when the cancel button is pressed while playing.*/
	public void cancle() {
		if (unitselected) {
			if (units.get(selectedunit).moved&&!units.get(selectedunit).acted) {
				units.get(selectedunit).moved=false;
				units.get(selectedunit).x=units.get(selectedunit).oldx;
				units.get(selectedunit).y=units.get(selectedunit).oldy;
				unitselected=false;
			}
			else if (!units.get(selectedunit).moved) {
				units.get(selectedunit).move(units.get(selectedunit).oldx,units.get(selectedunit).oldy);
				units.get(selectedunit).moved=false;
				unitselected=false;
			}
			else {unitselected=false;}
		}
	}
	
	public void LoadAndy() {
		
	}

	public void Buyunit(int x, int y) {
		System.out.println("BLARGH!!! " + money);
		//TODO: Compare money to the prices of each unit.
		if (money>=20) {
			units.add(new Character(Game.btl.currentplayer, x, y));
			money-=20;
		}
	}
}
