
package engine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
	
public class Gui extends JPanel {
	private static final long serialVersionUID = 3457450162330022096L;
	
	/**The width and height of the content box.*/
	public int width = 640;
	public int height = 400;

	//Main Menu
	public JButton Join = new JButton("New Game");
	public JButton Options = new JButton("Options");
	public JButton Exit = new JButton("Exit");
	
	/**Plan on updating this for better character selection.*/
	public JButton[] charselect = new JButton[10];
	public JButton ply_next = new JButton("Next");
	public JButton ply_prev = new JButton("Prev");
	public JButton ply_endturn = new JButton("End Turn");
	JList maps_list = new JList();
	DefaultListModel maps_model = new DefaultListModel();
	
	public GameMenus gms;
		
	public Gui(JFrame item) {
	    item.add(this);
		setPreferredSize(new Dimension(width,height));
		setBounds(0, 0, width, height);
		setLayout(null);
		
		LoginScreen();
	}

	/**Creates the Login screen layout*/
	public void LoginScreen() {
		Game.GameState=Game.TheMenu;
		
		removeAll();
		
		add(Join);
		add(Options);
		add(Exit);
		
		Insets insets = getInsets();
		Dimension size = Join.getPreferredSize();
		Join.setBounds(220 + insets.left, 200 + insets.top, 
				size.width, size.height);
		size = Options.getPreferredSize();
		Options.setBounds(220 + insets.left, 230 + insets.top, 
				size.width, size.height);
		size = Exit.getPreferredSize();
		Exit.setBounds(220 + insets.left, 260 + insets.top, 
				size.width, size.height);
		
		JScrollPane maps_pane = new JScrollPane(maps_list = new JList(maps_model));
		maps_pane.setBounds(400 + insets.left, 40 + insets.top, 140, 300);
		add(maps_pane);
		size = maps_list.getPreferredSize();
		maps_list.setBounds(insets.left, insets.top, size.width, size.height);
	}
	/**Creates the InGame screen layout*/
	public void InGameScreen() {
		removeAll();
		Game.GameState=Game.Playing;
		add(ply_next);
		add(ply_prev);
		add(ply_endturn);
		Insets insets = getInsets();
		Dimension size = ply_next.getPreferredSize();
		ply_next.setBounds(578 + insets.left, 316 + insets.top, size.width, size.height);
		size = ply_prev.getPreferredSize();
		ply_prev.setBounds(514 + insets.left, 316 + insets.top, size.width, size.height);
		size = ply_endturn.getPreferredSize();
		ply_endturn.setBounds(521 + insets.left, 350 + insets.top, 110, size.height);
	}
	
	public void paintComponent(Graphics g) {
		Image buffimage = createImage(width, height);
		Graphics2D gg = (Graphics2D) buffimage.getGraphics();
		gg.setColor(new Color(0,0,60));
		gg.fillRect(0, 0, width, height);
		gg.setColor(new Color(255,255,255));
		switch (Game.GameState) {
		case Game.TheMenu:
		break;
		case Game.Playing:
			//TODO: Dealth with who is currently playing better.
			if (Game.btl.currentplayer==0) {gg.setColor(new Color(60,0,0));}
			else {gg.setColor(new Color(0,0,60));}
			gg.fillRect(0, 0, width, height);
			gg.setColor(new Color(255,255,255));
			DrawTerrain(gg);
			DrawSidebar(gg);
			DrawBuildings(gg);
			DrawUnits(gg);
			
			//Draw the selector
			gg.drawImage(Game.img_tile,
					Game.player.get(Game.btl.currentplayer).selectx*32,
					Game.player.get(Game.btl.currentplayer).selecty*32,
					Game.player.get(Game.btl.currentplayer).selectx*32+32,
					Game.player.get(Game.btl.currentplayer).selecty*32+32,32*7,0,32*8,32,null);
			//TODO: Draw any foreground effects.
		break;
		}
		g.drawImage(buffimage, 0, 0, this);
	}

