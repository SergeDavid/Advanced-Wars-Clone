package engine;

import java.awt.Toolkit;

/**A very simple class that takes a nice big list of images and loads them into the game. It is separated from the main class to decrease size.*/
public class LoadImages {
	/**This will initialize the loading images area by only loading up the logo, the rest are called as the game loads different parts.*/
	public LoadImages() {
		Toolkit tool = Toolkit.getDefaultToolkit();
		Game.img_char = tool.getImage(getClass().getResource("/img/"+"Units"+".png"));
		Game.img_tile = tool.getImage(getClass().getResource("/img/"+"Terrain"+".png"));
		Game.img_menu[0] = tool.getImage(getClass().getResource("/img/"+"GameInfo"+".png"));
	
	}
	/**This loads all the images not used in the startup screen*/
	public void LoadMenu() {}
	public void LoadGame() {}
	public void LoadCredit() {}
}
