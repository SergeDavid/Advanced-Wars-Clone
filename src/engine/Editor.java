package engine;

import java.util.ArrayList;

public class Editor {
	
	public enum Type {TILE, UNIT, CITY};
	public Type pick = Type.TILE;
	public int id = 0;
	public int owner = 15;
	public boolean holding = false;
	public boolean moved = false; //This keeps you from building a ton of cities in the same spot
	
	public int selecty = 0;
	public int selectx = 0;
	public String mapname;
	
	public void StartEditor(String name, int height, int width) {
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
		Game.btl.totalplayers = 4;//HACK
	}
	
	public void AssButton() {
		switch (pick) {
		case TILE://Done
			if (Game.map.map[selecty][selectx].building() == true) {RemoveBuilding();}
			Game.map.map[selecty][selectx] = Game.map.getTile(id);
		break;
		case CITY:
			AddBuilding();
			Game.map.map[selecty][selectx] = new terrain.City();
		break;
		case UNIT:
			Game.units.add(Game.list.CreateUnit(id, 0, selectx, selecty, false));//Change 0 to player
		break;
		}
	}

	public void ButtButton() {
		new menus.EditorStuff();
	}
	
	private void AddBuilding() {
		moved = false;//Keeps game from generating lots of useless buildings.
		
		int newbld = (Game.map.width * selecty) + selectx;//editor id of the new city (before it is made)
		
		for (int i = 0; i < Game.builds.size(); i++) {//Cleans out any city at the current X and Y locations using their editorid.
			if (Game.builds.get(i).editorid == newbld) {
				Game.builds.remove(i);
			}
		}
		System.out.println("Editor level owner: " + owner);
		Game.builds.add(Game.list.CreateCity(owner, selectx, selecty, id));
	}
	private void RemoveBuilding() {
		for (int i = 0; i < Game.builds.size(); i++) {
			if (Game.builds.get(i).x == selectx && Game.builds.get(i).y == selecty) {
				Game.builds.remove(i);
			}
		}
	}

	public void ChangePlayer(boolean right) {
		if (right) {
			owner++;
			if (owner>4) {owner=0;}
		}
		else {
			owner--;
			if (owner<0) {owner=4;}
		}
		new menus.EditorStuff();
	}
}
