package engine;

import java.awt.Image;
import java.awt.Toolkit;

/**A very simple class that takes a nice big list of images and loads them into the game. It is separated from the main class to decrease size.*/
public class LoadImages {
	
	//These boolean and int's are used for texture packs.
	public boolean Bool_Unit;
	public boolean Bool_Terrain;
	public boolean Bool_City;
	public boolean Bool_Player;
	public int[] size_Unit = {512,512};
	public int[] size_Terrain = {512,512};
	public int[] size_City = {512,512};
	public int[] size_Player = {512,512};
	
	/**This will initialize the loading images area by only loading up the logo, the rest are called as the game loads different parts.*/
	public LoadImages() {
		Toolkit tool = Toolkit.getDefaultToolkit();
		Game.img_char = tool.getImage(getClass().getResource("/img/"+"Units"+".png"));
		Game.img_tile = tool.getImage(getClass().getResource("/img/"+"Terrain"+".png"));
		Game.img_city = tool.getImage(getClass().getResource("/img/"+"Cities"+".png"));
		//TODO: Add Commander images (Probably two, an ingame and menu version)
		//TODO: Add in menu images
		Game.img_menu[0] = tool.getImage(getClass().getResource("/img/"+"GameInfo"+".png"));
	}
	
	/**This loads all the images not used in the startup screen*/
	public void LoadMenu() {}
	public void LoadGame() {}
	public void LoadCredit() {}
	
	public void UseTextures() {
		if (CompareSizes(size_Unit,512,512)) {
			Game.img_char = ResizeImage("Units", size_Unit[0], size_Unit[1]);
		}
		else {
			Game.img_char = OldImage("Units");
		}
	}
	
	/**
	 * Compares the default image size with the new images size for custom texture packs.
	 * @param base = Size to compare too.
	 * @param width = New images width.
	 * @param height = New images height.
	 * @return = return true if they match
	 */
	private boolean CompareSizes(int[] base, int width, int height) {
		//TODO: change it to (width%base[0] == 0)
		if (base[0]!=width) {return false;}
		if (base[1]!=height) {return false;}
		return true;
	}
	/**
	 * 
	 * @param type = The name of the image to be changed
	 * @param width = the width the image is to become
	 * @param height = the height the image is to become
	 * @return = Returns the resized image.
	 */
	private Image ResizeImage(String type, int width, int height) {
		Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("packs/" + type + ".png"));
		//TODO: Resize image to default size using Graphics2D or something.
		return img;
	}
	/**
	 * Loads the default image into the game.
	 * @param type = the name of the image file to be used.
	 * @return = returns the image to be used.
	 */
	private Image OldImage(String type) {
		return Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/" + type + ".png"));
	}
}
