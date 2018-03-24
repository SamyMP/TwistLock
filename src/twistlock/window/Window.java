package twistlock.window;

import javax.swing.JFrame;
import java.awt.Color;

/**
 * TODO
 */
public class Window extends JFrame
{
	private static final Color player1Color = Color.red;
	private static final Color player2Color = Color.green;
	private static final Color player3Color = Color.blue;
	private static final Color player4Color = Color.yellow;

	JFrame frame;

	public Window()
	{
		super("TwistLock");



		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
	}
}
