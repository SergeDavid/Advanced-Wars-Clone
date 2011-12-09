package engine;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * A very simple class that takes a nice big list of images and loads them into the game. It is separated from the main class to decrease size.
 * This also contains the functions for using custom texture packages.
 * @author SergeDavid
 * @version 0.2
 */
public class LoadImages {
	
	private int[] size_Unit = {256,256};
	private int[] size_Terrain = {256,128};
	private int[] size_City = {256,256};
	private int[] size_Player = {128,128};
	//private int[] size_Menus = {64,256};//To add
	private int[] size_Extras = {128,128};
	
	public int Times_Unit = 5;
	public int Times_Terrain = 5;
	public int Times_City = 5;
	public int Times_Player = 5;
	//public int Times_Menus = 5;
	public int Times_Extras = 5;
	
	/**This will initialize the different sprite sheets/atlas*/
	public LoadImages() {
		Toolkit tool = Toolkit.getDefaultToolkit();
		Game.img_char = tool.getImage(getClass().getResource("/img/"+"Units"+".png"));
		Game.img_tile = tool.getImage(getClass().getResource("/img/"+"Terrain"+".png"));
		Game.img_city = tool.getImage(getClass().getResource("/img/"+"Cities"+".png"));
		Game.img_exts = tool.getImage(getClass().getResource("/img/"+"Extras"+".png"));
		Game.img_plys = tool.getImage(getClass().getResource("/img/"+"Players"+".png"));
		Game.img_menu[0] = tool.getImage(getClass().getResource("/img/"+"GameInfo"+".png"));
	}
	/**Opens a custom texture pack then goes through it comparing sprite sheets to see if they are usable.*/
	public void LoadTexturePack(String name) {
		ZipHandler zip = new ZipHandler(name);
		
	    Game.img_char = TryNewImage("Units", zip, size_Unit);
	    Times_Unit = ResizeImage(size_Unit[0], Game.img_char.getWidth(null));
	    
	    Game.img_tile = TryNewImage("Terrain", zip, size_Terrain);
	    Times_Terrain = ResizeImage(size_Terrain[0], Game.img_tile.getWidth(null));
	    
	    Game.img_city = TryNewImage("Cities", zip, size_City);
	    Times_City = ResizeImage(size_City[0], Game.img_city.getWidth(null));
	    
	    Game.img_plys = TryNewImage("Players", zip, size_Player);
	    Times_Player = ResizeImage(size_Player[0], Game.img_plys.getWidth(null));
	    
	    Game.img_exts = TryNewImage("Extras", zip, size_City);
	    Times_Extras = ResizeImage(size_Extras[0], Game.img_exts.getWidth(null));
	}
	/**Currently supports 2x2 to 1024x1024 images.*/
	private int ResizeImage(int original, int modified) {
		if (original == modified) {return 5;}
		int base = original/(32);
		for (int size = 1; size<=10; size++) {
			if (Math.pow(2, size)*base == modified) {
				System.out.println("ROAR! " + size);
				return size;
			}
		}
		return 5;//Defaults at 32
	}
	/**Finds out if the custom image matches the same base dimensions of a given default texture, if not it returns the default image.*/
	private Image TryNewImage(String path, ZipHandler zip, int[] size) {
	    try {
	    	InputStream in = new ByteArrayInputStream(zip.getEntry(path + ".png"));
	        BufferedImage ZipImg = ImageIO.read(in);
	        in.close();
	        if (CompareSizes(size,ZipImg)) {
				return ZipImg;
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
		if (img.getHeight(null)%2==0 && img.getWidth(null)%2==0) {
			int y = img.getHeight(null);
			int x = img.getWidth(null);
			if (x/y == base[0]/base[1]) {
				return true;
			}
			else {Game.error.ShowError("An Images in this texturepack does not have the correct width/height ratio.");}
		}
		return false;
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
