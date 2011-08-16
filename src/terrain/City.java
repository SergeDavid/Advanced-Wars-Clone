package terrain;

public class City extends Base implements Inter {
	public boolean building() {return true;}
	public boolean walk() {return true;}
	public boolean drive() {return true;}
	public boolean swim() {return false;}
	public boolean fly() {return true;}
	public int speed() {return 0;}
	public int defense() {return 0;};
	public int x() {return 0;};
	public int y() {return 0;}
}
