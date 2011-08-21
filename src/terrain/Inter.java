package terrain;

/**Interface for tiles so I can keep everything running smoothly*/
public interface Inter {
	boolean building();
	boolean walk();
	boolean drive();
	boolean swim();
	boolean fly();
	double speed();
	int defense();
	int x();
	int y();
}
