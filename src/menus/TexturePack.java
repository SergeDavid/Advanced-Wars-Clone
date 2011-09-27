package menus;

import java.awt.Dimension;
import java.awt.Point;
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
 * @version 0.2
 */
public class TexturePack implements ActionListener {
	JList packs_list = new JList();
	DefaultListModel packs_model = new DefaultListModel();
	JButton Return = new JButton("Return");
	JButton Load = new JButton("Load");
	
	public TexturePack() {//TODO: Add a return to default button.
		Point size = MenuHandler.PrepMenu(300,320);
		SetBounds(size);
		AddGui();
		AddListeners();
		MapListStuff(size);
	}
	private void SetBounds(Point size) {
		Load.setBounds(size.x+15, size.y+20, 100, 32);
		Return.setBounds(size.x+15, size.y+60, 100, 32);
	}
	private void AddGui() {
		Game.gui.add(Load);
		Game.gui.add(Return);
	}
	private void AddListeners() {
		Return.addActionListener(this);
		Load.addActionListener(this);
	}
	
	private void MapListStuff(Point me) {
		packs_model = Game.finder.GrabPacks();
		JScrollPane packs_pane = new JScrollPane(packs_list = new JList(packs_model));
		packs_pane.setBounds(me.x+140, me.y+10, 140, 300);
		Game.gui.add(packs_pane);
		Dimension size = packs_list.getPreferredSize();
		packs_list.setBounds(4, 2, size.width, size.height);
		packs_list.setSelectedIndex(0);
	}

	@Override public void actionPerformed(ActionEvent e) {
		Object s = e.getSource();
		if (s==Load) {
			Game.load.LoadTexturePack(packs_list.getSelectedValue() + "");
			MenuHandler.CloseMenu();
		}
		else if (s==Return) {
			new Options();
		}
	}
}
