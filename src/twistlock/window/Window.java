package twistlock.window;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.BorderLayout;


/**
 * TODO
 */
public class Window extends JFrame
{
	static final Color[] playerColors =
		new Color[] { Color.red, Color.green, Color.blue, Color.yellow };

	JFrame frame;

	public Window()
	{
		super("TwistLock");

		changeShownPanel(new MenuPanel());
		changeShownPanel(new GamePanel());

		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
	}

	void changeShownPanel(JPanel pan)
	{
		add(pan);
	}
}
