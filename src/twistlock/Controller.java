package twistlock;

import twistlock.job.Container;
import twistlock.job.Player;
import twistlock.job.Twistlock;
import twistlock.job.Network;
import twistlock.window.Window;
import twistlock.util.InetAddressWithPort;

import javax.swing.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.net.DatagramPacket;


/**
 * Main controller of the MVC model, managing the mulitple important values of the game.
 * @author Javane
 * @version 2018-03-26
 * TODO : connection d'un client au pif en fin de partie.
 */
public class Controller
{
	/** Single instance of the {@link Controller}. */
	private static Controller 	singleton;
	/** Set of the players participating to the the twistlock game. */
	private Player[] 			players;
	/** The index of the player whose it is the turn. */
	private int 				currentPlayer;
	/** Set of the containers forming the game canvas. */
	private Container[][] 		containers;
	/** Frame forming the user interface of the game */
	private Window 				window;
	/** Network manager of the game. */
	private Network				network;
	/** Sets the game in demo mode. */
	private static boolean		isDemo = false;


	/**
	 * Creates the controller for the server, along with its attached network manager.
	 */
	private Controller ()
	{
		this.network		= new Network();
	}

	/**
	 * Creates and launches the main game of Twistlock.
	 * @param width Number of columns of containers.
	 * @param height Number of rows of containers.
	 * @param players Players to play in the game.
	 */
	public void createGame (int width, int height, Map<String, InetAddressWithPort> players)
	{
		Player.resetId();
		this.currentPlayer = 0;

		int count=0;
		this.players = new Player[players.size()];
		for (String key : players.keySet())
		{
			this.players[count++] = new Player(key, players.get(key));
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

		this.containers = new Container[colCount][rowCount];
		for (int i = 0; i < containers.length; i++)

		{
			for (int j = 0; j < this.containers[i].length; j++)
			{
				this.containers[i][j] = 	new Container(i, j,
			                        	twistlocks[i][j], twistlocks[i + 1][j],
										twistlocks[i][j + 1], twistlocks[i + 1][j + 1]
									);
			}
		}

		// Sets the display to the game

		Controller that = this;

		this.window.setToGame();
		new Thread()
		{
			public void run()
			{
				that.play();
			}
		}.start();
	}

	/**
	 * Checks if the game is finished, which can be reached if all the players have used the totality of their locks,
	 * or if the locks of the containers are all set.
	 * @return True if the game is finished, otherwise false.
	 */
	public boolean isFinished ()
	{
		boolean isFinished = true;

		// Checks if the all the containers have their lock set
		for (int i = 0 ; i < this.containers.length ; i++)
			for (int j = 0 ; j < this.containers[i].length ; j++)
				for (int coin = 1 ; coin < 5 ; coin++)
					if (this.containers[i][j].getLock(coin).getOwner() == -1)
						isFinished = false;

		if (isFinished)
			return true;

		// Checks if all the players have used all their locks.
		for (int i = 0 ; i < this.players.length ; i++)
			if (this.players[i].hasTwistlock())
				return false;

		return true;
	}

	/**
	 * Perform an action on a certain lock belonging to a container referenced by its coordinates.
	 * @param row Row index of the container.
	 * @param col Column index of the container.
	 * @param corner Corner index of the container, referencing a certain lock.
	 * @return True if an error must be displayed, otherwise false.
	 */
	public boolean doAction (int row, int col, int corner)
	{
	    boolean toReturn = true;
		// If the values don't exist, the player loses two twistlocks
		if (col >= containers.length || col < 0 || row >= containers[col].length || row < 0 || corner > 4 ||
				row < 0 || col < 0 || corner <= 0)
		{
			Player player = this.players[this.currentPlayer];
			player.useTwistlock(2);
			toReturn = false;
		}

		// Otherwise, the lock is captured by the player and is now his
        if (toReturn)
		    toReturn = containers[col][row].capture(corner, this.players[this.currentPlayer]);

		if (this.nextPlayer() == false && toReturn == false)
			toReturn = true;

		return toReturn;
	}

	/**
	 * Updates the current player to make the next player play, or end the game if it ended.
	 * @return True if the turn was set to the next player, false if the game has ended.
	 */
	public boolean nextPlayer ()
	{
		if (!isFinished())
		{
			do
			{
				this.currentPlayer = (this.currentPlayer + 1) % this.players.length;
			} while ( !this.players[this.currentPlayer].hasTwistlock() );

			// Sends a notification to the next player to play
			// TODO

			return true;
		}
		else
		{ //TODO a modifier
			String endMsg = "";
			for (int i = 0, n = players.length; i < n; i++)
			{
				Player player = players[i];
				endMsg += String.format(" - %-10s : %d%s", player.getPseudo(), player.getScore(), (i == n-1) ? "" : "\n");
			}
			window.repaint();

			// Displays a recap window of the scores of every player
			JOptionPane d = new JOptionPane();
			d.showMessageDialog( this.window.getContentPane(), endMsg,"Récapitulatif", JOptionPane.INFORMATION_MESSAGE);

			// Bring the players back to the menu
			this.window.setToMenu();

			return false;
		}
	}

	public void sendMessage( String numMessage, InetAddressWithPort inetAddressWithPort, String ... info )
	{
		String[] coul = new String[] {"rouge","vert"};

		switch(numMessage)
		{
			case "1":
				network.sendMessage(inetAddressWithPort, "1-Bonjour " + info[0] + "\n" + "Vous etes le joueur 1 (" + coul[0] + "), attente suite ...");
				break;

			case "2":
				network.sendMessage(inetAddressWithPort, "2-Bonjour " + info[0] + "\n" + "Vous etes le joueur 2 (" + coul[1] + "), attente suite ...");
				break;

			case "01":
				String map = "MAP=";
				for (int i = 0 ; i < containers[0].length ; i++)
				{
					map += containers[0][i].getValue();
					for (int j = 1; j < containers.length ; j++)
						map += ":" + containers[j][i].getValue();

					map += "|";
				}
				network.sendMessage(inetAddressWithPort, "01-la partie va commencer\n" + map);
				break;

			case "10":
				network.sendMessage(inetAddressWithPort, "10-A vous de jouer (" + coul[Integer.parseInt(info[0])] + ") :");
				break;

			case "20":
				network.sendMessage(inetAddressWithPort, "20-coup adversaire:" + info[0]);
				break;

			case "21":
				network.sendMessage(inetAddressWithPort, "21-coup joué illegal");
				break;

			case "22":
				network.sendMessage(inetAddressWithPort, "22-coup adversaire illegal");
				break;

			case "50":
				network.sendMessage(inetAddressWithPort, "50-Vous ne pouvez plus jouer");
				break;

			case "88":
				network.sendMessage(inetAddressWithPort, "88-Partie Terminée, vous avez " + info[0] + " " + info[1] + " - " + info[2]);
				break;

			case "91":
				network.sendMessage(inetAddressWithPort, "91-demande non valide");
				break;
		}
	}

	/**
	 * Send the game information to the players.
	 */
	public void play ()
	{
		sendMessage("01", this.players[0].getInetAddress());
		sendMessage("01", this.players[1].getInetAddress());

		sendMessage("10", this.players[currentPlayer].getInetAddress(), currentPlayer + "");

		while(!isFinished())
		{
			int currentPlayerAtStart = currentPlayer;
			String message = network.getMessage(players[currentPlayerAtStart].getInetAddress());
			try
			{
				int row = Integer.parseInt(message.charAt(0) + "");
				int col = message.charAt(1) - 'A';
				int corner = Integer.parseInt(message.charAt(2) + "");

				if (doAction(row - 1, col, corner))
				{
					// 20 adv reussi
					this.sendMessage("20", players[1 - currentPlayerAtStart].getInetAddress(), message);
				}
				else
				{
					throw new Exception();
					// sendMessage("21", players[currentPlayerAtStart].getInetAddress());
					// sendMessage("22", players[1 - currentPlayerAtStart].getInetAddress());
				}
			}
			catch(Exception e)
			{
				this.sendMessage("21", players[currentPlayerAtStart].getInetAddress());
				this.sendMessage("22", players[1 - currentPlayerAtStart].getInetAddress());
			}

			if (!players[currentPlayerAtStart].hasTwistlock())
			{
				this.sendMessage("50", players[currentPlayerAtStart].getInetAddress());
			}

			if (!isFinished())
			{
				sendMessage("10", players[currentPlayer].getInetAddress(), currentPlayer + "");
			}
			this.window.repaint();
		}

		int winner = 0;
		if (players[1].getScore() > players[0].getScore())
		{
			winner = 1;
		}

		sendMessage("88", players[0].getInetAddress(), (winner == 0)? "gagné" : "perdu", players[0].getScore() + "", players[1].getScore() + "");
		sendMessage("88", players[1].getInetAddress(), (winner == 1)? "gagné" : "perdu", players[1].getScore() + "", players[0].getScore() + "");
	}

	public DatagramPacket newConnection ()
	{
		DatagramPacket dp;
		do
		{
			dp = network.getMessage ();
		} while ( dp.getData().length == 0 );

		return dp;
	}


	/*-------------*/
	/*   GETTERS   */
	/*-------------*/

	/**
	 * Gets the instance of the controller.
	 * @return Single instance of the controller.
	 */
	public static Controller getController ()
	{
		if (singleton == null)
		{
			singleton = new Controller();
			singleton.window = new Window();

			if (Controller.isDemo)
			{
				Controller.isDemo = false;
				try
				{
					HashMap<String, InetAddressWithPort> map = new HashMap<String, InetAddressWithPort>();
					map.put( "Joueur 1", new InetAddressWithPort(InetAddress.getByName("127.0.0.1"), 2009) );
					map.put( "Joueur 2", new InetAddressWithPort(InetAddress.getByName("127.0.0.1"), 2009) );
					singleton.createGame(5, 5, map);
				}
				catch (UnknownHostException e) { }
			}
		}

		return singleton;
	}


	public JFrame getWindow ()
    {
        return this.window;
    }

	/**
	 * Gets the players playing the game.
	 * @return The set of players participating to the game.
	 */
	public Player[] getPlayers ()
	{
		return players;
	}

	/**
	 * Gets the containers displayed in the game canvas.
	 * @return Set of the containers of the game canvas.
	 */
	public Container[][] getContainers()
	{
		return containers;
	}


	/**
	 * Main function launching the program by creating a window and attaching the menu to it.
	 * @param args Possible arguments of the function.
	 */
	public static void main (String[] args)
	{
		getController();
	}
}
