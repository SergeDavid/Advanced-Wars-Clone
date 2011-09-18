package menus;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import engine.Game;

/** 
 * This contains a list of texture packs and two buttons, return and load. Eventually to support reload default pack.
 * @author SergeDavid
 * @version 0.1
 */
public class TexturePack implements ActionListener {
	JList packs_list = new JList();
	DefaultListModel packs_model = new DefaultListModel();
	JButton Return = new JButton("Return");
	JButton Load = new JButton("Load");
	
	public TexturePack() {//TODO: Add a return to default button.
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
		packs_model = Game.finder.GrabPacks();
		JScrollPane packs_pane = new JScrollPane(packs_list = new JList(packs_model));
		packs_pane.setBounds(140, 10, 140, 300);
		Game.gms.add(packs_pane);
		Dimension size = packs_list.getPreferredSize();
		packs_list.setBounds(4, 2, size.width, size.height);
		packs_list.setSelectedIndex(0);
	}

	@Override public void actionPerformed(ActionEvent e) {
		Object s = e.getSource();
		if (s==Load) {
			Game.load.LoadTexturePack(packs_list.getSelectedValue() + "");
			Game.gms.CloseMenu();
		}
		else if (s==Return) {
			new Options();
		}
	}
}
