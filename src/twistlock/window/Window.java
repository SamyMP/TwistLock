package twistlock.window;

import javax.swing.JFrame;
import java.awt.Color;

/**
 * TODO
 */
public class Window extends JFrame
{
	private static final Color[] playerColors =
		new Color[] { Color.red, Color.green, Color.blue, Color.yellow };

	JFrame frame;

	public Window()
	{
		super("TwistLock");



		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
	}
}
