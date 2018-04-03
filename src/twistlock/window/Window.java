package twistlock.window;

import javax.swing.*;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;


/**
 * Only window of the user interface of the game.
 * @author Javane
 * @version 2018-03-26
 */
public class Window extends JFrame
{
	/** Colors of the players, in the order of their index */
	static final 	Color[] playerColors 	= new Color[] { Color.red, Color.green };
	/** The swing frame wrapped by the {@link Window}. */
	private 		JFrame 	frame;


	/**
	 * Creates the window which will be containing the entire user interface.
	 */
	public Window ()
	{
		super("TwistLock");

		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setToMenu();

		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	/**
	 * Sets the main display to the menu.
	 */
	public void setToMenu ()
	{
		this.changeShownPanel( new MenuPanel() );
		this.pack();
	}

	/**
	 * Sets the main display to the game.
	 */
	public void setToGame ()
	{
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		GamePanel gp = new GamePanel();
		this.changeShownPanel(gp);
	}

	/**
	 * Sets the main panel of the window to the panel passed as parameter.
	 * @param pan Panel to display in the window.
	 */
	void changeShownPanel (JPanel pan)
	{
		this.setContentPane(pan);
		SwingUtilities.updateComponentTreeUI(this);
	}
}
