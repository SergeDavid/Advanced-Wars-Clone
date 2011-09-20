package engine;

import java.awt.Point;

/**
 * This controls what you can see in game (on the map) and moving around along it.
 * Place MoveView(); in the game-loop (100ms) to automate it (Teleporting across the map between switching viewed units / turns is automatic)
 * Use Viewable(x,y); to check if something can be seen on screen and ViewX(); / ViewY(); to grab the offsets (in case it changes from a direct copy + paste).
 * @author SergeDavid
 * @version 0.2
 */
public class ViewPoint {
	Point Loc = new Point(0,0);//Location of the viewpoint
	final int expanded = 3;//This is the max distance from the edge of the map that the viewpoint can go.
	final int speed = 1;
	
	public final int width = 20;//How wide / tall the viewpoint is (how far from the x/y should the )
	final int height = 12;
	
	/**
	 * Place this in a the game loop (100ms) to update the view-port automatically.
	 * Currently it does not support speeds other then 1.0 (You'll get a mostly never ending +1/-1 view-port shake)
	 */
	public void MoveView() {
		int y = 0;
		int x = 0;
		if (Game.GameState == Game.State.PLAYING) {
			y = Game.player.get(Game.btl.currentplayer).selecty;
			x = Game.player.get(Game.btl.currentplayer).selectx;
		}
		else {
			y = Game.edit.selecty;
			x = Game.edit.selectx;
		}
		MoveNorth(y);
		MoveSouth(y);
		MoveEast(x);
		MoveWest(x);
	}
	
	/**
	 * This is a check to see if something in the game map is viewable on screen.
	 * @param x = x location of unit/building/tile to compare
	 * @param y = y location of object to compare
	 * @return = True when in view range, false when outside of it. (saves time so you don't have to render images)
	 */
	public boolean Viewable(int x, int y) {
		if (y>=Loc.y && y<Loc.y+height) {
			if (x>=Loc.x && x<Loc.x+width) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Simple lame getter.
	 * @return X location of view-port to offset anything being drawn on the map.
	 */
	public int ViewX() {return Loc.x;}
	/**
	 * Simple lame getter.
	 * @return Y location of view-port to offset anything being drawn on the map.
	 */
	public int ViewY() {return Loc.y;}
	
	//These move the viewpoint in the correct direction at a set speed, used internally through MoveView();
	private void MoveNorth(int y) {
		if (y<Loc.y+expanded) {
			Loc.y-=speed;
		}
	}
	private void MoveSouth(int y) {
		if (y>Loc.y+height-expanded) {
			Loc.y+=speed;
		}
	}
	private void MoveEast(int x) {
		if (x<Loc.x+width-expanded) {
			Loc.x-=speed;
		}
	}
	private void MoveWest(int x) {
		if (x>Loc.x+expanded) {
			Loc.x+=speed;
		}
	}

}