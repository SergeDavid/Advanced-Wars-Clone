package engine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**This handles all of the mouse, key, and action listeners.*/
public class InputHandler implements KeyListener,MouseListener,ActionListener,MouseMotionListener {
	//Keyboard shortcuts, might change this method for the different dev ones and any character shortcuts.
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
	
	//KeyShortcuts
	public int up = KeyEvent.VK_W;
	public int down = KeyEvent.VK_S;
	public int left = KeyEvent.VK_A;
	public int right = KeyEvent.VK_D;
	public int up2 = KeyEvent.VK_UP;
	public int down2 = KeyEvent.VK_DOWN;
	public int left2 = KeyEvent.VK_LEFT;
	public int right2 = KeyEvent.VK_RIGHT;
	public int enter = KeyEvent.VK_SPACE;
	
	public int select = KeyEvent.VK_Z;
	public int cancle = KeyEvent.VK_X;
	public int start = KeyEvent.VK_ENTER;
	
	//Mouse (right/left clicks)
	public int main = MouseEvent.BUTTON1;
	public int alt = MouseEvent.BUTTON1;
	
	public InputHandler() {
		Game.gui.addKeyListener(this);
		Game.gui.addMouseListener(this);
		Game.gui.addMouseMotionListener(this);
		Game.gui.Join.addActionListener(this);
		Game.gui.Exit.addActionListener(this);
		
		Game.gui.ply_endturn.addActionListener(this);
	}
	
	public void keyPressed(KeyEvent e) {
		int i=e.getKeyCode();
		if (i==exit) {System.exit(0);}
		
		if (i==up||i==up2) {Game.selecty--;if (Game.selecty<0) {Game.selecty++;}}
		if (i==down||i==down2) {Game.selecty++;if (Game.selecty>=Game.map.height) {Game.selecty--;}}
		if (i==left||i==left2) {Game.selectx--;if (Game.selectx<0) {Game.selectx++;}}
		if (i==right||i==right2) {Game.selectx++;if (Game.selectx>=Game.map.width) {Game.selectx--;}}
		
		//TODO: Refine this to work for the selected unit during a players turn.
		if (i==select) {Game.player.get(Game.btl.currentplayer).Action();}
		if (i==cancle) {Game.player.get(Game.btl.currentplayer).cancle();}
		
		if (i==start) {Game.MenuButton();}
		
		if (i==dev1) {Game.gui.LoginScreen();}
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
		Object s = e.getSource();
		if (s==Game.gui.Join) {
			Game.btl = new Battle(Game.map.MapNames.get(Game.gui.maps_list.getSelectedIndex()));
			Game.gui.InGameScreen();
		}
		else if (s==Game.gui.Exit) {System.exit(0);}
		else if (s==Game.gui.ply_endturn) {Game.btl.EndTurn();}
		Game.gui.requestFocusInWindow();
	}

	@Override public void mouseDragged(MouseEvent arg0) {}

	@Override public void mouseMoved(MouseEvent e) {
		Game.mouseX=e.getX();
		Game.mouseY=e.getY();
	}
}
