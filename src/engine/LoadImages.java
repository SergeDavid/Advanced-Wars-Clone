package engine;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**A very simple class that takes a nice big list of images and loads them into the game. It is separated from the main class to decrease size.*/
public class LoadImages {
	
	//These boolean and int's are used for texture packs.
	public boolean Bool_Unit;
	public boolean Bool_Terrain;
	public boolean Bool_City;
	public boolean Bool_Player;
	public boolean Bool_Menus;
	public int[] size_Unit = {256,256};
	public int[] size_Terrain = {256,128};
	public int[] size_City = {256,256};
	public int[] size_Player = {512,512};
	public int[] size_Menus = {64,256};
	
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
	public void LoadTexturePack() {
		ZipHandler zip = new ZipHandler("Test");
	    Game.img_char = TryNewImage("Units", zip, size_Unit);
	    Game.img_tile = TryNewImage("Terrain", zip, size_Terrain);
	    Game.img_city = TryNewImage("Cities", zip, size_City);
	   // Game.img_menu[0] = TryNewImage("Info", zip, size_Menus);
	}
	
	private Image TryNewImage(String path, ZipHandler zip, int[] size) {
	    try {
	    	InputStream in = new ByteArrayInputStream(zip.getEntry(path + ".png"));
	        BufferedImage ZipImg = ImageIO.read(in);
	        if (CompareSizes(size,ZipImg)) {
				return ResizeImage(ZipImg, size[0], size[1]);
			}
			else {
				return OldImage(path);
			}
	    }
	    catch (Exception e) {
	        return OldImage(path);
	    }
	}
	/**
	 * Compares the default image size with the new images size for custom texture packs.
	 * @param base = Size to compare too.
	 * @param width = New images width.
	 * @param height = New images height.
	 * @return = return true if they match
	 */
	private boolean CompareSizes(int[] base, Image img) {
		//TODO: change it to (width%base[0] == 0)
		if (base[0]!=img.getWidth(null)) {return false;}
		if (base[1]!=img.getHeight(null)) {return false;}
		return true;
	}
	/**
	 * 
	 * @param type = The name of the image to be changed
	 * @param width = the width the image is to become
	 * @param height = the height the image is to become
	 * @return = Returns the resized image.
	 */
	private Image ResizeImage(Image img, int width, int height) {
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
