package menus;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;

import engine.Game;

/**
 * This is the opening menu of the game.
 * @author SergeDavid
 * @version 0.2
 */
public class StartMenu implements ActionListener {
	//Single Player
	public JButton New = new JButton("New Game");
	public JButton Load = new JButton("Continue");
	
	//Online
	public JButton Join = new JButton("Online");
	
	//Other
	public JButton Editor = new JButton("Editor");
	public JButton Credits = new JButton("Credits");
	public JButton Options = new JButton("Options");
	public JButton Exit = new JButton("Exit");
	
	//Map list
	public JList maps_list = new JList();
	DefaultListModel maps_model = new DefaultListModel();
	
	public StartMenu() {
		Point size = MenuHandler.PrepMenu(400,280);
		MenuHandler.HideBackground();
		SetBounds(size);
		AddGui();
		AddListeners();
		MapList(size);
	}

	private void SetBounds(Point size) {
		New.setBounds(size.x,size.y+10, 100, 32);
		Load.setBounds(size.x,size.y+10+38*1, 100, 32);
		Join.setBounds(size.x,size.y+10+38*2, 100, 32);
		Editor.setBounds(size.x,size.y+10+38*3, 100, 32);
		Credits.setBounds(size.x,size.y+10+38*4, 100, 32);
		Options.setBounds(size.x,size.y+10+38*5, 100, 32);
		Exit.setBounds(size.x,size.y+10+38*6, 100, 32);
	}
	private void AddGui() {
		Game.gui.add(New);
		Game.gui.add(Load);
		//Game.gui.add(Join);
		Game.gui.add(Editor);
		Game.gui.add(Credits);
		Game.gui.add(Options);
		Game.gui.add(Exit);
	}
	private void MapList(Point size) {
		maps_model = Game.finder.GrabMaps();
		JScrollPane maps_pane = new JScrollPane(maps_list = new JList(maps_model));
		maps_pane.setBounds(size.x+220, size.y+10, 140, 260);//220,10
		Game.gui.add(maps_pane);
		maps_list.setBounds(0, 0, 140, 260);
		maps_list.setSelectedIndex(0);
	}
	private void AddListeners() {
		New.addActionListener(this);
		Load.addActionListener(this);
		Join.addActionListener(this);
		Editor.addActionListener(this);
		Credits.addActionListener(this);
		Options.addActionListener(this);
		Exit.addActionListener(this);
	}

	@Override public void actionPerformed(ActionEvent e) {
		Object s = e.getSource();
		if (s==New) {new PlayerSelection(maps_list.getSelectedValue()+"");}
		else if (s==Load) {Game.save.LoadGame();MenuHandler.CloseMenu();}
		else if (s==Join) {Game.error.ShowError("Online features are not added yet.");}
		else if (s==Editor) {
			Game.edit.StartEditor(
					"MapName",
					16,
					20);
			MenuHandler.CloseMenu();
		}
		else if (s==Credits) {new Credits();}
		else if (s==Options) {new Options();}
		else if (s==Exit) {System.exit(0);}
	}
}
