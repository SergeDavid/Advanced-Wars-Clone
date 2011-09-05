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
 * @version 0.1
 */
public class Ranges {
	//TODO: Change this to support texture packs.
	public Ranges(Graphics2D gg) {
		if (Game.player.get(Game.btl.currentplayer).unitselected) {
			units.Base unit = Game.units.get(Game.player.get(Game.btl.currentplayer).selectedunit);
			if (unit.moved&&!unit.acted) {
				Attacking(gg,unit);
			}
			else if (!unit.moved) {
				Moving(gg,unit);
				gg.setColor(new Color(255,255,255));
				if (Game.pathing.ShowCost) {ShowCost(gg);}
				if (Game.pathing.ShowHits) {ShowHits(gg);}
			}
		}
	}
	private void Attacking(Graphics2D gg, Base unit) {
		//If the unit disabled MoveAndShoot, then it won't display it's attack range since it can't attack.
		if (unit.x != unit.oldx && unit.y != unit.oldy && !unit.MoveAndShoot) {return;}
		
		//TODO: Maybe change this into a list of points like moving instead of playing with a block.
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
	private void Moving(Graphics2D gg, units.Base unit) {
		for (Point p : unit.map) {
			if (Game.view.Viewable(p.x,p.y)) {
				gg.drawImage(Game.img_tile,
						(p.x-Game.view.ViewX())*32,(p.y-Game.view.ViewY())*32,
						(p.x-Game.view.ViewX())*32+32,(p.y-Game.view.ViewY())*32+32,
						32*6,0,32*7,32,null);
			}
		}
	}
	private void ShowCost(Graphics2D gg) {
		for (Pathfinding.PathNode node : Game.pathing.closedlist) {
			if (Game.view.Viewable(node.loc.x,node.loc.y)) {
				gg.drawString(node.cost + "",(node.loc.x-Game.view.ViewX())*32+5,(node.loc.y-Game.view.ViewY())*32+19);
			}
		}
	}
	private void ShowHits(Graphics2D gg) {
		for (int y = 0; y < Game.map.height; y++) {
			for (int x = 0; x < Game.map.width; x++) {
				if (Game.view.Viewable(x,y)) {
					gg.drawString(Game.pathing.maphits[y][x] + "",(x-Game.view.ViewX())*32 + 5,(y-Game.view.ViewY())*32 + 19);
				}
			}
		}
	}
}
