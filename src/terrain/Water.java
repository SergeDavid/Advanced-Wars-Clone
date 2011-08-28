package terrain;

public class Water extends Base {
	public boolean walk() {return false;}
	public boolean drive() {return false;}
	public boolean swim() {return true;}
	public boolean fly() {return true;}
	public double speed() {return 1;}
	public double defense() {return 1;};
	public int x() {return 3;};
	public int y() {return 0;}
}
