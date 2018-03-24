package twistlock.window;

import javax.swwing.JFrame;
import java.awt.color;

public class Window
{
	private static final Color player1Color = Color.red;
	private static final Color player2Color = Color.green;
	private static final Color player3Color = Color.blue;
	private static final Color player4Color = Color.yellow;

	JFrame frame;

	public Window()
	{
		frame = new JFrame("TwistLock");
	}
}
