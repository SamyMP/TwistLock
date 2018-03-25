package twistlock;

import twistlock.window.Window;
import twistlock.job.Container;
import twistlock.job.Player;
import twistlock.job.Twistlock;

/**
 * TODO
 */
public class Controller
{
	private static Controller singleton;

	private Player[] players;
	private Container[][] containers;

	private Controller()
	{
		new Window();
	}

	public static Controller getController()
	{
		if (singleton == null) {
			singleton = new Controller();
		}
		return singleton;
	}

	public void createGame(String... players)
	{
		int rowCount = (int)(Math.random() * 10) + 6;
		int colCount = (int)(Math.random() * 8) + 3;

		Twistlock[][] tls = new Twistlock[colCount + 1][rowCount + 1];
		for (int i = 0; i < tls.length; i++)
		{
			for (int j = 0; j < tls[i].length; j++)
			{
				tls[i][j] = new Twistlock();
			}
		}

		containers = new Container[colCount][rowCount];
		for (int i = 0; i < containers.length; i++)
		{
			for (int j = 0; j < containers[i].length; j++)
			{
				containers[i][j] = new Container(i, j, tls[i][j], tls[i][j + 1], tls[i + 1][j], tls[i + 1][j + 1]);
			}
		}
	}

	public Player[] getPlayers()
	{
		return players;
	}

	public Container[][] getContainers()
	{
		return containers;
	}

	public static void main(String[] args)
	{
		getController().createGame("Adam", "Jonat");

	}
}
