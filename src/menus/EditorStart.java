package menus;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import engine.Game;

/**
 * This handles the basic settings for the map editor (name, width, and height) I shouldn't need this when I add dynamic map size updating.
 * @author SergeDavid
 * @version 0.2
 */
public class EditorStart implements ActionListener {

	JButton Start = new JButton("Start");
	JButton Return = new JButton("Return");
	
	JTextField Name = new JTextField(40);
	JTextField Width = new JTextField(25);
	JTextField Height = new JTextField(25);
	JLabel LabelN = new JLabel("Name: ");
	JLabel LabelW = new JLabel("Width: ");
	JLabel LabelH = new JLabel("Height: ");
	
	
	public EditorStart() {
		Name.setText("MapName");
		Width.setText("32");
		Height.setText("32");
		Point size = MenuHandler.PrepMenu(280,180);
		SetBounds(size);
		AddGui();
		AddListeners();
	}
	private void SetBounds(Point size) {
		LabelN.setBounds(size.x+20, size.y+20, 100, 24);
		LabelW.setBounds(size.x+20, size.y+50, 100, 24);
		LabelH.setBounds(size.x+20, size.y+80, 100, 24);
		
		Name.setBounds(size.x+140, size.y+20, 100, 24);
		Width.setBounds(size.x+140, size.y+50, 100, 24);
		Height.setBounds(size.x+140, size.y+80, 100, 24);
		
		Start.setBounds(size.x+60, size.y+150, 100, 24);
		Return.setBounds(size.x+160, size.y+150, 100, 24);
	}
	private void AddGui() {
		Game.gui.add(LabelN);
		Game.gui.add(LabelW);
		Game.gui.add(LabelH);
		Game.gui.add(Name);
		Game.gui.add(Width);
		Game.gui.add(Height);
		Game.gui.add(Start);
		Game.gui.add(Return);
	}
	private void AddListeners() {
		Return.addActionListener(this);
		Start.addActionListener(this);
	}
	
	@Override public void actionPerformed(ActionEvent e) {
		Object s = e.getSource();
		if (s==Start) {
			Game.edit.StartEditor(
					Name.getText(),
					Integer.parseInt(Width.getText()),
					Integer.parseInt(Height.getText()));
			MenuHandler.CloseMenu();
		}
		if (s==Return) {
			Game.gui.LoginScreen();
		}
	}
}
