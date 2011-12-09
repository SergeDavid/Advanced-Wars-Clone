package menus;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import engine.Game;

public class EditorStuff implements ActionListener {

	JButton[] Units = new JButton[10];
	JButton[] Cities = new JButton[10];
	JButton[] Tiles = new JButton[10];
	JButton Next = new JButton("Next");
	JButton Prev = new JButton("Prev");
	JButton Return = new JButton("Return");
	
	public EditorStuff() {
		Prev.addActionListener(this);
		Next.addActionListener(this);
		Return.addActionListener(this);
		Show();
	}
	private void Show() {
		Game.gui.removeAll();
		Point size = MenuHandler.PrepMenu(260,140);
		Prev.setBounds(size.x+190, size.y+100, 60, 24);
		Next.setBounds(size.x+10, size.y+100, 60, 24);
		Return.setBounds(size.x+80, size.y+100, 100, 24);
		
		if (Game.edit.owner > 0) {
			for (int i = 0; i < Game.displayU.size(); i++) {
				Units[i] = new JButton(ButtonImage(i,0,Type.UNIT));
				Units[i].addActionListener(this);
				Units[i].setBounds(size.x+10+32*i, size.y+10, 32, 32);
				Game.gui.add(Units[i]);
			}
		}
		for (int i = 0; i < Game.displayB.size(); i++) {
			Cities[i] = new JButton(ButtonImage(i,0,Type.CITY));
			Cities[i].addActionListener(this);
			Cities[i].setBounds(size.x+10+32*i, size.y+40, 32, 32);
			Game.gui.add(Cities[i]);
		}
		int use = 0;//Hack so people can't place cities.
		for (int i = 0; i < Game.map.tiles.size(); i++) {
			if (!Game.map.tiles.get(i).building()) {
				Tiles[i] = new JButton(ButtonImage(Game.map.tiles.get(i).x,Game.map.tiles.get(i).y,Type.TILE));
				Tiles[i].addActionListener(this);
				Tiles[i].setBounds(size.x+10+32*use, size.y+70, 32, 32);
				Game.gui.add(Tiles[i]);
				use++;
			}
		}
		Game.gui.add(Prev);
		Game.gui.add(Next);
		Game.gui.add(Return);
	}
	
	enum Type {UNIT,CITY,TILE};
	private ImageIcon ButtonImage(int x, int y, Type type) {
		ImageIcon boo = new ImageIcon();
		BufferedImage buffimage = 
				new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
		Graphics2D gg = (Graphics2D) buffimage.getGraphics();
		int plyer = Game.edit.owner;
		switch (type) {//TODO: Find out how to fix the city/unit images from not displaying on the first load in some cases.
			case UNIT:
				plyer = (plyer-1)*2;
				gg.drawImage(Game.img_char, 0, 0, 32, 32, x*32, plyer*32, x*32+32, plyer*32+32, null);
			break;
			case CITY:
				gg.drawImage(Game.img_city, 0, 0, 32, 32, x*32, plyer*32, x*32+32, plyer*32+32, null);
			break;
			case TILE:
				gg.drawImage(Game.img_tile, 0, 0, 32, 32, x*32, y*32, x*32+32, y*32+32, null);
			break;
		}
		boo.setImage(buffimage);
		return boo;
	}
	
	@Override public void actionPerformed(ActionEvent e) {
		Object s = e.getSource();
		if (s==Return) {
			MenuHandler.CloseMenu();
		}
		else if (s==Prev) {
			Game.edit.ChangePlayer(false);
		}
		else if (s==Next) {
			Game.edit.ChangePlayer(true);
		}
		for (int i = 0; i < Game.displayU.size(); i++) {
			if (s==Units[i]) {
				Game.edit.pick = engine.Editor.Type.UNIT;
				Game.edit.id = i;
				MenuHandler.CloseMenu();
			}
		}
		for (int i = 0; i < Game.displayB.size(); i++) {
			if (s==Cities[i]) {
				Game.edit.pick = engine.Editor.Type.CITY;
				Game.edit.id = i;
				MenuHandler.CloseMenu();
			}
		}
		for (int i = 0; i < Game.map.tiles.size(); i++) {
			if (s==Tiles[i]) {
				Game.edit.pick = engine.Editor.Type.TILE;
				Game.edit.id = i;
				MenuHandler.CloseMenu();
			}
		}
		
	}
}
