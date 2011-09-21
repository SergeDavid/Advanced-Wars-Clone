package terrain;

public class Base {
	public int x = 0;
	public int y = 0;
	public String name = "Void";
	public boolean walk = true;
	public boolean drive = true;
	public boolean swim = false;
	public boolean fly = true;
	public int oldx = x;//Used for editor stuff
	public int oldy = y;
	
	public boolean MultiTiled = false;//Determines if ChangeTiles is called or not because the tile has multiple settings.
	
	public boolean building() {return false;}
	public double speed() {return 1;}//Path Cost based on 10 (-10 = almost none, +10 = a ton)
	public double defense() {return 1;};//Protection offered for those being shot at.
}
