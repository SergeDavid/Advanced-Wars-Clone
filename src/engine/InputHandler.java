package engine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Keyboard handling for the game along with the mouse setup for game handling.
 * Menus are being moved to gui.gms
 * @author SergeDavid
 * @version 0.1
 */
@SuppressWarnings("unused")
public class InputHandler implements KeyListener,MouseListener,ActionListener {
	
	//Development buttons and the exit game button (escape key)
	private int dev1 = KeyEvent.VK_NUMPAD1;
	private int dev2 = KeyEvent.VK_NUMPAD2;
	private int dev3 = KeyEvent.VK_NUMPAD3;
	private int dev4 = KeyEvent.VK_NUMPAD4;
	private int dev5 = KeyEvent.VK_NUMPAD5;
	private int dev6 = KeyEvent.VK_NUMPAD6;
	private int dev7 = KeyEvent.VK_NUMPAD7;
	private int dev8 = KeyEvent.VK_NUMPAD8;
	private int dev9 = KeyEvent.VK_NUMPAD9;
	private int exit = KeyEvent.VK_ESCAPE;
	
	//Movement buttons
	private int up = KeyEvent.VK_UP;
	private int down = KeyEvent.VK_DOWN;
	private int left = KeyEvent.VK_LEFT;
	private int right = KeyEvent.VK_RIGHT;

	//Command buttons
	private int select = KeyEvent.VK_Z;
	private int cancle = KeyEvent.VK_X;
	private int start = KeyEvent.VK_ENTER;
	
	//Mouse (right/left clicks)
	private int main = MouseEvent.BUTTON1;
	private int alt = MouseEvent.BUTTON1;
	
	public boolean MenuHack;
	
	public InputHandler() {
		Game.gui.addKeyListener(this);
		Game.gui.addMouseListener(this);
		Game.gui.Join.addActionListener(this);
		Game.gui.Load.addActionListener(this);
		Game.gui.Exit.addActionListener(this);
	}
	
	int DevPathing = 1;
	public void keyPressed(KeyEvent e) {
		int i=e.getKeyCode();
		if (i==exit) {System.exit(0);}
		if (Game.GameState==Game.State.PLAYING) {
			players.Base ply = Game.player.get(Game.btl.currentplayer);
			if (i==up) {ply.selecty--;if (ply.selecty<0) {ply.selecty++;}}
			if (i==down) {ply.selecty++;if (ply.selecty>=Game.map.height) {ply.selecty--;}}
			if (i==left) {ply.selectx--;if (ply.selectx<0) {ply.selectx++;}}
			if (i==right) {ply.selectx++;if (ply.selectx>=Game.map.width) {ply.selectx--;}}
			if (i==select) {Game.btl.Action();}
			if (i==cancle) {Game.player.get(Game.btl.currentplayer).Cancle();}
			if (i==start) {
				if (MenuHack) {Game.gms.CloseMenu();}
				else {
					new menus.Pause();
				}
				//else {Game.gui.gms.OpenMenu("Pause");}
			}
		}
		
		if (i==dev1) {Game.gui.LoginScreen();}
		if (i==dev2) {Game.load.LoadTexturePack();}
		if (i==dev3) {
			DevPathing++;
			switch (DevPathing) {
				case 1:Game.pathing.ShowCost=false;break;
				case 2:Game.pathing.ShowHits=true;break;
				case 3:Game.pathing.ShowHits=false;Game.pathing.ShowCost=true;DevPathing=0;break;
			}
		}
		if (i==dev4) {Game.btl.EndTurn();}
		if (i==dev5) {Game.player.get(Game.btl.currentplayer).npc = !Game.player.get(Game.btl.currentplayer).npc; Game.btl.EndTurn();}
		if (i==dev6) {Game.finder.GrabMods();}
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
			Game.btl.NewGame(Game.gui.maps_list.getSelectedValue() + "");
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
