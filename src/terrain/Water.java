package terrain;

public class Water extends Base implements Inter {
	public boolean walk() {return false;}
	public boolean drive() {return false;}
	public boolean swim() {return true;}
	public boolean fly() {return true;}
	public int speed() {return 0;}
	public int defense() {return 0;};
	public int x() {return 3;};
	public int y() {return 0;}
}
