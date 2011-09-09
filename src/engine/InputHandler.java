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
	private final int dev1 = KeyEvent.VK_NUMPAD1;
	private final int dev2 = KeyEvent.VK_NUMPAD2;
	private final int dev3 = KeyEvent.VK_NUMPAD3;
	private final int dev4 = KeyEvent.VK_NUMPAD4;
	private final int dev5 = KeyEvent.VK_NUMPAD5;
	private final int dev6 = KeyEvent.VK_NUMPAD6;
	private final int dev7 = KeyEvent.VK_NUMPAD7;
	private final int dev8 = KeyEvent.VK_NUMPAD8;
	private final int dev9 = KeyEvent.VK_NUMPAD9;
	private final int exit = KeyEvent.VK_ESCAPE;
	
	//Movement buttons
	private final int up = KeyEvent.VK_UP;
	private final int down = KeyEvent.VK_DOWN;
	private final int left = KeyEvent.VK_LEFT;
	private final int right = KeyEvent.VK_RIGHT;

	//Command buttons
	private final int select = KeyEvent.VK_Z;
	private final int cancel = KeyEvent.VK_X;
	private final int start = KeyEvent.VK_ENTER;
	
	//Mouse (right/left clicks)
	private final int main = MouseEvent.BUTTON1;
	private final int alt = MouseEvent.BUTTON1;
	
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
			if (i==cancel) {Game.player.get(Game.btl.currentplayer).Cancle();}
			if (i==start) {
				if (MenuHack) {Game.gms.CloseMenu();}
				else {
					new menus.Pause();
				}
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
