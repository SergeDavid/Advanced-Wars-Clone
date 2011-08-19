package buildings;

public class Barracks extends Base {

	public Barracks(int owner, int team, int xx, int yy) {
		super(owner, team, xx, yy);
		name="Capital";
		desc="Creates ground units.";
		img = 4;
	}

	public void OpenMenu() {}
}
