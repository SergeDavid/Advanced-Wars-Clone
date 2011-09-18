package terrain;

public class Grass extends Base {
	public Grass() {
		name = "Grass";
		x = 1;
		y = 0;
	}
	public boolean walk() {return true;}
	public boolean drive() {return true;}
	public boolean swim() {return false;}
	public boolean fly() {return true;}
	public double speed() {return 0.8;}
	public double defense() {return 0.9;}
}
