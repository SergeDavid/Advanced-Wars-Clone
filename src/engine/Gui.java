
package engine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
	
public class Gui extends JPanel {
	private static final long serialVersionUID = 3457450162330022096L;
	
	/**The width and height of the content box.*/
	public int width = 640;
	public int height = 400;
	
	/**The base frame to keep animations in sync (1 frame = 100ms) Remember to reset this to zero when it hits 12.*/
	public int frame = 0; //counts up to 12 (resets to zero)

	//Main Menu
	public JButton Join = new JButton("New Game");
	public JButton Load = new JButton("Continue");
	public JButton Options = new JButton("Options");
	public JButton Exit = new JButton("Exit");

	JList maps_list = new JList();
	DefaultListModel maps_model = new DefaultListModel();
	public GameMenus gms = new GameMenus();
		
	public Gui() {
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
		
		JScrollPane maps_pane = new JScrollPane(maps_list = new JList(maps_model));
		maps_pane.setBounds(400 + insets.left, 40 + insets.top, 140, 300);
		add(maps_pane);
		size = maps_list.getPreferredSize();
		maps_list.setBounds(insets.left, insets.top, size.width, size.height);
		if (Game.error.showing) {add(Game.error);}
	}
	/**Creates the InGame screen layout*/
	public void InGameScreen() {
		removeAll();
		Game.GameState=Game.Playing;
		if (Game.error.showing) {add(Game.error);}
	}
	
	//TODO: See about making different layers for easier control.
	//TODO: See about optimizing the layers so that I can skip some to make this part faster.
	public void paintComponent(Graphics g) {
		//Creates the buffer image and graphic settings.
		Image buffimage = createImage(width, height);
		Graphics2D gg = (Graphics2D) buffimage.getGraphics();
		
		switch (Game.GameState) {
		case Game.TheMenu:
			gg.setColor(new Color(0,0,60));
			gg.fillRect(0, 0, width, height);
			gg.setColor(new Color(255,255,255));
		break;
		case Game.Playing:
			gg.drawImage(Game.img_menu[0], 0, 0, width, height, 32, 0, 64, 256, null);
			DrawTerrain(gg);
			DrawRanges(gg);
			DrawBuildings(gg);
			DrawUnits(gg);
			DrawSelector(gg);
			if (Game.input.MenuHack) {DrawSidebar(gg);}
		break;
		}
		g.drawImage(buffimage, 0, 0, this);
	}

	private void DrawTerrain(Graphics2D gg) {
		for (int y=0; y < Game.map.height; y++) {
			for (int x=0; x < Game.map.width; x++) {
				if (Game.view.Viewable(x,y)) {
					int xx = Game.map.map[y][x].x();
					int yy = Game.map.map[y][x].y();
					gg.drawImage(Game.img_tile, (x-Game.view.ViewX())*32, (y-Game.view.ViewY())*32, 
												(x-Game.view.ViewX())*32+32, (y-Game.view.ViewY())*32+32, 
												 xx*32, yy*32, xx*32+32, yy*32+32, null);
				}
			}
		}
	}
	/**This draws 4 different images (each corner) of the selector and moves them in/out in connection with the current frame*/
	private void DrawSelector(Graphics2D gg) {
		int x = Game.player.get(Game.btl.currentplayer).selectx;
		int y = Game.player.get(Game.btl.currentplayer).selecty;
		if (Game.view.Viewable(x,y)) {
			int off = (frame>5) ? frame/2+2 : 6-frame/2+2;
			gg.drawImage(Game.img_tile,
					(x-Game.view.ViewX())*32-off,   (y-Game.view.ViewY())*32-off,   
					(x-Game.view.ViewX())*32+10-off,   (y-Game.view.ViewY())*32+10-off,
					32*7, 32, 32*7+10, 32+10,null);
			gg.drawImage(Game.img_tile,
					(x-Game.view.ViewX())*32+22+off,   (y-Game.view.ViewY())*32-off,   
					(x-Game.view.ViewX())*32+32+off,   (y-Game.view.ViewY())*32+10-off,
					32*7+22, 32, 32*7+32, 32+10,null);
			gg.drawImage(Game.img_tile,
					(x-Game.view.ViewX())*32-off,  (y-Game.view.ViewY())*32+22+off,   
					(x-Game.view.ViewX())*32+10-off,   (y-Game.view.ViewY())*32+32+off,
					32*7, 32+22, 32*7+10, 32+32,null);
			gg.drawImage(Game.img_tile,
					(x-Game.view.ViewX())*32+22+off,  (y-Game.view.ViewY())*32+22+off,   
					(x-Game.view.ViewX())*32+32+off,   (y-Game.view.ViewY())*32+32+off,
					32*7+22, 32+22, 32*7+32, 32+32,null);
		}
	}
	/**this draws each building on the screen.*/
	private void DrawBuildings(Graphics2D gg) {
		for (buildings.Base bld : Game.builds) {
			if (Game.view.Viewable(bld.x,bld.y)) {
				int[] loc = bld.DrawMe();
				gg.drawImage(Game.img_city,
						(bld.x-Game.view.ViewX())*32,(bld.y-Game.view.ViewY())*32,(bld.x-Game.view.ViewX())*32+32,(bld.y-Game.view.ViewY())*32+32,
						loc[0]*32, loc[1]*32, loc[0]*32+32, loc[1]*32+32, null);
			}
		}
	}
	private void DrawUnits(Graphics2D gg) {
		for (units.Base chars : Game.units) {
			if (Game.view.Viewable(chars.x,chars.y)) {
				int[] loc = chars.DrawMe();
				gg.drawImage(Game.img_char,
					(chars.x-Game.view.ViewX())*32,(chars.y-Game.view.ViewY())*32,(chars.x-Game.view.ViewX())*32+32,(chars.y-Game.view.ViewY())*32+32,
					loc[0]*32,loc[1]*32,loc[0]*32+32,loc[1]*32+32,null);
			}
		}
	}
	
