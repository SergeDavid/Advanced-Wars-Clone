package menus;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JTextArea;

import engine.Game;

/**
 * The end game menu that pops up, it pretty much only contains player data such as kills, captures, money, and win / lose
 * @author SergeDavid
 * @version 0.2
 */
public class EndBattle implements ActionListener {
	JButton Return = new JButton("Main Menu");
	JTextArea Names = new JTextArea();
	JTextArea Money = new JTextArea();
	JTextArea Kills = new JTextArea();
	JTextArea Loses = new JTextArea();
	
	public EndBattle() {
		PopulateStats();
		Point size = MenuHandler.PrepMenu(360,260);
		SetBounds(size);
		AddGui();
		AddListeners();
	}
	private void SetBounds(Point size) {
		Return.setBounds(size.x+16, size.y+16, 100, 24);
		Names.setBounds(size.x+16, size.y+56, 64, 180);
		Money.setBounds(size.x+(32+64)*1, size.y+56, 64, 180);
		Kills.setBounds(size.x+(32+64)*2, size.y+56, 64, 180);
		Loses.setBounds(size.x+(32+64)*3, size.y+56, 64, 180);
	}
	private void AddGui() {
		Game.gui.add(Return);
		Game.gui.add(Names);
		Game.gui.add(Money);
		Game.gui.add(Kills);
		Game.gui.add(Loses);
	}
	private void AddListeners() {
		Return.addActionListener(this);
	}

	private void PopulateStats() {
		String[] text = {"Players\n","Money\n","Kills\n","Loses\n"};
		for (players.Base ply : Game.player) {
			text[0]+="\n" + ply.name;
			text[1]+="\n" + ply.money;
			text[2]+="\n" + ply.kills;
			text[3]+="\n" + ply.loses;
		}
		Names.setText(text[0]);
		Money.setText(text[1]);
		Kills.setText(text[2]);
		Loses.setText(text[3]);
	}
	@Override public void actionPerformed(ActionEvent e) {
		Object s = e.getSource();
		if (s==Return) {
			MenuHandler.CloseMenu();
			Game.gui.LoginScreen();
		}
	}
}
