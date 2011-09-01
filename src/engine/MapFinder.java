package engine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MapFinder {
	
	final String path = "maps/";

	public ArrayList<String> GrabMaps() {
		String name;
		
		File folder = new File(path);
		File[] filelist = folder.listFiles();
		List<String> maps = new ArrayList<String>();

		for (int i = 0; i < filelist.length; i++) {
			if (filelist[i].isFile()) {
				name = filelist[i].getName();
				if (name.endsWith(".txt") || name.endsWith(".TXT")) {
					Game.gui.maps_model.addElement(name.substring(0, name.length()-4));
					maps.add(name);
				}
			}
		}
		return (ArrayList<String>) maps;
	}
}
