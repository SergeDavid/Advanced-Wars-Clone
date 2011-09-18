package menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import engine.Game;

public class EditorMenu implements ActionListener {
	
	JButton Quit = new JButton("Quit");
	JButton Save = new JButton("Save");
	JButton Load = new JButton("Load");
	JButton Return = new JButton("Return");
	
	public EditorMenu() {
		Quit.addActionListener(this);
		Save.addActionListener(this);
		Load.addActionListener(this);
		Return.addActionListener(this);
		
		ChangeSizes();
		Show();
	}
	private void ChangeSizes() {
		Load.setBounds(20, 20, 100, 24);
		Save.setBounds(20, 50, 100, 24);
		Return.setBounds(20, 80, 100, 24);
		Quit.setBounds(20, 110, 100, 24);
	}
	private void Show() {
		Game.gui.removeAll();
		Game.gms.OpenMenu(140,140);
		
		Game.gms.add(Quit);
		Game.gms.add(Save);
		Game.gms.add(Load);
		Game.gms.add(Return);
	}
	
	@Override public void actionPerformed(ActionEvent e) {
		Object s = e.getSource();
		if (s==Save) {
			Game.map.parse.encode(Game.edit.mapname);
		}
		else if (s==Load) {
			new LoadMap();
		}
		else if (s==Return) {
			Game.gms.CloseMenu();
		}
		else if (s==Quit) {
			Game.gui.LoginScreen();
			Game.gms.CloseMenu();
		}
	}
}
