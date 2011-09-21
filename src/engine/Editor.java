package engine;

import java.util.ArrayList;

public class Editor {
	
	public enum Type {TILE, UNIT, CITY};
	public Type pick = Type.TILE;
	public int id = 0;
	
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
		switch (pick) {
		case TILE://Done
			if (Game.map.map[selecty][selectx].building() == true) {RemoveBuilding();}
			Game.map.map[selecty][selectx] = Game.map.getTile(id);
		break;
		case CITY:
			//Put city in the proper location by going through the list and looking for correct Y and X locations.
			Game.builds.add(new buildings.Town(0, selectx, selecty));
			Game.map.map[selecty][selectx] = new terrain.City();
		break;
		case UNIT:
			//Add setup for unit removal.
			Game.map.map[selecty][selectx] = new terrain.Forest();
		break;
		}
	}

	public void ButtButton() {
		new menus.EditorStuff();
	}
	
	private void RemoveBuilding() {
		for (int i = 0; i < Game.builds.size(); i++) {
			if (Game.builds.get(i).x == selectx && Game.builds.get(i).y == selecty) {
				Game.builds.remove(i);
			}
		}
	}
}
