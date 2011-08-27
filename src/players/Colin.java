package players;

public class Colin extends Base {
	
	public Colin(boolean ai, int color, int bling) {
		super(ai, color, bling);
		name="Colin";
		desc="Cheap units, weak units.";
		level1=50;
		level2=100;
		CostBonus=0.8;
	}
	public void MyPower1() {
		System.out.println(money + " : " + name + "'s power sucks it! D:");
	}
	public void MyPower2() {
		System.out.println(power + " : " + name + "'s power sucks it twice! D:");
	}
}
