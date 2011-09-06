package buildings;

import engine.Game;

public class Base {
	//Image and other info
	int img = 0;
	public String name = "Bacon";
	String desc = "Giggles";
	public boolean Locked;//Easy determination if a unit is standing on it or not. TODO: Install this or do something.
	public boolean HasAMenu = false;//TODO: Redesign this and the open menu area for better effect.
	
	//Health (How long until it is captured / destroyed, and how far along it is)
	int maxhealth = 20;
	public int health = maxhealth;
	
	//owner's ID and team #
	public int owner;
	public int team = 0;
	
	//Location
	public int x;
	public int y;
	
	public Base(int owner,int team,int xx, int yy) {
		//15 = Neutral, 12~14 are unused. (12 max players)
		this.owner=owner;
		this.team=team;
		x=xx;
		y=yy;
		img=0;
	}

	public void OpenMenu() {}
	
	public void Capture(int hp, int winner) {
		//TODO: Animation trigger for capturing.
		health-=hp;
		if (health<=0) {
			System.out.println("Building Captured!");
			owner=winner;
			team=Game.player.get(winner).team;
			health=maxhealth;
		}
		else {
			System.out.println("Building hp : " + health);
		}
		
	}
	
	public int[] DrawMe() {
		int[] loc = {img,team};
		return loc;
	}
}
