package engine;
//TODO: Setup this class as the controller for buildings.
//TODO: Think about expanding building types from x to xx for a ton of different kinds.
/** The different types of buildings list.
 * 0) Capital (Win game condition, optional, can have more then one for interesting results)
 * 1) Town (Revenue base)
 * 2) Barracks (Land unit)
 * 3) Airport (Air based units)
 * 4) Docks (Sea units)
 * 5)
 * 6)
 * 7)
 * 8)
 * 9)
 * 10)
 * 11)
 * 12)
 * 13)
 * 14)
 * 15)*/
public class Buildings {
	int maxhealth = 20;
	int health = maxhealth;
	int type;
	/**The type of building it is with*/
	int owner;
	int team = 0;
	int x;
	int y;
	
	/**
	 * 
	 * @param owner = 0-11 are players, 15 is neutral, 12-14 are unused.
	 * @param xx = X location
	 * @param yy = Y location
	 * @param type = Building type
	 */
	public Buildings(int owner,int xx, int yy, int type) {
		this.owner=owner;
		if (owner>11) {
			this.owner=15;
			team=0;
		}
		else {
			team=Game.player.get(owner).team;
		}
		x=xx;
		y=yy;
	}

	public void Capture(int hp, int winner) {
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
	
	public void Action() {
		switch (type) {
		
		//case 0:break;
		default:Game.btl.Buyunit(x,y);break;
		}
	}
	
	public int[] DrawMe() {
		int[] loc = {type+1,team+1};
		return loc;
	}

}
