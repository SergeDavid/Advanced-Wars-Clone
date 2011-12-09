package menus;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import engine.Game;

/**
 * This is the in editor menu that pops up when you hit the enter button.
 * @author SergeDavid
 * @version 0.2
 */
public class EditorMenu implements ActionListener {
	JButton Quit = new JButton("Quit");
	JButton Save = new JButton("Save");
	JButton Change = new JButton("Change");
	JButton Load = new JButton("Load");
	JButton Return = new JButton("Return");
	
	/**Max name length is 16*/
	JTextField Name = new JTextField(20);
	JTextField Width = new JTextField(20);
	JTextField Height = new JTextField(15);
	JLabel LabelN = new JLabel("Name: ");
	JLabel LabelW = new JLabel("Width: ");
	JLabel LabelH = new JLabel("Height: ");
	
	public EditorMenu() {
		Name.setText(Game.edit.mapname);
		Width.setText(Game.map.width+"");
		Height.setText(Game.map.height+"");
		Point size = MenuHandler.PrepMenu(180,250);
		SetBounds(size);
		AddGui();
		AddListeners();
	}
	private void SetBounds(Point size) {
		LabelN.setBounds(size.x+10, size.y+10, 100, 24);
		LabelW.setBounds(size.x+10, size.y+40, 100, 24);
		LabelH.setBounds(size.x+10, size.y+70, 100, 24);
		
		Name.setBounds(size.x+60, size.y+10, 100, 24);
		Width.setBounds(size.x+60, size.y+40, 60, 24);
		Height.setBounds(size.x+60, size.y+70, 60, 24);
		Change.setBounds(size.x+40, size.y+100, 100, 24);
		Load.setBounds(size.x+40, size.y+130, 100, 24);
		Save.setBounds(size.x+40, size.y+160, 100, 24);
		Return.setBounds(size.x+40, size.y+190, 100, 24);
		Quit.setBounds(size.x+40, size.y+220, 100, 24);
	}
	private void AddGui() {
		Game.gui.add(LabelN);
		Game.gui.add(LabelW);
		Game.gui.add(LabelH);
		Game.gui.add(Name);
		Game.gui.add(Width);
		Game.gui.add(Height);
		
		Game.gui.add(Change);
		Game.gui.add(Quit);
		Game.gui.add(Save);
		Game.gui.add(Load);
		Game.gui.add(Return);
	}
	private void AddListeners() {
		Change.addActionListener(this);
		Quit.addActionListener(this);
		Save.addActionListener(this);
		Load.addActionListener(this);
		Return.addActionListener(this);
	}
	
	private void ValidateMapSize(int height, int width) {
		boolean change = false;
		int newwidth = Game.map.width;
		int newheight = Game.map.height;
		if (height >= Game.map.minsize && height <= Game.map.maxsize) {newwidth = width;change = true;}
		if (width >= Game.map.minsize && width <= Game.map.maxsize) {newheight = height;change = true;}
		if (change == true) {
			Game.map.ResizeMap(newwidth,newheight,Game.map.width,Game.map.height);
		}
	}
	private void ValidateMapName(String name) {
		if (!name.isEmpty() && name.length() <= 16) {
			Game.edit.mapname = name;
		}
	}
	
	@Override public void actionPerformed(ActionEvent e) {
		Object s = e.getSource();
		if (s==Save) {
			Game.map.parse.encode(Game.edit.mapname);
		}
		else if (s==Load) {
			new LoadMap();
		}
		else if (s==Change) {
			ValidateMapSize(Integer.parseInt(Height.getText()),Integer.parseInt(Width.getText()));
			ValidateMapName(Name.getText());
		}
		else if (s==Return) {
			MenuHandler.CloseMenu();
		}
		else if (s==Quit) {
			Game.gui.LoginScreen();
		}
	}
}
