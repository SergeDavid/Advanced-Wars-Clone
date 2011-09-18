package terrain;

public class Water extends Base {
	public Water() {
		name = "Water";
		x = 3;
		y = 0;
	}
	public boolean walk() {return false;}
	public boolean drive() {return false;}
	public boolean swim() {return true;}
	public boolean fly() {return true;}
	public double speed() {return 1;}
	public double defense() {return 1;}
}
