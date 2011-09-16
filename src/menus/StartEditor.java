package menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import engine.Game;

public class StartEditor implements ActionListener {

	JButton Start = new JButton("Start");
	JButton Return = new JButton("Return");
	
	JTextField Name = new JTextField(40);
	JTextField Width = new JTextField(25);
	JTextField Height = new JTextField(25);
	JLabel LabelN = new JLabel("Name: ");
	JLabel LabelW = new JLabel("Width: ");
	JLabel LabelH = new JLabel("Height: ");
	
	
	public StartEditor() {
		Return.addActionListener(this);
		Start.addActionListener(this);

		Name.setText("MapName");
		Width.setText("32");
		Height.setText("32");
		//TODO: Split up what is shown via a String.
		ChangeSizes();
		Show();
	}
	private void ChangeSizes() {
		LabelN.setBounds(20, 20, 100, 24);
		LabelW.setBounds(20, 50, 100, 24);
		LabelH.setBounds(20, 80, 100, 24);
		
		Name.setBounds(140, 20, 100, 24);
		Width.setBounds(140, 50, 100, 24);
		Height.setBounds(140, 80, 100, 24);
		
		Start.setBounds(60, 150, 100, 24);
		Return.setBounds(160, 150, 100, 24);
	}
	private void Show() {
		Game.gui.removeAll();
		Game.gms.OpenMenu(280,180);
		Game.gms.add(LabelN);
		Game.gms.add(LabelW);
		Game.gms.add(LabelH);
		Game.gms.add(Name);
		Game.gms.add(Width);
		Game.gms.add(Height);
		Game.gms.add(Start);
		Game.gms.add(Return);
	}
	
	@Override public void actionPerformed(ActionEvent e) {
		Object s = e.getSource();
		if (s==Start) {
			Game.edit.StartEditor(
					Name.getText(),
					Integer.parseInt(Width.getText()),
					Integer.parseInt(Height.getText()));
		}
		if (s==Return) {
			Game.gui.LoginScreen();
			Game.gms.CloseMenu();
		}
	}
}
