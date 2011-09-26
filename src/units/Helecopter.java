package units;

public class Helecopter extends Base {
	public Helecopter(int owner, int xx, int yy, boolean active) {
		super(owner, xx, yy, active);
		name = "Helecopter";
		nick = "Hele";
		desc = "A fast reaction unit that kills things.";
		img = 4;
		speed = 8;
		legs = Move.FLY;
		ArmorType = Armor.AIR;
		building = "airport";
	}
}
