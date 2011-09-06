package menus;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import engine.Game;

public class Pause implements ActionListener {
	
	JButton Help = new JButton("Help");
	JButton Save = new JButton("Save");
	JButton Options = new JButton("Options");
	JButton EndTurn = new JButton("EndTurn");
	JButton Resume = new JButton("Resume");
	JButton Quit = new JButton("Quit");
	
	public Pause() {
		Insets insets = Game.gms.getInsets();
		Resume.setBounds(10+insets.left, 10+insets.top, 100, 24);
		Save.setBounds(10+insets.left, 30*1+10+insets.top, 100, 24);
		Options.setBounds(10+insets.left, 30*2+10+insets.top, 100, 24);
		EndTurn.setBounds(10+insets.left, 30*3+10+insets.top, 100, 24);
		Quit.setBounds(10+insets.left, 30*4+10+insets.top, 100, 24);
		
		//Main Pause Menu
		Resume.addActionListener(this);
		Save.addActionListener(this);
		Options.addActionListener(this);
		EndTurn.addActionListener(this);
		Quit.addActionListener(this);
		
		Show();
	}
	public void Show() {
		Game.gms.OpenMenu(120,180);
		Game.gms.add(Resume);
		Game.gms.add(Save);
		Game.gms.add(Options);
		Game.gms.add(EndTurn);
		Game.gms.add(Quit);
	}
	@Override public void actionPerformed(ActionEvent e) {
		Object s = e.getSource();
		if (s==Quit) {
			Game.gms.CloseMenu();
			Game.gui.LoginScreen();
		}
		else if (s==EndTurn) {
			Game.gms.CloseMenu();
			Game.btl.EndTurn();
		}
		else if (s==Resume) {Game.gms.CloseMenu();}
		else if (s==Save) {Game.save.SaveGame();}
		else if (s==Options) {new Options();}
	}
}
