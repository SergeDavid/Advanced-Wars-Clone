package menus;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import engine.Game;

/**
 * The options menu is used for modifying the music / sound volume, window size, and other settings (debug and what not included).
 * @author SergeDavid
 * @version 0.2
 */
public class Options implements ActionListener,ChangeListener {
	
	JSlider Sound = new JSlider(1,5,3);//Slider for sound volume (grunts and groans)
	JSlider Music = new JSlider();//Slider for music volume (background music)
	JList Textures;//Texture Pack setup.
	DefaultListModel Text_Model;//Model to be used for the Texture List
	JButton Close = new JButton("Return");
	JButton Load = new JButton("LoadTexture");
	
	public Options() {
		Sound.setMajorTickSpacing(2);
		Sound.setMinorTickSpacing(1);
		Sound.setPaintTicks(true);
		Sound.setSnapToTicks(true);
		
		Point size = MenuHandler.PrepMenu(360,260);
		SetBounds(size);
		AddGui();
		AddListeners();
	}
	private void SetBounds(Point size) {
		Sound.setBounds(size.x+120, size.y+0, 32*6, 64);
		Music.setBounds(size.x+120, size.y+92, 32*4, 32);
		Close.setBounds(size.x+150, size.y+180, 32*4, 32);
		Load.setBounds(size.x+150, size.y+150, 32*4, 32);
	}
	private void AddGui() {
		Game.gui.add(Load);
		Game.gui.add(Close);
		Game.gui.add(Music);
		Game.gui.add(Sound);
	}
	private void AddListeners() {
		Load.addActionListener(this);
		Close.addActionListener(this);
		Sound.addChangeListener(this);
	}


	@Override public void actionPerformed(ActionEvent e) {
		Object s = e.getSource();
		if (s == Close) {//TODO: Add in handling for opening this from the main menu.
			Game.save.SaveSettings();
			MenuHandler.CloseMenu();
			if (Game.GameState == Game.State.MENU) {
				new StartMenu();
			}
		}
		else if (s == Load) {
			new TexturePack();
		}
	}
	@Override public void stateChanged(ChangeEvent e) {
		Object s = e.getSource();
		if (s == Sound) {
			Game.gui.ResizeScreen(Sound.getValue());
		}
	}
	
}
