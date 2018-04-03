package client;


import client.view.Window;
import client.job.ia.IA;
import client.job.ia.IA1;
import client.job.ia.IA2;
import client.job.ia.IA3;

/**
 * Panel containing a log and a field for the player to input in action.
 * @author  Javane
 * @version 2018-03-2018
 */
public class Client
{
    /** Window of the client. */
	private static Window   window;
	/** Network manager of the client */
	private static Network  network;
	/** IA of the client, null if it is a physical player. */
    private static IA       ia;


    /**
     * Creates the network manager of the client.
     * @param serverIp IP of the server.
     * @return The network manager created.
     */
	public static Network createNetwork (String serverIp, int port)
	{
		Client.network = new Network(serverIp, port);
		return Client.network;
	}

    /**
     * Creates an IA of specified level.
     * @param level Level of the IA, 0 if it is a physical player.
     * @return The IA created.
     */
	public static IA createIA (int level)
    {
        Client.ia = null;
        switch (level)
        {
            case 1:
                Client.ia = new IA1();
                break;
            case 2:
                Client.ia = new IA2();
                break;
            case 3:
                Client.ia = new IA3();
                break;
        }
        return Client.ia;
    }


    /*---------------*/
    /*    GETTERS    */
    /*---------------*/

    /**
     * Gets the network manager of the client.
     * @return Network of the client.
     */
    public static Network getNetwork ()
    {
        return network;
    }

    /**
     * Gets the window of the client.
     * @return Window of the client.
     */
    public static Window getWindow ()
    {
        return window;
    }

    /**
     * Gets the IA of the client.
     * @return IA of the client, null if it is a physical player.
     */
    public static IA getIA ()
    {
        return Client.ia;
    }


    public static void main (String[] args)
    {
        Client.window = new Window();
    }
}
