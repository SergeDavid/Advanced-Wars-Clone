package engine;

import java.awt.Point;
import java.util.Random;
import units.Base;

public class ComputerBrain {
	
	private Point building;
	public boolean finished;
	
	public boolean DoneUnits;
	public int UnitCount;
	
	public boolean DoneCities;
	public int CityCount;

	/**This method is hit every game loop for npcs and handles a single unit / city so it is less heavy on the game.*/
	public void ThinkDamnYou(players.Base ply) {
		if (finished) {
			finished = false;
			DoneCities = DoneUnits = false;
			UnitCount = CityCount = 0;
			if (ply.power>=ply.level2) {ply.UsePower(false);}
			else if (ply.power>=ply.level1) {ply.UsePower(true);}
		}
		
		NextUnit(Game.btl.currentplayer);
		NextCities(Game.btl.currentplayer);
		
		if (!DoneUnits) {
			units.Base unit = Game.units.get(UnitCount);
			UnitCount++;
			if (unit.owner == Game.btl.currentplayer) {
				HandleUnit(unit);
			}
		}
		else if (!DoneCities) {
			buildings.Base bld = Game.builds.get(CityCount);
			CityCount++;
			if (bld.owner == Game.btl.currentplayer) {
				HandleBuilding(bld);
			}
		}
		
		else {
			finished = true;
			Game.btl.EndTurn();
		}
	}
	
	private void NextUnit(int me) {
		if (DoneUnits) {return;}
		for (int i = UnitCount; i < Game.units.size(); i++) {
			if (Game.units.get(i).owner == me) {
				UnitCount = i;
				return;
			}
		}
		DoneUnits = true;
	}
	private void NextCities(int me) {
		if (DoneCities) {return;}
		for (int i = CityCount; i < Game.builds.size(); i++) {
			if (Game.builds.get(i).owner == me && !Game.builds.get(i).Menu.isEmpty()) {
				CityCount = i;
				return;
			}
		}
		DoneCities = true;
	}

	private void HandleBuilding(buildings.Base bld) {
		if (!bld.Menu.isEmpty()) {
			if (NoUnit(bld.x,bld.y)) {
				Random rand = new Random();
				if (rand.nextInt(2)==0 || Game.btl.day<3) {
					Game.btl.Buyunit(rand.nextInt(3), bld.x, bld.y);
					Game.player.get(bld.owner).selectx = bld.x;
					Game.player.get(bld.owner).selecty = bld.y;
				}
			}
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
		unit.Pathing();
		//If the unit can capture cities and is on a city, it does it then returns so the rest of this code is not ran.
		if (unit.raider && unit.FindBuilding()!=null) {
			unit.action(unit.x, unit.y);
			Game.player.get(unit.owner).selectx = unit.x;
			Game.player.get(unit.owner).selecty = unit.y;
			return;
		}
		if (BuildingInRange(unit)) {
			unit.move(building.x,building.y);
			unit.action(building.x,building.y);
			Game.player.get(unit.owner).selectx = unit.x;
			Game.player.get(unit.owner).selecty = unit.y;
		}
		else {
			Random rand = new Random();
			for (int i = 0; i < unit.map.size(); i++) {
				if (rand.nextInt(5)==0) {
					unit.move(unit.map.get(i).x, unit.map.get(i).y);
					Game.player.get(unit.owner).selectx = unit.x;
					Game.player.get(unit.owner).selecty = unit.y;
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
}
