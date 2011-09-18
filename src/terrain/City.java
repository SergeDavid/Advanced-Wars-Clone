package terrain;

public class City extends Base {
	public City() {
		name = "City";
	}
	public boolean building() {return true;}
	public boolean walk() {return true;}
	public boolean drive() {return true;}
	public boolean swim() {return false;}
	public boolean fly() {return true;}
	public double speed() {return 1;}
	public double defense() {return 1.3;}
}
