package terrain;

public class Mountain extends Base implements Inter {
	public boolean walk() {return true;}
	public boolean drive() {return false;}
	public boolean swim() {return false;}
	public boolean fly() {return true;}
	public double speed() {return 1.3;}
	public int defense() {return 0;};
	public int x() {return 2;};
	public int y() {return 0;}
}
