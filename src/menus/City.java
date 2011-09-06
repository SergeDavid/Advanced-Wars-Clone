package menus;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;

import engine.Game;

public class City implements ActionListener {
	
	DefaultListModel UnitModel = new DefaultListModel();
	JList Units = new JList(UnitModel);
	JButton Buy = new JButton("Buy");
	JButton Return = new JButton("Return");
	int x;
	int y;
	public City(String Type, int xx, int yy) {
		Insets insets = Game.gms.getInsets();
		Buy.setBounds(10+insets.left, 30*6+10+insets.top, 100, 24);
		Return.setBounds(10+insets.left, 30*7+10+insets.top, 100, 24);
		Units.setBounds(10+insets.left, 10+insets.top, 160, 32*5+16);
		UnitModel.removeAllElements();
		for (int i = 0; i < Game.displayU.size(); i++) {
			UnitModel.addElement(Game.displayU.get(i).name + " = $" + (Game.displayU.get(i).cost * Game.player.get(Game.btl.currentplayer).CostBonus));
		}
		
		Return.addActionListener(this);
		Buy.addActionListener(this);
		x=xx;
		y=yy;
		
		//TODO: Split up what is shown via a String.
		Show();
	}
	public void Show() {
		Game.gms.OpenMenu(180,256);
		Units.setSelectedIndex(0);
		Game.gms.add(Return);
		Game.gms.add(Buy);
		Game.gms.add(Units);
	}
	
	@Override public void actionPerformed(ActionEvent e) {
		Object s = e.getSource();
		if (s==Return) {Game.gms.CloseMenu();}
		else if (s==Buy) {
			Game.btl.Buyunit(Units.getSelectedIndex(), x, y);
			Game.gms.CloseMenu();
		}
	}
}
