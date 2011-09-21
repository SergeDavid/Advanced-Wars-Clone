package terrain;

public class Water extends Base {
	public Water() {
		name = "Water";
		oldx = x = 1;
		oldy = y = 2;
		MultiTiled = true;
		walk = false;
		drive = false;
		swim = true;
	}
	public double speed() {return 1;}
	public double defense() {return 1;}
}