	private void DrawSidebar(Graphics2D gg) {
		int offset = 514;
		gg.drawString("Funds = " + Game.player.get(Game.btl.currentplayer).money,offset+4,128);
		
		DrawUnitInfo(gg, offset);
		DrawTerrainInfo(gg);
	}

	private void DrawBuildings(Graphics2D gg) {
		for (buildings.Base bld : Game.builds) {
			int[] loc = bld.DrawMe();
			gg.drawImage(Game.img_char,
					bld.x*32,bld.y*32,bld.x*32+32,bld.y*32+32,
					loc[0]*32,loc[1]*32,
					loc[0]*32+32,loc[1]*32+32,null);
		}
	}
	private void DrawUnits(Graphics2D gg) {
		for (units.Base chars : Game.units) {
			int[] loc = chars.DrawMe();
			gg.drawImage(Game.img_char,
				chars.x*32,chars.y*32,chars.x*32+32,chars.y*32+32,
				loc[0]*32,loc[1]*32,loc[0]*32+32,loc[1]*32+32,null);
		}
	}

	/**This draws the following things when applicable.
	 * 1) Move-able locations
	 * 2) Attackable locations
	 * 3) Unit name, health, image
	 * */
	private void DrawUnitInfo(Graphics2D gg, int offset) {
		players.Base plyer = Game.player.get(Game.btl.currentplayer);
		if (plyer.unitselected) {
			units.Base unit = Game.units.get(plyer.selectedunit);
			if (unit.acted) {
				gg.drawString("Finished.",offset,64);
			}
			else if (unit.moved&&!unit.acted) {int i=0;
				gg.drawString("Attacking.",offset,64);
				for (int y = unit.y-1; y <= unit.y + 1; y++) {//TODO: Do something with this.
					for (int x = unit.x-1; x <= unit.x + 1; x++) {
						if (unit.atkrange[i]) {gg.drawImage(Game.img_tile,x*32,y*32,x*32+32,y*32+32,32*6,0,32*7,32,null);}
						i++;
			}	}	}
			else if (!unit.moved) {int i=0;
				gg.drawString("Moving",offset,64);
				for (int y = unit.y - 2; y <= unit.y + 2; y++) {//TODO: Do something with this.
					for (int x = unit.x - 2; x <= unit.x + 2; x++) {
						if (unit.movrange[i]) {gg.drawImage(Game.img_tile,x*32,y*32,x*32+32,y*32+32,32*6,0,32*7,32,null);}
						i++;
			}	}	}
			
			//UNIT SELECTION STUFFIES!
			int[] loc = unit.DrawMe();
			gg.drawImage(Game.img_char,
					offset,8,offset+32,8+32,
					32*loc[0]*32,loc[1]*32,loc[0]*32+32,loc[1]*32+32,null);
			gg.drawString(unit.nick + " = " + unit.health + " HP!",offset,52);
		}
	}
	private void DrawTerrainInfo(Graphics2D gg) {
		//TODO: Align this up with the Menu bar
		//TODO: Display Terrain Name
		//TODO: Display Terrain Bonuses
		//TODO: If building, display building info.
		int x = Game.player.get(Game.btl.currentplayer).selectx;
		int y = Game.player.get(Game.btl.currentplayer).selecty;
		int xx = Game.map.tiles.get(Game.map.map[y][x]).x();
		int yy = Game.map.tiles.get(Game.map.map[y][x]).y();
		gg.drawImage(Game.img_tile, 520+32, 200, 520+32+32, 232, xx*32, yy*32, xx*32+32, yy*32+32, null);
	}

	private void DrawTerrain(Graphics2D gg) {
		for (int y=0; y < Game.map.height; y++) {
			for (int x=0; x < Game.map.width; x++) {
				int xx = Game.map.tiles.get(Game.map.map[y][x]).x();
				int yy = Game.map.tiles.get(Game.map.map[y][x]).y();
				gg.drawImage(Game.img_tile, x*32, y*32, x*32+32, y*32+32, xx*32, yy*32, xx*32+32, yy*32+32, null);
			}
		}
	}
	
	
	

}
