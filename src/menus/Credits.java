package menus;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JTextArea;

import engine.Game;

public class Credits implements ActionListener {
	
	String[] titles = {"Dev Team","TITLE HERE","Special Mentions"};
	String[] list1 = {"Serge-David"};//Main Developers
	String[] list2 = {"hithere"};//People who've helped
	String[] list3 = {"Google","Gamers like you"};
	
	JTextArea Menu = new JTextArea();
	JButton Return = new JButton("Return");
	
	public Credits() {
		Point size = MenuHandler.PrepMenu(180,300);
		Menu.setText(CreditList());
		Menu.setEditable(false);
		SetBounds(size);
		AddGui();
		AddListeners();
	}
	private void SetBounds(Point size) {
		Menu.setBounds(size.x+0, size.y+0, 180, 276);
		Return.setBounds(size.x+40, size.y+276, 100, 24);
	}
	private void AddGui() {
		Game.gui.add(Menu);
		Game.gui.add(Return);
	}
	private void AddListeners() {
		Return.addActionListener(this);
	}
	
	private String CreditList() {
		String blah = " " + titles[0];
		for (String hey : list1) {blah += "\n      " + hey;}
		blah += "\n " + titles[1];
		for (String hey : list2) {blah += "\n      " + hey;}
		blah += "\n " + titles[2];
		for (String hey : list3) {blah += "\n      " + hey;}
		return blah;
	}
	
	@Override public void actionPerformed(ActionEvent e) {
		Object s = e.getSource();
		if (s==Return) {
			MenuHandler.CloseMenu();
			Game.gui.LoginScreen();
		}
	}	
}
