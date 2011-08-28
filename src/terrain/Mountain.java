package terrain;

public class Mountain extends Base {
	public boolean walk() {return true;}
	public boolean drive() {return false;}
	public boolean swim() {return false;}
	public boolean fly() {return true;}
	public double speed() {return 1.2;}
	public double defense() {return 2;};
	public int x() {return 2;};
	public int y() {return 0;}
}
