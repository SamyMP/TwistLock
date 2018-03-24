package twistlock;

import twistlock.window.Window;
import twistlock.job.Container;
import twistlock.job.Player;

public class Controller
{
	public Controller()
	{
		new Window();
	}

	private Player[] playerList;
	private Container[] containerList;

	public void getPlayers()
	{

	}

	public static void main(String[] args)
	{
		new Controller();
	}
}
