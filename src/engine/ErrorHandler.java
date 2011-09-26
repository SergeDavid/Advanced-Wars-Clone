package engine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This Class handles showing all the error messages that crop up through the game.
 * ErrorTicker(); Needs to be placed in the game loop (fps section preferred) so that the message disappears after a set time. (6 seconds)
 * To display a message, just call ShowError("Stupid message!");
 * Include if(showing) {gui.add(this);} when changing gui elements (As I remove all elements when changing between game states such as in game / main menu / editor)
 * @author SergeDavid
 * @version 0.2
 */
public class ErrorHandler extends JPanel {
	private static final long serialVersionUID = 3813816343175926170L;
	private JLabel text = new JLabel("This is an error message!");
	public boolean showing;
	private int showtime;//how much time it takes until the error message goes away.
	private Vector<String> hiddenlist = new Vector<String>();
	
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
	/**Resets the text, then changes the shape and location of the error message so it is correctly in the middle as well as sets it to true.*/
	public void ShowError(String message) {
		if (showing) {hiddenlist.add(message);}
		else {text.setText(message);}
		ResetSize();
		Game.gui.add(this);
		showing=true;
		showtime = 5;
	}
	/**This removes the error message from the gui and sets the boolean to false.*/
	private void HideError() {
		if (hiddenlist.isEmpty()) {
			Game.gui.remove(this);
			showing=false;	
		}
		else {
			text.setText(hiddenlist.get(0));
			hiddenlist.remove(0);
			Game.gui.remove(this);
			ResetSize();
			Game.gui.add(this);
			showtime = 2;
		}
	}
	/**This changes the width, height, and offset so that it stays in the center and shows the whole message.*/
	private void ResetSize() {
		Insets insets = Game.gui.getInsets();
		Dimension size = Game.gui.getPreferredSize();
		Dimension size2 = text.getPreferredSize();
		//sets the size and what not of the text to be shown and what not
		text.setBounds(8, 2, size2.width, size2.height);
		setBounds(size.width/2+insets.left-size2.width/2-8,insets.top+8, size2.width+16, size2.height+8);
	}
	
}
