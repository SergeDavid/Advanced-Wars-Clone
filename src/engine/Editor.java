package engine;

import java.util.ArrayList;

public class Editor {
	int Type;
	int Player;//Displays the available placed units, buildings, and tiles
	int Tile;
	int Unit;
	int Building;
	
	public int selecty = 0;
	public int selectx = 0;
	public String mapname;
	
	public void StartEditor(String name, int width, int height) {
		Game.gui.removeAll();
		Game.GameState=Game.State.EDITOR;
		if (Game.error.showing) {Game.gui.add(Game.error);}
		
		Game.player = new ArrayList<players.Base>();
		Game.builds = new ArrayList<buildings.Base>();
		Game.units = new ArrayList<units.Base>();
		
		Game.view.Loc.x = 0;
		Game.view.Loc.y = 0;
		mapname = name;
		
		Game.map.MapSetup(width, height);
	}
	
	public void AssButton() {
		Game.map.map[selecty][selectx] = new terrain.Forest();
	}
	public void ButtButton() {
	}
}
