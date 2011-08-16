package terrain;

public class Road extends Base implements Inter {
	public boolean walk() {return true;}
	public boolean drive() {return true;}
	public boolean swim() {return false;}
	public boolean fly() {return true;}
	public int speed() {return 5;}
	public int defense() {return 0;};
	public int x() {return 2;};
	public int y() {return 0;}
}
