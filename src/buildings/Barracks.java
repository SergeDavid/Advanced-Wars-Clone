package buildings;

public class Barracks extends Base {

	public Barracks(int owner, int xx, int yy) {
		super(owner, xx, yy);
		name="Barracks";
		desc="Creates ground units.";
		img = 2;
		Menu = "barracks";
	}
}
