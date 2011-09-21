package players;

public class Sammi extends Base {
	
	public Sammi(boolean ai, int color, int bling) {
		super(ai, color, bling);
		name="Sammi";
		desc="Ground pounders for the win.";
		level1=50;
		level2=100;
		CaptureBonus=1.5;
	}
	public void MyPower1() {
		System.out.println(money + " : " + name + "'s power sucks it! D:");
	}
	public void MyPower2() {
		System.out.println(power + " : " + name + "'s power sucks it twice! D:");
	}
}
