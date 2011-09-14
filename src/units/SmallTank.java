package units;

public class SmallTank extends Base {

	public SmallTank(int owner, int xx, int yy, boolean active) {
		super(owner, xx, yy, active);
		name = "Small Tank";
		nick = "STnk";
		desc = "A small tank, very effective against lightly armored opponents.";
		legs = Move.TANK;
		speed = 6;
		img = 2;
	}
}