	/***/
	private void DrawSidebar(Graphics2D gg) {
		int offset = 514;
		switch (Game.btl.currentplayer) {
			case 0:gg.setColor(new Color(200,0,0));break;
			case 1:gg.setColor(new Color(0,0,200));break;
			case 2:gg.setColor(new Color(0,200,0));break;
			case 3:gg.setColor(new Color(200,200,0));break;
			default:gg.setColor(new Color(200,200,200));break;
		}
		gg.fillRect(offset-2, 0, width, height);
		gg.setColor(new Color(255,255,255));
		gg.drawImage(Game.img_menu[0], offset-2, 0, width, height, 0, 0, 32, 256, null);
		gg.drawString("Funds = " + Game.player.get(Game.btl.currentplayer).money,offset+4,128);
		
		DrawUnitInfo(gg, offset);
		DrawTerrainInfo(gg);
	}

	/**This draws the following things when applicable.
	 * 1) Move-able locations
	 * 2) Attackable locations
	 * 3) Unit name, health, image
	 * */
	private void DrawRanges(Graphics2D gg) {
		if (Game.player.get(Game.btl.currentplayer).unitselected) {
			units.Base unit = Game.units.get(Game.player.get(Game.btl.currentplayer).selectedunit);
			if (unit.moved&&!unit.acted) {
				for (int y = unit.y - unit.MaxAtkRange; y <= unit.y + unit.MaxAtkRange; y++) {
					for (int x = unit.x - unit.MaxAtkRange; x <= unit.x + unit.MaxAtkRange; x++) {
						if (Game.view.Viewable(x,y)) {
							if (unit.inrange(x,y)) {
								gg.drawImage(Game.img_tile,
										(x-Game.view.ViewX())*32,(y-Game.view.ViewY())*32,
										(x-Game.view.ViewX())*32+32,(y-Game.view.ViewY())*32+32,
										32*7,0,32*8,32,null);
							}
						}
					}
				}
			}
			else if (!unit.moved) {
				for (Point p : unit.map) {
					if (Game.view.Viewable(p.x,p.y)) {
						gg.drawImage(Game.img_tile,
								(p.x-Game.view.ViewX())*32,(p.y-Game.view.ViewY())*32,
								(p.x-Game.view.ViewX())*32+32,(p.y-Game.view.ViewY())*32+32,
								32*6,0,32*7,32,null);
					}
				}
				if (Game.pathing.ShowCost) {
					for (Pathfinding.PathNode node : Game.pathing.closedlist) {
						if (Game.view.Viewable(node.loc.x,node.loc.y)) {
							gg.drawString(node.cost + "",node.loc.x*32+5,node.loc.y*32+19);
						}
					}
				}
				if (Game.pathing.ShowHits) {
					for (int y = 0; y < Game.map.height; y++) {
						for (int x = 0; x < Game.map.width; x++) {
							if (Game.view.Viewable(x,y)) {
								gg.drawString(Game.pathing.maphits[y][x] + "",x*32 + 5,y*32 + 19);
							}
						}
					}
				}
			}
		}
	}
	private void DrawUnitInfo(Graphics2D gg, int offset) {
		players.Base plyer = Game.player.get(Game.btl.currentplayer);
		if (plyer.unitselected) {
			units.Base unit = Game.units.get(plyer.selectedunit);
			if (unit.acted) {
				gg.drawString("Finished.",offset,64);
			}
			else if (unit.moved&&!unit.acted) {
				gg.drawString("Attacking.",offset,64);
			}
			else if (!unit.moved) {
				gg.drawString("Moving",offset,64);
			}
			//UNIT SELECTION STUFFIES!
			int[] loc = unit.DrawMe();
			gg.drawImage(Game.img_char,
					offset,8,offset+32,8+32,
					loc[0]*32,loc[1]*32,loc[0]*32+32,loc[1]*32+32,null);
			gg.drawString(unit.nick + " = " + unit.health + " HP!",offset,52);
		}
	}
	private void DrawTerrainInfo(Graphics2D gg) {
		int x = Game.player.get(Game.btl.currentplayer).selectx;
		int y = Game.player.get(Game.btl.currentplayer).selecty;
		int xx = Game.map.map[y][x].x();
		int yy = Game.map.map[y][x].y();
		gg.drawImage(Game.img_tile, 520+32, 200, 520+32+32, 232, xx*32, yy*32, xx*32+32, yy*32+32, null);
		gg.drawString(x + " and " + y, 520+32, 190);
		
	}
}
