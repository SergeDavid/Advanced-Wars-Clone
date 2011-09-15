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
		//TODO: Redesign it to allow the npc to grab if someone is in firing range faster and easier.
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
	
	/**Grabs the next unit in line, if none are found sets DoneUnits to true.*/
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
	/**Grabs the next city in line, if none are found sets DoneCities to true.*/
	private void NextCities(int me) {
		if (DoneCities) {return;}
		for (int i = CityCount; i < Game.builds.size(); i++) {
			if (Game.builds.get(i).owner == me && !Game.builds.get(i).Menu.equals("")) {
				CityCount = i;
				return;
			}
		}
		DoneCities = true;
	}

	private void HandleBuilding(buildings.Base bld) {
		if (!bld.Menu.equals("")) {
			if (!bld.Locked) {
				Random rand = new Random();
				if (rand.nextInt(2)==0 || Game.btl.day<3) {
					Game.btl.Buyunit(rand.nextInt(3), bld.x, bld.y);
					Game.player.get(bld.owner).selectx = bld.x;
					Game.player.get(bld.owner).selecty = bld.y;
				}
			}
		}
	}
	private void HandleUnit(Base unit) {
		unit.Pathing();
		if (unit.raider && unit.bld!=-1) {//Finds, moves, and captures a building if the unit can capture it.
			if (Game.builds.get(unit.bld).owner != unit.owner) {
				unit.action(unit.x, unit.y);
				Game.player.get(unit.owner).selectx = unit.x;
				Game.player.get(unit.owner).selecty = unit.y;
				return;
			}
		}
		if (unit.raider && BuildingInRange(unit)) {
			unit.move(building.x,building.y);
			unit.action(building.x,building.y);
			unit.acted=true;
			Game.player.get(unit.owner).selectx = unit.x;
			Game.player.get(unit.owner).selecty = unit.y;
		}
		else {
			if (WannaHug(unit)) {//Attack anyone near you.
				unit.acted=true;
				return;
			}
			else if (unit.map.size() <= 1) {//For when a unit can't move anywhere
				WannaHug(unit);
				unit.acted=true;
				return;
			}
			Random rand = new Random();
			for (int i = unit.map.size()-1; i >= 0; i--) {//Randomly selects somewhere to run.
				if (rand.nextInt(5)==0) {
					unit.move(unit.map.get(i).x, unit.map.get(i).y);
					Game.player.get(unit.owner).selectx = unit.x;
					Game.player.get(unit.owner).selecty = unit.y;
					
					WannaHug(unit);
					unit.acted=true;
					break;
				}
			}
		}
	}
	
	/**Looks for any enemies in range at the units current location by attacking each location in the attackable locations.*/
	private boolean WannaHug(units.Base unit) {
		for (int y = unit.y - unit.MaxAtkRange; y <= unit.y + unit.MaxAtkRange; y++) {
			for (int x = unit.x - unit.MaxAtkRange; x <= unit.x + unit.MaxAtkRange; x++) {
				if (unit.attack(x, y, true)) {
					return true;
				}
			}
		}
		// TODO Auto-generated method stub
		return false;
	}

	/**Finds a building in the unit's movement range.*/
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
