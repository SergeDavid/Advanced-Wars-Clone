package menus;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
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
	
	JLabel LScr = new JLabel("Screen Size");
	JLabel LSnd = new JLabel("Sound Volume");
	JLabel LMsc = new JLabel("Music Volume");
	
	JSlider Screen = new JSlider(1,5,3);
	JSlider Sound = new JSlider(0,100,50);//Slider for sound volume (grunts and groans)
	JSlider Music = new JSlider(0,100,50);//Slider for music volume (background music)
	
	JList Textures;//Texture Pack setup.
	DefaultListModel Text_Model;//Model to be used for the Texture List
	JButton ChangeSize = new JButton("Change Size");
	JButton Close = new JButton("Return");
	JButton Load = new JButton("Load Texture");
	
	public Options() {
		Screen.setMajorTickSpacing(2);
		Screen.setMinorTickSpacing(1);
		Screen.setPaintTicks(true);
		Screen.setSnapToTicks(true);
		Sound.setPaintLabels(true);
		Sound.setMajorTickSpacing(25);
		Sound.setPaintTicks(true);
		Music.setPaintLabels(true);
		Music.setMajorTickSpacing(25);
		Music.setPaintTicks(true);
		
		//TODO: Remove this when I add music and sound.
		Sound.setToolTipText("Currently does not work until I add sounds.");
		Music.setToolTipText("Currently does not work until I add music.");
		
		Point size = MenuHandler.PrepMenu(360,260);
		SetBounds(size);
		AddGui();
		AddListeners();
	}
	private void SetBounds(Point size) {
		LScr.setBounds(size.x+20, size.y+10, 160, 48);
		LSnd.setBounds(size.x+20, size.y+74, 180, 48);
		LMsc.setBounds(size.x+20, size.y+138, 200, 48);
		
		Screen.setBounds(size.x+110, size.y+10, 240, 48);
		Sound.setBounds(size.x+110, size.y+74, 240, 48);
		Music.setBounds(size.x+110, size.y+138, 240, 48);
		
		Close.setBounds(size.x+224, size.y+224, 128, 32);
		Load.setBounds(size.x+224, size.y+190, 128, 32);
		ChangeSize.setBounds(size.x+80, size.y+190, 128, 32);
	}
	private void AddGui() {
		Game.gui.add(LSnd);
		Game.gui.add(LScr);
		Game.gui.add(LMsc);
		
		Game.gui.add(Load);
		Game.gui.add(Close);
		Game.gui.add(ChangeSize);
		
		Game.gui.add(Screen);
		Game.gui.add(Music);
		Game.gui.add(Sound);
	}
	private void AddListeners() {
		Load.addActionListener(this);
		Close.addActionListener(this);
		ChangeSize.addActionListener(this);
		
		Sound.addChangeListener(this);
		Music.addChangeListener(this);
	}


	@Override public void actionPerformed(ActionEvent e) {
		Object s = e.getSource();
		if (s == Close) {
			Game.save.SaveSettings();
			MenuHandler.CloseMenu();
			if (Game.GameState == Game.State.MENU) {new StartMenu();}
		}
		else if (s == Load) {new TexturePack();}
		else if (s == ChangeSize) {Game.gui.ResizeScreen(Screen.getValue());}
	}
	@Override public void stateChanged(ChangeEvent e) {
		Object s = e.getSource();
		if (s == Sound) {
			//TODO: Add Sounds to the game.
		}
		else if (s == Music) {
			//TODO: Add Music to the game.
		}
	}
	
}
