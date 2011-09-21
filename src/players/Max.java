package players;

public class Max extends Base {
	
	public Max(boolean ai, int color, int bling) {
		super(ai, color, bling);
		name="Max";
		desc="Strong tanks, weak artillery.";
		level1=50;
		level2=100;
		WeaponBonus=1.2;
	}
	public void MyPower1() {
		System.out.println(money + " : " + name + "'s power sucks it! D:");
	}
	public void MyPower2() {
		System.out.println(power + " : " + name + "'s power sucks it twice! D:");
	}
}
