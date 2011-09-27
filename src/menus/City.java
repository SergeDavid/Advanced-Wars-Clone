package menus;

import java.awt.Color;
import java.awt.Point;
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

/**
 * Displays a list of available units, and some information about them to buy.
 * @author SergeDavid
 * @version 0.2
 */
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
	int[] ids = new int[Game.displayU.size()];
	
	public City(String Type, int xx, int yy) {
		//UnitModel.removeAllElements();
		int a = 0;
		for (int i = 0; i < Game.displayU.size(); i++) {
			if (Game.displayU.get(i).building.equals(Type)) {
				UnitModel.addElement(Game.displayU.get(i).name + " = $" + (Game.displayU.get(i).cost * Game.player.get(Game.btl.currentplayer).CostBonus));
				ids[a] = i;
				a++;
			}
		}
		x=xx;
		y=yy;
		
		Main.setBackground(new Color(80,80,80));
		Alt.setBackground(new Color(120,120,120));
		UnitDesc.setBackground(new Color(138,138,146));
		UnitDesc.setEditable(false);
		
		current = 0;
		ChangeAlt();
		Units.setSelectedIndex(0);
		Point size = MenuHandler.PrepMenu(480,260);
		MenuHandler.HideBackground();
		SetBounds(size);
		AddGui();
		AddListeners();
	}
	private void SetBounds(Point size) {
		Main.setBounds(size.x, size.y, 150, 260);
		Alt.setBounds(size.x+140 + 20, size.y, 320, 200);
		Buy.setBounds(25, 30*6+16, 100, 24);
		Return.setBounds(25, 30*7+16, 100, 24);
		Units.setBounds(10, 10, 135, 176);
		UnitName.setBounds(42, 10, 200, 24);
		UnitDesc.setBounds(10, 42, 300, 152);
	}
	private void AddGui() {
		Main.add(Buy);
		Main.add(Return);
		Main.add(Units);
		
		Game.gui.add(Main);
		Game.gui.add(Alt);
	}
	private void AddListeners() {
		UnitName.setFocusable(false);
		UnitDesc.setFocusable(false);
		Return.addActionListener(this);
		Buy.addActionListener(this);
		Units.addListSelectionListener(this);
	}
	
	
	@Override public void actionPerformed(ActionEvent e) {
		Object s = e.getSource();
		if (s==Return) {MenuHandler.CloseMenu();}
		else if (s==Buy) {
			Game.btl.Buyunit(ids[Units.getSelectedIndex()], x, y);
			MenuHandler.CloseMenu();
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
		UnitDesc.setText(
				"Fuel: " + unit.MaxFuel + "\n" +
				"Ammo: " + unit.Ammo + "\n" +
				"Speed: " + unit.speed + "\n" +
				"\nDescription: " + unit.desc);
		Alt.add(UnitName);
		Alt.add(UnitDesc);
	}
}
