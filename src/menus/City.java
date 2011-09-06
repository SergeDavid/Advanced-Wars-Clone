package menus;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import engine.Game;

public class City implements ActionListener,ListSelectionListener {
	
	DefaultListModel UnitModel = new DefaultListModel();
	JList Units = new JList(UnitModel);
	JButton Buy = new JButton("Buy");
	JButton Return = new JButton("Return");
	JPanel Main = new JPanel();
	JPanel Alt = new JPanel();
	
	JLabel UnitName = new JLabel("Unit Name");
	JTextArea UnitDesc = new JTextArea("Unit Description");
	
	int current;
	int x;
	int y;
	public City(String Type, int xx, int yy) {
		Insets insets = Game.gms.getInsets();
		
		Main.setBounds(insets.left, insets.top, 150, 260);
		Alt.setBounds(140 + 20 + insets.left, insets.top, 320, 200);
		
		Buy.setBounds(25, 30*6+16+insets.top, 100, 24);
		Return.setBounds(25, 30*7+16+insets.top, 100, 24);
		Units.setBounds(10, 10+insets.top, 135, 176);
		UnitModel.removeAllElements();
		for (int i = 0; i < Game.displayU.size(); i++) {
			UnitModel.addElement(Game.displayU.get(i).name + " = $" + (Game.displayU.get(i).cost * Game.player.get(Game.btl.currentplayer).CostBonus));
		}
		Main.add(Buy);
		Main.add(Return);
		Main.add(Units);
		
		UnitName.setBounds(25, 16, 200, 32);
		UnitDesc.setBounds(25, 64, 200, 100);
		UnitName.setFocusable(false);
		UnitDesc.setFocusable(false);
			
		Return.addActionListener(this);
		Buy.addActionListener(this);
		Units.addListSelectionListener(this);
		x=xx;
		y=yy;
		
		Main.setBackground(new Color(80,80,80));
		Alt.setBackground(new Color(120,120,120));
		UnitDesc.setBackground(new Color(130,130,130));
		//TODO: Split up what is shown via a String.
		Show();
	}
	public void Show() {
		current = 0;
		ChangeAlt();
		Game.gms.OpenMenu(480,260);
		Game.gms.setOpaque(false);
		Units.setSelectedIndex(0);
		Game.gms.add(Main);
		Game.gms.add(Alt);
	}
	
	@Override public void actionPerformed(ActionEvent e) {
		Object s = e.getSource();
		if (s==Return) {Game.gms.CloseMenu();}
		else if (s==Buy) {
			Game.btl.Buyunit(Units.getSelectedIndex(), x, y);
			Game.gms.CloseMenu();
		}
	}
	@Override public void valueChanged(ListSelectionEvent e) {
		Object s = e.getSource();
		if (s == Units) {
			if (Units.getSelectedIndex()!=current) {
				current = Units.getSelectedIndex();
				ChangeAlt();
			}
		}
	}
	private void ChangeAlt() {
		Alt.removeAll();
		units.Base unit = Game.displayU.get(current);
		UnitName.setText(unit.name);
		UnitDesc.setText(unit.desc);
		Alt.add(UnitName);
		Alt.add(UnitDesc);
	}
}
