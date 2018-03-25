package twistlock;

import twistlock.job.Container;
import twistlock.job.Player;
import twistlock.job.Twistlock;
import twistlock.window.Window;

/**
 * TODO
 */
public class Controller
{
	private static Controller singleton;

	private Player[] players;

	private int actualPlayer;

	private Container[][] containers;
	private Window window;

	private Controller()
	{
		window = new Window();
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
		createGame((int)(Math.random() * 10) + 6, (int)(Math.random() * 8) + 3, players);
	}

	public void createGame(int width, int height, String... players)
	{
		this.actualPlayer = 0;

		this.players = new Player[players.length];
		for (int i = 0; i < this.players.length; i++)
		{
			this.players[i] = new Player(players[i]);
		}

		int rowCount = height;
		int colCount = width;

		Twistlock[][] twistlocks = new Twistlock[colCount + 1][rowCount + 1];
		for (int i = 0; i < twistlocks.length; i++)
		{
			for (int j = 0; j < twistlocks[i].length; j++)
			{
				twistlocks[i][j] = new Twistlock();
			}
		}

		containers = new Container[colCount][rowCount];
		for (int i = 0; i < containers.length; i++)
		{
			for (int j = 0; j < containers[i].length; j++)
			{
				containers[i][j] = 	new Container(i, j,
			                        	twistlocks[i][j], twistlocks[i + 1][j],
										twistlocks[i][j + 1], twistlocks[i + 1][j + 1]
									);
			}
		}

		containers[1][4].capture(1, this.players[0]);
		containers[1][3].capture(3, this.players[1]);
		containers[1][3].capture(1, this.players[0]);
		containers[3][2].capture(1, this.players[1]);
		containers[1][3].capture(2, this.players[0]);
		containers[3][2].capture(4, this.players[1]);
		window.repaint();
		System.out.println(containers[0][7].getPlayer());
	}

	public boolean isFinished()
	{
		boolean isFinished = true;

		for (int i = 0 ; i < containers.length ; i++)
			for (int j = 0 ; j < containers[i].length ; j++)
				for (int coin = 0 ; coin < 4 ; coin++)
					if (containers[i][j].getLock(coin).getPlayer() == -1)
						isFinished = false;

		if (isFinished)
			return true;

		for (int i = 0 ; i < players.length ; i++)
			if (players[i].hasTwistlock())
				return false;

		return true;
	}

	public void doAction(int row, int col, int coin)
	{
		containers[col][row].capture(coin, this.players[this.actualPlayer]);

		if (! isFinished())
			do
			{
				this.actualPlayer = (this.actualPlayer + 1 ) % this.players.length;

			} while ( ! this.players[this.actualPlayer].hasTwistlock());
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
		getController().createGame("Adam", "Jonat", "Martin", "Samy");

	}
}
