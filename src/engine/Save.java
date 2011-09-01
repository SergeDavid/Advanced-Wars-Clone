package engine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Properties;

public class Save {
	
	final String path = "saves/";
	
	public void SaveGame() {
		
		File directory = new File(path);
		if (!directory.exists()) {
			if (directory.mkdir()) {Game.error.ShowError("The " + path + " directory has been created.");} 
	    	else {Game.error.ShowError("Cannot create the save directory. " + path + "");return;}
		}
		try {
			FileWriter fstream = new FileWriter(path + "savegame.properties");
		    BufferedWriter out = new BufferedWriter(fstream);
		    out.write("# A testing save file format for my advanced wars project, using property file as it is a list of properties...\n" +
		    		  "# When loading map, skip the unit stage (will load units from here, maybe buildings too with health)" +	
		    		  "# I might change this to something close to the map.txt files\n" +
		    		  "Map = " + Game.btl.mapname + "\n" +
		    		  "CurrentPlayer = " + Game.btl.currentplayer + "\n" +
		    		  "Days = " + Game.btl.day + "\n" +
		    		  "# type,money,kills,loses,power,currentlyusingpower,selectorX/Y,etc.\n" +
		    		  "Player1 = 400\n" +
		    		  "Player2 = same.\n" +
		    		  "Buildings = owner,health,type*#\n" +
		    		  "Units = owner,type,health,ammo,fuel,x,y,etc.*#\n");
		    out.close();
		} 
		catch (Exception e) {Game.error.ShowError("Save Failed:" + e.getMessage());}
	}
	
	public void LoadGame() {
		try {
			Properties configFile = new Properties();
			configFile.load(new FileInputStream(System.getProperty("user.dir") + "/" + path + "savegame.properties"));
			Game.btl.NewGame(configFile.getProperty("Map"));
			Game.btl.currentplayer = Integer.parseInt(configFile.getProperty("CurrentPlayer")); 
			Game.player.get(0).money = Integer.parseInt(configFile.getProperty("Player1")); 
			Game.gui.InGameScreen();
		} 
		catch (Exception e) {Game.error.ShowError("Saved game failed to load."); e.printStackTrace();}
	}
}
