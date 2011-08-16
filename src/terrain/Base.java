package terrain;

public class Base implements Inter {
	public boolean building() {return false;}
	public boolean walk() {return false;}//Can you walk on it?
	public boolean drive() {return false;}//Can you drive on it?
	public boolean swim() {return false;}//Can you swim on it?
	public boolean fly() {return true;}//Can you fly on it?
	public int speed() {return 0;}//Path Cost based on 10 (-10 = almost none, +10 = a ton)
	public int defense() {return 0;};//Protection offered for those being shot at.
	public int x() {return 0;};
	public int y() {return 0;}
}
