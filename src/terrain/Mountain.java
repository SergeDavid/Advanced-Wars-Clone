package terrain;

public class Mountain extends Base {
	public Mountain() {
		name = "Mountain";
		oldx = x = 2;
		oldy = y = 0;
		drive = false;
	}
	public double speed() {return 1.2;}
	public double defense() {return 1.5;}
}
