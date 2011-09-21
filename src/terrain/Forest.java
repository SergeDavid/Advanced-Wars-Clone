package terrain;

public class Forest extends Base {
	public Forest() {
		name = "Forest";
		oldx = x = 4;
		oldy = y = 0;
	}
	public double speed() {return 1.1;}
	public double defense() {return 1.1;}
}
