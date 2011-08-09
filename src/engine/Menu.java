package engine;

import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Menu extends JPanel {
	private static final long serialVersionUID = -7953759133984304287L;
	//In game Options
	JButton Help = new JButton("Help");
	JButton Save = new JButton("Save");
	JButton Options = new JButton("Options");
	JButton Resume = new JButton("Resume");
	JButton Quit = new JButton("Quit");
	
	//Confirmation
	JButton Yes = new JButton("Yes");
	JButton No = new JButton("No");
	
	//unused
	JButton Quit1 = new JButton("Quit");
	JButton Quit2= new JButton("Quit");
	JButton Quit3 = new JButton("Quit");
	
	//Menu lengths
	int Paused = 5;
	int YesNo = 2;
	
	public Menu(String Type) {
		if (Type.equals("Pause")) {
			Insets insets = Game.gui.getInsets();
			setBounds(320 + insets.left, 60 + insets.top, 120, 140);
			insets = getInsets();
			Resume.setBounds(10+insets.left, 10+insets.top, 100, 24);
			Save.setBounds(10+insets.left, 30*1+10+insets.top, 100, 24);
			Options.setBounds(10+insets.left, 30*2+10+insets.top, 100, 24);
			Quit.setBounds(10+insets.left, 30*3+10+insets.top, 100, 24);
			
			add(Resume);
			add(Save);
			add(Options);
			add(Quit);
		}
		else {
			add(Resume);
		}
	}
}
