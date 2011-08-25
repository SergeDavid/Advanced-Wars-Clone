package buildings;

import engine.Game;

public class Airport extends Base {

	public Airport(int owner, int team, int xx, int yy) {
		super(owner, team, xx, yy);
		name="Capital";
		desc="Creates Air units.";
		img = 4;
	}

	public void OpenMenu() {
		Game.gui.gms.OpenMenu("Airport", x, y);
	}

}
