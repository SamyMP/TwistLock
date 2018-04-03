package client.job.ia;

import client.job.Container;
import client.job.Twistlock;


/**
 * IA of a player.
 */
public abstract class IA
{
    /** Containers of the current game. */
	protected Container[][]	containers;


    /**
     * Receive the containers from the first message sent to the client.
     * @param message Message containing all the containers as a string.
     */
	public void receiveContainers (String message)
    {
        String[] arrRows = message.split("\n")[1].substring(4).split("\\|");

        int row = arrRows.length;
        int col = arrRows[0].split(":").length;

        // Creation of the twistlocks
        Twistlock[][] twistlocks = new Twistlock[col + 1][row + 1];
        for (int i = 0; i < twistlocks.length; i++)
        {
            for (int j = 0; j < twistlocks[i].length; j++)
            {
                twistlocks[i][j] = new Twistlock();
            }
        }

        // Creation of the containers
        this.containers = new Container[row][col];
        for (int i = 0, n = this.containers.length; i < n; i++)
        {
            String[] arrCols = arrRows[i].split(":");
            for (int j = 0, m = this.containers[i].length; j < m; j++)
            {
				int sqd = Integer.parseInt(arrCols[j]);
				Twistlock a1 = twistlocks[j][i];
				Twistlock a2 = twistlocks[j + 1][i];
				Twistlock a3 = twistlocks[j][i + 1];
				Twistlock a4 = twistlocks[j + 1][i + 1];
                this.containers[i][j] = new Container(i, j, sqd, a1, a2, a3, a4);
            }
        }

		for(Container[] cont : containers)
		{
			for (Container con : cont)
			{
				System.out.print(con.getValue() + " ");
			}
			System.out.println();
		}
    }

    /**
     * Saves the actions of the players.
     * @param action Action done by the player.
     * @param owner 0 if the action is done by the player attached to the IA, 1 if the action comes from the opponent.
     */
    public void saveAction (String action, int owner)
    {
		System.out.println(action);
        this.containers[action.charAt(0)-'1'][action.charAt(1)-'A'].capture(action.charAt(2)-'0', owner);
    }

	/**
	 * Plays for the player, according to its IA level.
	 */
	public abstract String calcResponse ();
}
