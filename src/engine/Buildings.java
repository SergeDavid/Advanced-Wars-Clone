package engine;

public class Buildings {
	int maxhealth = 20;
	int health = maxhealth;
	int building;
	int type;
	/**The type of building it is with*/
	int owner;
	int team = 0;
	int x;
	int y;
	
	/**Owned building.*/
	public Buildings(int owner,int xx, int yy) {
		this.owner=owner;
		team=Game.player.get(owner).team;
		x=xx;y=yy;
	}
	/**Neutral team building.*/
	public Buildings(int xx, int yy) {
		x=xx;y=yy;
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
		default:Game.player.get(Game.btl.currentplayer).Buyunit(x,y);break;
		}
	}
	
	public int[] DrawMe() {
		int[] loc = {type+1,team+1};
		return loc;
	}

}
