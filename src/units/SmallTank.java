package units;

public class SmallTank extends Base {

	public SmallTank(int owner, int xx, int yy, boolean active) {
		super(owner, xx, yy, active);
		name = "Small Tank";
		nick = "STnk";
		desc = "A small tank.";
		MovType = MovMob;
		speed = 6;
		defense = 80;
		img = 2;
	}
}
