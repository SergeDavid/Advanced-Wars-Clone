package menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JTextArea;

import engine.Game;

public class EndBattle implements ActionListener {
	JButton Return = new JButton("Main Menu");
	JTextArea Names = new JTextArea();
	JTextArea Money = new JTextArea();
	JTextArea Kills = new JTextArea();
	JTextArea Loses = new JTextArea();
	
	public EndBattle() {
		Return.setBounds(16, 16, 100, 24);
		Names.setBounds(16,56,64,180);
		Money.setBounds((32+64)*1,56,64,180);
		Kills.setBounds((32+64)*2,56,64,180);
		Loses.setBounds((32+64)*3,56,64,180);

		Return.addActionListener(this);
		
		Show();
	}
	public void Show() {
		Game.gms.OpenMenu(360,260);
		PopulateStats();
		Game.gms.add(Return);
		Game.gms.add(Names);
		Game.gms.add(Money);
		Game.gms.add(Kills);
		Game.gms.add(Loses);
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
			Game.gms.CloseMenu();
			Game.gui.LoginScreen();
		}
	}
}
