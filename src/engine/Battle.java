package engine;

/**Put the game stuff in here so all I have to do is end/start this to make a game work or not.*/
public class Battle {
	int totalplayers = 2;
	int currentplayer = 0;
	final int maxplayers = 2;
	
	//Game settings
	boolean FogOfWar;
	int startingmoney = 10;//How much you start with each round.
	int buildingmoney = 10;//How much each building provides.
	
	//Winning condition settings	
	
	public Battle() {
		//TODO: Load map from map files into Game.Map.map
		Game.player.removeAll(null);
		Game.player.add(new Player(true,1,0));
		Game.player.add(new Player(true,2,0));
		MapParser map = new MapParser();
		map.decode("Basic");
	}
	
	public void EndTurn() {
		Player ply = Game.player.get(currentplayer);
		for (Character unit : ply.units) {
			unit.acted=false;
			unit.moved=false;
		}
		currentplayer++;
		if (currentplayer>=totalplayers) {currentplayer=0;}
		ply = Game.player.get(currentplayer);
		ply.money+=buildingmoney*Buildingcount(currentplayer);
	}
	
	private int Buildingcount(int owner) {
		int total = 0;
		for (Buildings bld : Game.builds) {
			if (bld.owner==owner) {total++;}
		}
		return total;
	}
}
