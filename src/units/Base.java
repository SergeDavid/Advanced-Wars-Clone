package units;

//TODO: Refracter this to work here.
public class Base {
	//TODO: Find out if it is just easier to reference the base image sprite array instead of saving one for each sprite.
	int sprite = 32;
	String name = "Missing No.";
	String desc = "Unknown description present.";
	int owner;//What player owns it.
	int type;
	

	//life settings
	boolean dead;
	int maxhp = 100;
	int health = maxhp;
	boolean moved;
	boolean acted;
	
	//Location
	int x;
	int y;
	int oldx;
	int oldy;
	boolean[] atkrange = {false,true,false,true,false,true,false,true,false};
	boolean[] movrange = {
			false,false,true,false,false,
			false,true,true,true,false,
			true,true,false,true,true,
			false,true,true,true,false,
			false,false,true,false,false};
	int speed = 2;
	//TODO: Generate an array of available destinations to use in finding range.
	
	//Battle Settings
	double attack = 100;//Base damage done to others
	double defense = 60;//Base armor for taking damage
	//TODO: Add in type modifiers for damage and attack.
	//TODO: Generate an array of attackable locations.
	
	/** I need to add what kind of unit is being constructed or split it into extended classes.
	 * 
	 * @param owner = What player owns this unit.
	 * @param xx = The X location of the unit.
	 * @param yy = The Y location of the unit.
	 */
	public Base(int owner, int xx,int yy) {
		this.owner=owner;
		oldx = x = xx;
		oldy = y = yy;
		name = "Infantry";
		acted=true;moved=true;
	}

	/**Returns the image location of the sprite sheet where this unit is located.*/
	public int[] DrawMe() {
		int[] loc = {type,0};
		if (acted) {loc[1]++;}
		if (owner%2==1) {loc[1]+=2;}
		return loc;
	}
}
