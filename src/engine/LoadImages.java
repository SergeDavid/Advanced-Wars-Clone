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
	public int Times_Unit = 1;
	public int Times_Terrain = 1;
	public int Times_City = 1;
	public int Times_Player = 1;
	public int Times_Menus = 1;
	
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
	    Times_Unit = ResizeImage(size_Unit[0], Game.img_char.getWidth(null));
	    
	    Game.img_tile = TryNewImage("Terrain", zip, size_Terrain);
	    Times_Terrain = ResizeImage(size_Terrain[0], Game.img_tile.getWidth(null));
	    
	    Game.img_city = TryNewImage("Cities", zip, size_City);
	    Times_City = ResizeImage(size_City[0], Game.img_city.getWidth(null));
	}
	
	private int ResizeImage(int original, int modified) {
		int i = modified/original;
		System.out.println(i + " new size!");
		return i;
	}
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
		if (img.getHeight(null)%base[1]==0) {
			int y = img.getHeight(null)/base[1];
			if (img.getWidth(null)%base[0]==0) {
				int x = img.getWidth(null)/base[0];
				//This checks to see if both X and Y are the same size for integrate (Change when supporting additional characters).
				if (x == y) {return true;}
			}
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
