package engine;

import java.util.List;
import java.util.ArrayList;

/**This is going to become the repository of classes to use for the different things such as pulling building stats and what not.*/
public class Tiles {
	//TODO: Find a good way of handling these lists.
	
	/**The list of buildings in the game.*/
	public List<buildings.Base> b = new ArrayList<buildings.Base>();
	/**The list of units in the game.*/
	public List<units.Base> u = new ArrayList<units.Base>();
	/**The list of commanders in the game.*/
	public List<players.Base> c = new ArrayList<players.Base>();
	public Tiles() {
		LoadCities();
		LoadUnits();
		LoadCOs();
	}
	private void LoadCities() {
		b.add(new buildings.Base(0,0,0,0));
	}
	private void LoadUnits() {
		
	}
	/**Loads all of the characters you can play as in the game.*/
	private void LoadCOs() {
		c.add(new players.Base(false,1,100));//Game.btl.startingmoney
		c.add(new players.Andy(false,1,111));
		c.get(1).power=100;
		c.get(1).UsePower(true);
		c.get(1).UsePower(false);
	}
}
