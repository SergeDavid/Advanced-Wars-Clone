package buildings;

import engine.Game;

public class Base {
	//Image and other info
	/**(y*width+x) = id so when saving it can place it in the correct location.*/
	public int editorid = 0;
	int img = 0;
	public String name = "Bacon";
	String desc = "Giggles";
	public boolean Locked;//Easy determination if a unit is standing on it or not. TODO: Install this or do something.
	public String Menu = "";//The menu this opens.
	enum Units {GROUND, AIR, WATER};
	Units unittype = Units.GROUND;
	
	//Health (How long until it is captured / destroyed, and how far along it is)
	public int maxhealth = 20;
	public int health = maxhealth;
	
	//owner's ID and team #
	public int owner;
	public int team = 0;
	
	//Location
	public int x;
	public int y;
	
	public Base(int owner,int xx, int yy) {
		//15 = Neutral, 12~14 are unused. (12 max players)
		System.out.println("Base level owner: " + owner);
		this.owner=owner;
		x=xx;
		y=yy;
		editorid = y * Game.map.width + x;
		img=0;
	}

	public void OpenMenu() {
		if (Menu!=null) {
			new menus.City(Menu, x, y);
		}
	}
	
	public void Capture(int hp, int winner) {//TODO: Fix this up to work properly
		//TODO: Animation trigger for capturing.
		health-=hp;
		if (health<=0) {
			System.out.println("Building Captured!");
			if (name.equals("Capital")) {
				Game.btl.CaptureCapital(x,y);
			}
			System.out.println("Herp Derp");
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
