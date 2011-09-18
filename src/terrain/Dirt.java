package terrain;

public class Dirt extends Base {
	public Dirt() {
		name = "Dirt";
	}
	public boolean walk() {return true;}
	public boolean drive() {return true;}
	public boolean swim() {return false;}
	public boolean fly() {return true;}
	public double speed() {return 1;}
	public double defense() {return 1;}
}
