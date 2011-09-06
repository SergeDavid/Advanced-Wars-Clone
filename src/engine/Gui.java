
package engine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
	
public class Gui extends JPanel {
	private static final long serialVersionUID = 3457450162330022096L;
	
	/**The width and height of the content box.*/
	public int width = Game.width-6;
	public int height = Game.height-12;
	
	/**The base frame to keep animations in sync (1 frame = 100ms) Remember to reset this to zero when it hits 12.*/
	public int frame = 0; //counts up to 12 (resets to zero)

	//Main Menu
	public JButton Join = new JButton("New Game");
	public JButton Load = new JButton("Continue");
	public JButton Options = new JButton("Options");
	public JButton Exit = new JButton("Exit");

	JList maps_list = new JList();
	DefaultListModel maps_model = new DefaultListModel();
	JList packs_list = new JList();
	DefaultListModel packs_model = new DefaultListModel();
		
	public Gui() {
		setPreferredSize(new Dimension(width,height));
		setBounds(0, 0, width, height);
		setLayout(null);
		
		LoginScreen();
	}

	/**Creates the Login screen layout*/
	public void LoginScreen() {
		Game.GameState=Game.State.MENU;
		removeAll();
		
		add(Join);
		add(Load);
		add(Options);
		add(Exit);
		
		Insets insets = getInsets();
		Dimension size = Join.getPreferredSize();
		Join.setBounds(220 + insets.left, 200 + insets.top, 
				size.width, size.height);
		size = Load.getPreferredSize();
		Load.setBounds(220 + insets.left, 230 + insets.top, 
				size.width, size.height);
		size = Options.getPreferredSize();
		Options.setBounds(220 + insets.left, 260 + insets.top, 
				size.width, size.height);
		size = Exit.getPreferredSize();
		Exit.setBounds(220 + insets.left, 290 + insets.top, 
				size.width, size.height);
		
		maps_model = Game.finder.GrabMaps();
		JScrollPane maps_pane = new JScrollPane(maps_list = new JList(maps_model));
		maps_pane.setBounds(400 + insets.left, 40 + insets.top, 140, 300);
		add(maps_pane);
		size = maps_list.getPreferredSize();
		maps_list.setBounds(insets.left, insets.top, size.width, size.height);
		maps_list.setSelectedIndex(0);
		if (Game.error.showing) {add(Game.error);}
	}
	/**Creates the InGame screen layout*/
	public void InGameScreen() {
		removeAll();
		Game.GameState=Game.State.PLAYING;
		if (Game.error.showing) {add(Game.error);}
	}

	public void paintComponent(Graphics g) {
		//Creates the buffer image and graphic settings.
		Image buffimage = createImage(width, height);
		Graphics2D gg = (Graphics2D) buffimage.getGraphics();
		
		switch (Game.GameState) {
		case MENU:
			gg.setColor(new Color(0,0,60));
			gg.fillRect(0, 0, width, height);
			gg.setColor(new Color(255,255,255));
		break;
		case PLAYING:
			gg.setColor(GrabColor());
			gg.fillRect(0, 0, width, height);
			gg.drawImage(Game.img_menu[0], 0, 0, width, height, 0, 0, 32, 256, null);
			gg.drawImage(gui.Terrain.Draw(), 0, 0, width, height, null);
			new gui.Ranges(gg);
			gg.drawImage(gui.Cities.Draw(), 0, 0, width, height, null);
			gg.drawImage(gui.Units.Draw(), 0, 0, width, height, null);
			gg.drawImage(gui.Selector.Draw(frame), 0, 0, width, height, null);
			
			if (Game.input.MenuHack) {new gui.InfoMenu(gg);}//This displays the menu only when it is paused.
			else {/*Include a mini menu that floats around the map*/}
		break;
		case EDITOR:
			gg.drawImage(gui.Terrain.Draw(), 0, 0, width, height, null);
			gg.drawImage(gui.Cities.Draw(), 0, 0, width, height, null);
			gg.drawImage(gui.Units.Draw(), 0, 0, width, height, null);
		break;
		}
		g.drawImage(buffimage, 0, 0, this);
		buffimage.flush();
		gg.dispose();
	}

	private Color GrabColor() {
		switch (Game.btl.currentplayer) {//TODO: Redesign coloring to be taken from the player.
			case 0:return new Color(200,0,0);
			case 1:return new Color(0,0,200);
			case 2:return new Color(0,200,0);
			case 3:return new Color(200,200,0);
			default:return new Color(200,200,200);
		}
	}
}
