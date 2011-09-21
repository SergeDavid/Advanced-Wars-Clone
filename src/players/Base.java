package players;

import engine.Game;

public class Base {
	//Basic Info
	public String name;
	public String desc;
	public int team;
	public int money;
	public boolean npc;
	public boolean defeated;
	
	//Modifiers (unit cost/damage/defense)
	/**Percentage offset for how much a unit costs to build with 1.0 = 100% (0.5 = half / 2.0 = double)*/
	public double CostBonus = 1.0;
	public double ArmorBonus = 1.0;//Currently just an overall bonus, TODO: Add in specific bonuses (foot, vehicle, tank, artillery, etc.)
	public double WeaponBonus = 1.0;
	public double CaptureBonus = 1.0;

	//Special Power
	/**The current power level, max is level2.*/
	public int power;
	/**The amount of power points needed to achieve the first ability. Set to 0 for it to not exist*/
	public int level1;
	/**The amount of power points needed to achieve the second ability.*/
	public int level2;
	
	//Unit selection and unit end game statistics (total kills / loses)
	public boolean unitselected;//If the character has selected a unit or not. (using this instead of selectedunit=-1)
	public int selectedunit = 0;//Which unit is currently selected to use.
	public int usedunits;//How many units does the player have left until turn ends. Currently unused.
	public int kills;
	public int loses;
	
	//Player's image in the sprite sheet and the current looking location.
	int imgx;
	int imgy;
	public int selectx = 2;
	public int selecty = 2;
	
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
	
	public void Powerup(double damage, boolean defending) {
		power += (defending) ? damage/3 : damage ;
		if (power>level2) {power=level2;}
	}
	
	//TODO: Remove the UserPower thing if I can get the direct MyPower1/2 to work correctly this time.
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
	
	public void Cancle() {
		if (unitselected) {
			Game.units.get(selectedunit).cancle();
			unitselected=false;
		}
	}
	
	public boolean FindUnit() {
		for (int i=0; i<Game.units.size(); i++) {
			if (Game.units.get(i).x==selectx&&Game.units.get(i).y==selecty) {
				if (!Game.units.get(i).moved) {Game.units.get(i).Pathing();}
				selectedunit=i;
				unitselected=true;
				return true;
			}
		}
		return false;
	}
	public boolean FindCity() {//Finds a city to use for menu pickings.
		for (buildings.Base bld : Game.builds) {
			if (bld.x==selectx&&bld.y==selecty&&bld.owner==Game.btl.currentplayer) {
				bld.OpenMenu();
				return true;
			}
		}
		return false;
	}
	
	/**Returns the image location of the sprite sheet where this unit is located.*/
	public int[] DrawMe() {
		int imgx=0;
		int imgy=0;
		int[] loc = {imgx,imgy};
		return loc;
	}
}
