package engine;

import java.io.File;
import javax.swing.DefaultListModel;

/**
 * This grabs a list of names of files from a set directory next to the Jar and returns a DefaultListModel for use in the GUI minus the file type. (.txt as an example)
 * Loading the file is handled somewhere else by using the JList.getSelectedValue() to retrieve the files name.
 * Currently does not support any files that don't end in lower-case (.txt == good, .ZIP == bad)
 * @author SergeDavid
 * @version 0.3
 */
public class FileFinder {
	final String mappath = "maps";
	final String packpath = "mods";
	final String modpath = "mods";

	/**This looks for .txt files and returns the list of names.*/
	public DefaultListModel GrabMaps() {
		String name;
		File folder = new File(mappath);
		if (folder.mkdir()) {
			Game.error.ShowError("The " + mappath + " folder was created.");
		    //TODO: Transfer pre-made map files into here, or include src/maps/ to the list of maps somehow.
		}
		File[] filelist = folder.listFiles();
		DefaultListModel ListModel = new DefaultListModel();
		for (int i = 0; i < filelist.length; i++) {
			if (filelist[i].isFile()) {
				name = filelist[i].getName();
				if (name.endsWith(".txt")) {
					ListModel.addElement(name.substring(0, name.length()-4));
				}
			}
		}
		return ListModel;
	}
	/**This looks for .zip files and returns the list of names.*/
	public DefaultListModel GrabPacks() {
		String name;
		File folder = new File(packpath);
		if (folder.mkdir()) {Game.error.ShowError("The " + packpath + " folder was created.");}
		File[] filelist = folder.listFiles();
		DefaultListModel ListModel = new DefaultListModel();
		for (int i = 0; i < filelist.length; i++) {
			if (filelist[i].isFile()) {
				name = filelist[i].getName();
				if (name.endsWith(".zip")) {
					ListModel.addElement(name.substring(0, name.length()-4));
				}
			}
		}
		return ListModel;
	}
	/**This looks for .properties files and returns the list of names.*/
	public DefaultListModel GrabMods() {
		String name;
		File folder = new File(modpath);
		if (folder.mkdir()) {Game.error.ShowError("The " + modpath + " folder was created.");}
		File[] filelist = folder.listFiles();
		DefaultListModel ListModel = new DefaultListModel();
		for (int i = 0; i < filelist.length; i++) {
			if (filelist[i].isFile()) {
				name = filelist[i].getName();
				if (name.endsWith(".properties")) {
					ListModel.addElement(name.substring(0, name.length()-4));
				}
			}
		}
		return ListModel;
	}
}
