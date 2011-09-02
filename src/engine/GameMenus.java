package engine;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

public class GameMenus extends JPanel implements ActionListener {
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
	JButton EndTurn = new JButton("EndTurn");
	JButton Resume = new JButton("Resume");
	JButton Quit = new JButton("Quit");
	
	//Confirmation
	JButton Yes = new JButton("Yes");
	JButton No = new JButton("No");
	
	//Unit Menu
	JButton Wait = new JButton("Wait");
	JButton Attack = new JButton("Attack");
	JButton Capture = new JButton("Capture");
	
	public GameMenus() {
		setBackground(new Color(80,80,80));
		
		//Main Pause Menu
		Resume.addActionListener(this);
		Save.addActionListener(this);
		Options.addActionListener(this);
		EndTurn.addActionListener(this);
		Quit.addActionListener(this);
		//Unit Menu Options
		Buy.addActionListener(this);
		Wait.addActionListener(this);
		Attack.addActionListener(this);
		Capture.addActionListener(this);
		//Yes/No
		Yes.addActionListener(this);
		No.addActionListener(this);
	}
	
	/**This one is for non-city menu's, for city menu's add the X and Y of said city.
	 * Acceptable Types include (Pause,YesNo,Barrack,Airport,Seaport)
	 * @param Type = The type of menu to open
	 */
	public void OpenMenu(String Type) {
		removeAll();
		Insets insets = Game.gui.getInsets();
		if (Type.equals("Pause")) {
			PauseMenu();}
		else if (Type.equals("YesNo")) {
			setBounds(320 + insets.left, 60 + insets.top, 120, 140);
			YesNoMenu();}
		Game.gui.add(this);
		Game.input.MenuHack=true;
	}
	/**Special setup for unit creation menu's*/
	public void OpenMenu(String Type, int xx, int yy) {
		removeAll();
		Insets insets = Game.gui.getInsets();
		setBounds(320 + insets.left, 60 + insets.top, 180, 256);
		x = xx;
		y = yy;
		if (Type.equals("Barrack")) {CityMenuGround();}
		else if (Type.equals("Seaport")) {CityMenuGround();}
		else if (Type.equals("Airport")) {CityMenuGround();}
		Game.gui.add(this);
		Game.input.MenuHack=true;
	}
	public void CloseMenu() {
		Game.gui.remove(this);
		Game.input.MenuHack=false;
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
		Resume.setMnemonic('R');
		Save.setBounds(10+insets.left, 30*1+10+insets.top, 100, 24);
		Options.setBounds(10+insets.left, 30*2+10+insets.top, 100, 24);
		EndTurn.setBounds(10+insets.left, 30*3+10+insets.top, 100, 24);
		Quit.setBounds(10+insets.left, 30*4+10+insets.top, 100, 24);
		
		add(Resume);
		add(Save);
		add(Options);
		add(EndTurn);
		add(Quit);
		
		insets = Game.gui.getInsets();
		setBounds(320 + insets.left, 60 + insets.top, 120, 30*5+20);
	}
	public void CityMenuGround() {
		Insets insets = getInsets();
		UnitModel.removeAllElements();
		//TODO: Add only units with the building integer that matches it's respective building. (air units = airbase, ground units = barracks)
		for (int i = 0; i < Game.displayU.size(); i++) {
			UnitModel.addElement(Game.displayU.get(i).name + " = $" + (Game.displayU.get(i).cost * Game.player.get(Game.btl.currentplayer).CostBonus));
		}
		Units.setSelectedIndex(0);
		Buy.setBounds(10+insets.left, 30*6+10+insets.top, 100, 24);
		Resume.setBounds(10+insets.left, 30*7+10+insets.top, 100, 24);
		Units.setBounds(10+insets.left, 10+insets.top, 160, 32*5+16);
		add(Resume);
		add(Buy);
		add(Units);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object s = e.getSource();
		if (s==Quit) {CloseMenu();Game.gui.LoginScreen();}
		else if (s==EndTurn) {Game.gui.gms.CloseMenu();Game.btl.EndTurn();}
		else if (s==Resume) {Game.gui.gms.CloseMenu();}
		else if (s==Buy) {
			Game.btl.Buyunit(Game.gui.gms.Units.getSelectedIndex(), Game.gui.gms.x, Game.gui.gms.y);
			Game.gui.gms.CloseMenu();
		}
		else if (s==Save) {Game.save.SaveGame();}
		Game.gui.requestFocusInWindow();
	}
}
