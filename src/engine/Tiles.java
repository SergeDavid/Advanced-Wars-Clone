package engine;

import java.util.List;
import java.util.ArrayList;

public class Tiles {
	public List<Base> t = new ArrayList<Base>();
	public Tiles() {
		t.add(new Dirt());
		t.add(new Grass());
		t.add(new Road());
		t.add(new Water());
	}
	public class Base implements Inter {
		public boolean walk() {return false;}//Can you walk on it?
		public boolean drive() {return false;}//Can you drive on it?
		public boolean swim() {return false;}//Can you swim on it?
		public boolean fly() {return true;}//Can you fly on it?
		public int speed() {return 0;}//Path Cost based on 10 (-10 = almost none, +10 = a ton)
		public int defense() {return 0;};//Protection offered for those being shot at.
		public int x() {return 0;};
		public int y() {return 0;}
	}
	public interface Inter {
		boolean walk();
		boolean drive();
		boolean swim();
		boolean fly();
		int speed();
		int defense();
		int x();
		int y();
	}
	public class Dirt extends Base {
		public boolean walk() {return true;}
		public boolean drive() {return true;}
		public boolean swim() {return false;}
		public boolean fly() {return true;}
		public int speed() {return 0;}
		public int defense() {return 0;};
		public int x() {return 0;};
		public int y() {return 0;}
	}
	public class Grass extends Base {
		public boolean walk() {return true;}
		public boolean drive() {return true;}
		public boolean swim() {return false;}
		public boolean fly() {return true;}
		public int speed() {return 0;}
		public int defense() {return 0;};
		public int x() {return 1;};
		public int y() {return 0;}
	}
	public class Road extends Base {
		public boolean walk() {return true;}
		public boolean drive() {return true;}
		public boolean swim() {return false;}
		public boolean fly() {return true;}
		public int speed() {return 5;}
		public int defense() {return 0;};
		public int x() {return 2;};
		public int y() {return 0;}
	}
	public class Water extends Base {
		public boolean walk() {return false;}
		public boolean drive() {return false;}
		public boolean swim() {return true;}
		public boolean fly() {return true;}
		public int speed() {return 0;}
		public int defense() {return 0;};
		public int x() {return 3;};
		public int y() {return 0;}
	}
}
