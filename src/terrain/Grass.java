package terrain;

public class Grass extends Base {
	public Grass() {
		name = "Grass";
		oldx = x = 1;
		oldy = y = 0;
	}
	public double speed() {return 0.8;}
	public double defense() {return 0.9;}
}
