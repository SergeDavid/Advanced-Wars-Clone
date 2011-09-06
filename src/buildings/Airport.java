package buildings;

public class Airport extends Base {

	public Airport(int owner, int team, int xx, int yy) {
		super(owner, team, xx, yy);
		name="Airport";
		desc="Creates Air units.";
		img = 4;
		HasAMenu = true;
	}

	public void OpenMenu() {
		new menus.City("Airport", x, y);
	}

}
