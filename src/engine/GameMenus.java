package engine;

import java.awt.Color;
import java.awt.Insets;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

public class GameMenus extends JPanel {
	private static final long serialVersionUID = -7953759133984304287L;
	//TODO: Redesign the menu's to be set around and handled better.
	//TODO: Include unit picture/description/statistics when a unit is selected
	//TODO: When loading units into the lists, grab only units that are connected to that menu.
	//TODO: Hook the menu up to the keyboard.
	
	DefaultListModel UnitModel = new DefaultListModel();
	JList Units = new JList(UnitModel);
	JButton Buy = new JButton("Buy");
	int x;
	int y;
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
	
	public GameMenus() {
		setBackground(new Color(80,80,80));
	}
	
	/**This one is for non-city menu's, for city menu's add the X and Y of said city.
	 * Acceptable Types include (Pause,YesNo,Barrack,Airport,Seaport)
	 * @param Type = The type of menu to open
	 */
	public void OpenMenu(String Type) {
		Insets insets = Game.gui.getInsets();
		if (Type.equals("Pause")) {
			setBounds(320 + insets.left, 60 + insets.top, 120, 140);
			PauseMenu();}
		else if (Type.equals("YesNo")) {
			setBounds(320 + insets.left, 60 + insets.top, 120, 140);
			YesNoMenu();}
		Game.gui.add(this);
	}
	/**Special setup for unit creation menu's*/
	public void OpenMenu(String Type, int xx, int yy) {
		Insets insets = Game.gui.getInsets();
		setBounds(320 + insets.left, 60 + insets.top, 180, 256);
		x = xx;
		y = yy;
		if (Type.equals("Barrack")) {CityMenuGround();}
		else if (Type.equals("Seaport")) {CityMenuGround();}
		else if (Type.equals("Airport")) {CityMenuGround();}
		Game.gui.add(this);
	}
	public void CloseMenu() {
		Game.gui.remove(this);
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
	public void CityMenuGround() {
		Insets insets = getInsets();
		UnitModel.removeAllElements();
		//TODO: Add only units with the building integer that matches it's respective building. (air units = airbase, ground units = barracks)
		for (int i = 0; i < Game.displayU.size(); i++) {
			UnitModel.addElement(Game.displayU.get(i).name + " = $" + (Game.displayU.get(i).cost * Game.player.get(Game.btl.currentplayer).CostBonus));
		}
		Buy.setBounds(10+insets.left, 30*6+10+insets.top, 100, 24);
		Quit.setBounds(10+insets.left, 30*7+10+insets.top, 100, 24);
		Units.setBounds(10+insets.left, 10+insets.top, 160, 32*5+16);
		add(Quit);
		add(Buy);
		add(Units);
	}
}
