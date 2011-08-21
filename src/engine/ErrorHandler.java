package engine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ErrorHandler extends JPanel {
	private static final long serialVersionUID = 3813816343175926170L;
	private JLabel text = new JLabel("This is an error message!");
	public boolean showing;
	private int showtime;//how much time it takes until the error message goes away.
	
	/**Adds the text to the panel as well as set the text's color to red.*/
	public ErrorHandler() {
		text.setForeground(new Color(255,0,0));
		add(text);
	}
	
	/**This function hides the error message when an integer reaches zero. (tick connected to the fps area of the game loop.)*/
	public void ErrorTicker() {
		if (showing) {
			showtime--;
			if (showtime <= 0) {
				HideError();
			}
		}
	}
	
	/**Resets the text, then changes the shape and location of the error message so it is correctly in the middle aswell as sets it to true.*/
	public void ShowError(String message) {
		text.setText(message);
		Insets insets = Game.gui.getInsets();
		Dimension size = Game.gui.getPreferredSize();
		Dimension size2 = text.getPreferredSize();
		//sets the size and what not of the text to be shown and what not
		text.setBounds(8, 2, size2.width, size2.height);
		setBounds(size.width/2+insets.left-size2.width/2-8,insets.top+8, size2.width+16, size2.height+8);
		Game.gui.add(this);
		showing=true;
		showtime = 10;
	}
	
	/**This removes the error message from the gui and sets the boolean to false.*/
	public void HideError() {
		Game.gui.remove(this);
		showing=false;
	}

}
