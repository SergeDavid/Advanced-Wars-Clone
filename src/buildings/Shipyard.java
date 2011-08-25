package buildings;

import engine.Game;

public class Shipyard extends Base {

	public Shipyard(int owner, int team, int xx, int yy) {
		super(owner, team, xx, yy);
		name="Capital";
		desc="Creates water units.";
		img = 3;
	}

	public void OpenMenu() {
		Game.gui.gms.OpenMenu("Seaport", x, y);
	}

}
