package engine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**This handles all of the mouse, key, and action listeners.*/
public class InputHandler implements KeyListener,MouseListener,ActionListener {
	
	//Development buttons and the exit game button (escape key)
	public int dev1 = KeyEvent.VK_NUMPAD1;
	public int dev2 = KeyEvent.VK_NUMPAD2;
	public int dev3 = KeyEvent.VK_NUMPAD3;
	public int dev4 = KeyEvent.VK_NUMPAD4;
	public int dev5 = KeyEvent.VK_NUMPAD5;
	public int dev6 = KeyEvent.VK_NUMPAD6;
	public int dev7 = KeyEvent.VK_NUMPAD7;
	public int dev8 = KeyEvent.VK_NUMPAD8;
	public int dev9 = KeyEvent.VK_NUMPAD9;
	public int exit = KeyEvent.VK_ESCAPE;
	
	//Movement buttons
	public int up = KeyEvent.VK_UP;
	public int down = KeyEvent.VK_DOWN;
	public int left = KeyEvent.VK_LEFT;
	public int right = KeyEvent.VK_RIGHT;

	//Command buttons
	public int select = KeyEvent.VK_Z;
	public int cancle = KeyEvent.VK_X;
	public int start = KeyEvent.VK_ENTER;
	
	//Mouse (right/left clicks)
	public int main = MouseEvent.BUTTON1;
	public int alt = MouseEvent.BUTTON1;
	
	public boolean MenuHack;
	
	public InputHandler() {
		Game.gui.addKeyListener(this);
		Game.gui.addMouseListener(this);
		Game.gui.Join.addActionListener(this);
		Game.gui.Load.addActionListener(this);
		Game.gui.Exit.addActionListener(this);
	}
	
	public void keyPressed(KeyEvent e) {
		int i=e.getKeyCode();
		if (i==exit) {System.exit(0);}
		if (Game.GameState==Game.Playing) {
			players.Base ply = Game.player.get(Game.btl.currentplayer);
			if (i==up) {ply.selecty--;if (ply.selecty<0) {ply.selecty++;}}
			if (i==down) {ply.selecty++;if (ply.selecty>=Game.map.height) {ply.selecty--;}}
			if (i==left) {ply.selectx--;if (ply.selectx<0) {ply.selectx++;}}
			if (i==right) {ply.selectx++;if (ply.selectx>=Game.map.width) {ply.selectx--;}}
			if (i==select) {Game.btl.Action();}
			if (i==cancle) {Game.player.get(Game.btl.currentplayer).Cancle();}
			if (i==start) {
				if (MenuHack) {Game.gui.gms.CloseMenu();}
				else {Game.gui.gms.OpenMenu("Pause");}
			}
		}
		
		if (i==dev1) {Game.gui.LoginScreen();}
		if (i==dev4) {Game.btl.EndTurn();}
		if (i==dev6) {Game.pathing.ShowCost=!Game.pathing.ShowCost;}
		if (i==dev3) {Game.pathing.ShowHits=!Game.pathing.ShowHits;}
	}
	public void keyReleased(KeyEvent arg0) {}
	public void keyTyped(KeyEvent arg0) {}
	public void mousePressed() {}
	public void mouseClicked(MouseEvent arg0) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}

	@Override
	public void actionPerformed(ActionEvent e) {
		Game.gui.requestFocusInWindow();
		Object s = e.getSource();
		if (s==Game.gui.Join) {
			Game.btl.NewGame(Game.map.MapNames.get(Game.gui.maps_list.getSelectedIndex()));
			Game.gui.InGameScreen();
		}
		else if (s==Game.gui.Load) {
			Game.save.LoadGame();
		}
		else if (s==Game.gui.Exit) {
			System.exit(0);
		}
	}
}
