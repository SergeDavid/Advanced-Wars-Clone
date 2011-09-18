package menus;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import engine.Game;

public class LoadMap implements ActionListener {
	JList maps_list = new JList();
	DefaultListModel maps_model = new DefaultListModel();
	JButton Return = new JButton("Return");
	JButton Load = new JButton("Load");
	
	public LoadMap() {
		Game.gms.OpenMenu(300,320);
		
		Load.setBounds(15, 20, 100, 32);
		Game.gms.add(Load);
		Return.setBounds(15, 60, 100, 32);
		Game.gms.add(Return);
		
		Return.addActionListener(this);
		Load.addActionListener(this);
		
		MapListStuff();
	}
	
	private void MapListStuff() {
		maps_model = Game.finder.GrabMaps();
		JScrollPane maps_pane = new JScrollPane(maps_list = new JList(maps_model));
		maps_pane.setBounds(140, 10, 140, 300);
		Game.gms.add(maps_pane);
		Dimension size = maps_list.getPreferredSize();
		maps_list.setBounds(4, 2, size.width, size.height);
		maps_list.setSelectedIndex(0);
	}

	@Override public void actionPerformed(ActionEvent e) {
		Object s = e.getSource();
		if (s==Load) {
			 Game.error.ShowError("Currently Not Supported");
		}
		else if (s==Return) {
			Game.gms.CloseMenu();
		}
	}
}
