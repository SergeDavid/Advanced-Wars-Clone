package menus;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import engine.Game;

/**
 * This is the in editor menu that pops up when you hit the enter button.
 * @author SergeDavid
 * @version 0.2
 */
public class EditorMenu implements ActionListener {
	JButton Quit = new JButton("Quit");
	JButton Save = new JButton("Save");
	JButton Load = new JButton("Load");
	JButton Return = new JButton("Return");
	
	public EditorMenu() {
		Point size = MenuHandler.PrepMenu(140,140);
		SetBounds(size);
		AddGui();
		AddListeners();
	}
	private void SetBounds(Point size) {
		Load.setBounds(size.x+20, size.y+20, 100, 24);
		Save.setBounds(size.x+20, size.y+50, 100, 24);
		Return.setBounds(size.x+20, size.y+80, 100, 24);
		Quit.setBounds(size.x+20, size.y+110, 100, 24);
	}
	private void AddGui() {
		Game.gui.add(Quit);
		Game.gui.add(Save);
		Game.gui.add(Load);
		Game.gui.add(Return);
	}
	private void AddListeners() {
		Quit.addActionListener(this);
		Save.addActionListener(this);
		Load.addActionListener(this);
		Return.addActionListener(this);
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
			MenuHandler.CloseMenu();
		}
		else if (s==Quit) {
			Game.gui.LoginScreen();
			MenuHandler.CloseMenu();
		}
	}
}
