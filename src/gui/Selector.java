package gui;

import java.awt.Graphics2D;
import engine.Game;

/**
 * This draws 4 different images (each corner) of the selector and moves them in/out in connection with the current frame (at 1/2 speed) [Based off 12 max]
 * @author SergeDavid
 * @version 0.1
 */
public class Selector {
	public Selector(Graphics2D gg, int frame) {
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
}
