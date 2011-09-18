package terrain;

public class Road extends Base {
	public Road() {
		name = "Road";
		x = 5;
		y = 0;
	}
	public boolean walk() {return true;}
	public boolean drive() {return true;}
	public boolean swim() {return false;}
	public boolean fly() {return true;}
	public double speed() {return 1.1;}
	public double defense() {return 1;}
}
