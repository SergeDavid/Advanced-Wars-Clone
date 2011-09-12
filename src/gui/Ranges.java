package gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import units.Base;
import engine.Game;
import engine.Pathfinding;

/**
 * Depending on if a unit is selected by the current player and the units state, it will draw the movement range (based on path finding outcome).
 * with the inclusion of two development settings to display times a tile has been hit in path finding, or just the tiles cost.
 * It will also draw the attackable locations depending on the units max and min attack ranges.
 * @author SergeDavid
 * @version 0.3
 */
public class Ranges {
	public static void Draw(Graphics2D gg, int resize) {
		if (Game.player.get(Game.btl.currentplayer).unitselected) {
			int size = (int) Math.pow(2, Game.load.Times_Extras);
			units.Base unit = Game.units.get(Game.player.get(Game.btl.currentplayer).selectedunit);
			if (unit.moved&&!unit.acted) {
				Attacking(gg,unit,size,resize);
			}
			else if (!unit.moved) {
				Moving(gg,unit,size,resize);
				gg.setColor(new Color(255,255,255));
				if (Game.pathing.ShowCost) {ShowCost(gg,resize);}
				if (Game.pathing.ShowHits) {ShowHits(gg,resize);}
			}
		}
	}
	private static void Attacking(Graphics2D gg, Base unit, int size, int resize) {
		//If the unit disabled MoveAndShoot, then it won't display it's attack range since it can't attack.
		if (unit.x != unit.oldx && unit.y != unit.oldy && !unit.MoveAndShoot) {return;}
		
		//TODO: Maybe change this into a list of points like moving instead of playing with a block.
		for (int y = unit.y - unit.MaxAtkRange; y <= unit.y + unit.MaxAtkRange; y++) {
			for (int x = unit.x - unit.MaxAtkRange; x <= unit.x + unit.MaxAtkRange; x++) {
				if (Game.view.Viewable(x,y)) {
					if (unit.inrange(x,y)) {
						gg.drawImage(Game.img_exts,
								(x-Game.view.ViewX())*resize,(y-Game.view.ViewY())*resize,
								(x-Game.view.ViewX())*resize+resize,(y-Game.view.ViewY())*resize+resize,
								0,0,size,size,null);
					}
				}
			}
		}
	}
	private static void Moving(Graphics2D gg, units.Base unit, int size, int resize) {
		for (Point p : unit.map) {
			if (Game.view.Viewable(p.x,p.y)) {
				gg.drawImage(Game.img_exts,
						(p.x-Game.view.ViewX())*resize,(p.y-Game.view.ViewY())*resize,
						(p.x-Game.view.ViewX())*resize+resize,(p.y-Game.view.ViewY())*resize+resize,
						0,size,size,size*2,null);
			}
		}
	}
	private static void ShowCost(Graphics2D gg, int size) {
		for (Pathfinding.PathNode node : Game.pathing.closedlist) {
			if (Game.view.Viewable(node.loc.x,node.loc.y)) {
				gg.drawString(node.cost + "",(node.loc.x-Game.view.ViewX())*size+size/4,(node.loc.y-Game.view.ViewY())*size+size/2);
			}
		}
	}
	private static void ShowHits(Graphics2D gg, int size) {
		for (int y = 0; y < Game.map.height; y++) {
			for (int x = 0; x < Game.map.width; x++) {
				if (Game.view.Viewable(x,y)) {
					gg.drawString(Game.pathing.maphits[y][x] + "",(x-Game.view.ViewX())*size + size/4,(y-Game.view.ViewY())*size + size/2);
				}
			}
		}
	}
}
