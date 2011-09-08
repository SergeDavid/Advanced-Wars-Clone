package units;

public class Artillery extends Base {

	public Artillery(int owner, int xx, int yy, boolean active) {
		super(owner, xx, yy, active);
		name = "Artillery";
		nick = "Art";
		desc = "A ranged unit.";
		MoveAndShoot = false;
		img = 0;
		speed = 5;
		MinAtkRange = 2;
		MaxAtkRange = 4;
	}
}
