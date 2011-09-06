package engine;

import java.awt.Point;
import java.util.Random;
import units.Base;

public class ComputerBrain {
	
	private Point building;
	public int period = 3;
	public boolean finished;
	
	public void UseBrain(players.Base ply) {
		finished = false;
		period = 3;
		//Player power use, they use it as soon as they can. TODO: Let them build up to level 2.
		if (ply.power>=ply.level2) {ply.UsePower(false);}
		else if (ply.power>=ply.level1) {ply.UsePower(true);}
		
		//Handles units first, then buildings (so they buy units based on what is needed after shit gets blown up)
		for (units.Base unit : Game.units) {
			if (unit.owner == Game.btl.currentplayer) {
				ply.selectx = unit.x;
				ply.selecty = unit.y;
				HandleUnit(unit);
			}
		}
		for (buildings.Base bld : Game.builds) {
			if (bld.owner == Game.btl.currentplayer) {
				ply.selectx = bld.x;
				ply.selecty = bld.y;
				HandleBuilding(bld);
			}
		}
		finished = true;
	}

	private void HandleBuilding(buildings.Base bld) {
		if (NoUnit(bld.x,bld.y)) {
			Game.btl.Buyunit(0, bld.x, bld.y);
		}
	}

	private boolean NoUnit(int x, int y) {
		for (units.Base unit : Game.units) {
			if (unit.x == x && unit.y == y) {
				return false;
			}
		}
		return true;
	}

	private void HandleUnit(Base unit) {
		//if (!unit.acted) {//Change when only certain units can capture.
		if (unit.FindBuilding()!=null) {
			unit.capture(unit.x, unit.y);
			return;
		}
		
		if (BuildingInRange(unit)) {
			System.out.println("Blargh!");//----------------------------------------------------
			unit.move(building.x,building.y);
			unit.action(building.x,building.y);
		}
		else {
			Random rand = new Random();
			unit.Pathing();
			for (int i = 0; i < unit.map.size(); i++) {
				if (rand.nextInt(5)==0) {
					unit.move(unit.map.get(i).x, unit.map.get(i).y);
					break;
				}
			}
		}
		// Basic Brain Functions...
		//   - Else If it can attack something worth while, move and attack it. (Don't move if MoveAndShoot is false)
		//   - Else Move towards an enemy city / unit
	}

	private boolean BuildingInRange(units.Base unit) {
		for (int i = 0; i < unit.map.size(); i++) {
			for (buildings.Base bld : Game.builds) {
				if (bld.owner!=unit.owner) {
					if (bld.x == unit.map.get(i).x && bld.y == unit.map.get(i).y) {
						building = unit.map.get(i);
						return true;
					}
				}
			}
		}
		return false;
	}

	public void NextMove() {
		if (period<=0) {Game.btl.EndTurn();}
		period--;
	}
}
