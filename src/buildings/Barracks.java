package buildings;

public class Barracks extends Base {

	public Barracks(int owner, int team, int xx, int yy) {
		super(owner, team, xx, yy);
		name="Barracks";
		desc="Creates ground units.";
		img = 2;
		HasAMenu = true;
	}

	public void OpenMenu() {
		new menus.City("Barrack", x, y);
	}
}
