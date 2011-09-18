package terrain;

public class Mountain extends Base {
	public Mountain() {
		name = "Mountain";
		x = 2;
		y = 0;
	}
	public boolean walk() {return true;}
	public boolean drive() {return false;}
	public boolean swim() {return false;}
	public boolean fly() {return true;}
	public double speed() {return 1.2;}
	public double defense() {return 1.5;}
}
