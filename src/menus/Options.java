package menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import engine.Game;

public class Options implements ActionListener,ChangeListener {
	JSlider Sound = new JSlider();//Slider for sound volume (grunts and groans)
	JSlider Music = new JSlider();//Slider for music volume (background music)
	JList Textures;//Texture Pack setup.
	DefaultListModel Text_Model;//Model to be used for the Texture List
	JButton Close = new JButton("Return");
	JButton Load = new JButton("LoadTexture");
	
	
	public Options() {
		Game.gms.OpenMenu(360,260);
		
		Sound.setBounds(120, 0, 32*4, 32);Game.gms.add(Sound);
		Music.setBounds(120, 64, 32*4, 32);Game.gms.add(Music);
		Close.setBounds(150, 180, 32*4, 32);Game.gms.add(Close);
		
		Close.addActionListener(this);
		Sound.addChangeListener(this);
	}


	@Override public void actionPerformed(ActionEvent e) {
		Object s = e.getSource();
		if (s == Close) {Game.gms.CloseMenu();}
	}
	@Override public void stateChanged(ChangeEvent e) {
		Object s = e.getSource();
		if (s == Sound) {
			//System.out.println("Sound : " + Sound.getComponentCount());
		}
	}
	
}
