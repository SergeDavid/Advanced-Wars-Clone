package terrain;

public class Water extends Base {
	public Water() {
		name = "Water";
		x = 1;
		y = 2;
	}
	public boolean walk() {return false;}
	public boolean drive() {return false;}
	public boolean swim() {return true;}
	public boolean fly() {return true;}
	public double speed() {return 1;}
	public double defense() {return 1;}
}
