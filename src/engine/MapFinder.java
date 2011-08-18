package engine;

import java.awt.Dimension;
import java.awt.Insets;
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
		//This apparently makes it show up...
		Insets insets = Game.gui.getInsets();
		Dimension size = Game.gui.maps_list.getPreferredSize();
		Game.gui.maps_list.setBounds(insets.left, insets.top, size.width, size.height);
		return (ArrayList<String>) maps;
	}
}
