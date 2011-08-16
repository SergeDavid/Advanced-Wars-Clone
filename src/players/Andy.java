package players;

public class Andy extends Base {
	
	public Andy(boolean ai, int color, int bling) {
		super(ai, color, bling);
		name="Andy";
		desc="A young man who smells of cheese.";
		level1=50;
		level2=100;
	}
	public void MyPower1() {
		System.out.println(money + " : " + name + "'s power sucks it! D:");
	}
	public void MyPower2() {
		System.out.println(power + " : " + name + "'s power sucks it twice! D:");
	}
}
