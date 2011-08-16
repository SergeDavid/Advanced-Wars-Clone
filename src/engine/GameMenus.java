package engine;

import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JPanel;

public class GameMenus extends JPanel {
	private static final long serialVersionUID = -7953759133984304287L;
	//StartMenu
	JButton Help = new JButton("Help");
	JButton Save = new JButton("Save");
	JButton Options = new JButton("Options");
	JButton Resume = new JButton("Resume");
	JButton Quit = new JButton("Quit");
	
	//Confirmation
	JButton Yes = new JButton("Yes");
	JButton No = new JButton("No");
	
	//Unit Menu
	JButton Wait = new JButton("Wait");
	JButton Attack = new JButton("Attack");
	JButton Capture = new JButton("Capture");
	
	//unused
	JButton Quit1 = new JButton("Quit");
	JButton Quit2= new JButton("Quit");
	JButton Quit3 = new JButton("Quit");
	
	public GameMenus(String Type) {
		Insets insets = Game.gui.getInsets();
		if (Type.equals("Pause")) {
			setBounds(320 + insets.left, 60 + insets.top, 120, 140);
			PauseMenu();}
		else if (Type.equals("YesNo")) {
			setBounds(320 + insets.left, 60 + insets.top, 120, 140);
			YesNoMenu();}
		
		//These are the unit creation menu's
		else if (Type.equals("Barrack")) {}
		else if (Type.equals("Seaport")) {}
		else if (Type.equals("Airport")) {}
		Game.gui.add(this);
	}
	private void YesNoMenu() {
		Insets insets = getInsets();
		Yes.setBounds(10+insets.left, 10+insets.top, 100, 24);
		No.setBounds(10+insets.left, 30*1+10+insets.top, 100, 24);
		add(Yes);
		add(No);
	}
	private void PauseMenu() {
		Insets insets = getInsets();
		Resume.setBounds(10+insets.left, 10+insets.top, 100, 24);
		Save.setBounds(10+insets.left, 30*1+10+insets.top, 100, 24);
		Options.setBounds(10+insets.left, 30*2+10+insets.top, 100, 24);
		Quit.setBounds(10+insets.left, 30*3+10+insets.top, 100, 24);
		
		add(Resume);
		add(Save);
		add(Options);
		add(Quit);
	}
	public void CreateAirMenu() {
		
	}
}
