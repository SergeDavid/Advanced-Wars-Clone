package players;

import java.util.ArrayList;
import java.util.List;

import engine.Character;

public class Base {
	
	//TODO: Move to Game? D:
	public List<Character> units = new ArrayList<Character>();
	
	//Basic Info
	public String name;
	public String desc;
	public int team;
	public int money;
	
	//Control Info (npc / skip)
	/**If true the computer will control said units instead of the player.*/
	public boolean npc;
	/**If true the player is skipped.*/
	public boolean defeated;
	
	//Special Power
	/**The current power level, max is level2.*/
	public int power;
	/**The amount of power points needed to achieve the first ability. Set to 0 for it to not exist*/
	public int level1;
	/**The amount of power points needed to achieve the second ability.*/
	public int level2;
	
	//Unit selection, 
	//TODO: Place game cursor here instead of having every player share one
	public boolean unitselected;//If the character has selected a unit or not. (using this instead of selectedunit=-1)
	public int selectedunit = 0;//Which unit is currently selected to use.
	public int usedunits;//How many units does the player have left until turn ends. Currently unused.
	public int kills;
	public int loses;
	
	//Location of players images to be used in game (Might do a sprite sheet done in rows, but right now I'm planning on a grid)
	int imgx;
	int imgy;
	public int selectx;
	public int selecty;
	
	/**
	 * @param ai = If true, this character is not controlled by the player.
	 * @param color = What team the player is associated with.
	 * @param bling = The amount of money the player starts with.
	 */
	public Base(boolean ai, int color,int bling) {//TODO: Might include an actual color attribute, but for now that is the team.
		team = color;
		npc = ai;
		money = bling;
	}
	
	public void Powerup() {//TODO: Power gained is based on how much damage you do / are dealt and some other fancy modifiers.
		power+=10;
		if (power>level2) {power=level2;}
	}
	
	/** This method is used in place of directly calling MyPower1 or 2
	 * I did it so I don't have to add the (enough power and not 0) checks along with removing the appropriate amount.
	 * 
	 * @param which = if (true) {level 1 power} else {level 2 power}
	 */
	public void UsePower(boolean which) {
		if (which) {
			if (power>=level1&&level1!=0) {
				power-=level1;
				MyPower1();
			}
		}
		else {
			if (power>=level2&&level2!=0) {
				power-=level2;
				MyPower2();
			}
		}
	}
	
	/**The first special ability the player can use when the players power>level1. leave level1 at 0 to remove access to this ability.*/
	public void MyPower1() {}
	/**The second ability, I might have these point to something like Game.powers.QuickFix();*/
	public void MyPower2() {}
	
	/**Returns the image location of the sprite sheet where this unit is located.*/
	public int[] DrawMe() {
		int imgx=0;
		int imgy=0;
		int[] loc = {imgx,imgy};
		return loc;
	}
}
