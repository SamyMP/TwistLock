package client;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import javax.swing.JOptionPane;


/**
 * Network manager attached to a single client.
 * @author  Javane
 * @version 2018-03-2018
 */
public class Network
{
    /** Default port to connect to. */
    private static int      DEFAULT_PORT = 2009;
    /** Port of the server to connect to. */
    private int             port;
    /** IP address of the server to connect to. */
	private InetAddress     serverIp;
	/** Datagram socket to receive the information sent by the server. */
	private DatagramSocket  dgSocket;


    /**
     * Creates the network server.
     * @param serverIp IP of the server.
     */
	private Network (String serverIp)
    {
        this(serverIp, DEFAULT_PORT);
    }

    /**
     * Creates the network server.
     * @param serverIp  IP of the server.
     * @param port      Port of the server to connect to.
     */
	public Network (String serverIp, int port)
	{
	    this.port = 2009;

		try
		{
			this.serverIp = InetAddress.getByName(serverIp);
			this.dgSocket = new DatagramSocket();
            this.dgSocket.setSoTimeout(5000);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		new Thread()
		{
			private Network network;

			public Thread setNetwork(Network network)
			{
				this.network = network;
				return this;
			}

			public void run()
			{
				while(true)
				{
					try
					{
						DatagramPacket msg = new DatagramPacket(new byte[512], 512);

						dgSocket.receive(msg);
                        dgSocket.setSoTimeout(1000 * 60 * 60 * 24);
						
						byte[] data = msg.getData();
		
						int i = 0;
						for (;i < data.length && data[i] != 0; i++);
							
						byte[] newData = new byte[i];
						
						for (int j = 0 ; j < newData.length ; j++)
							newData[j] = data[j];
						
						msg.setData(newData);
		
						network.printServerMessage(new String(msg.getData()));
					}
					catch (SocketTimeoutException e)
					{
						break;
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				JOptionPane.showMessageDialog( Client.getWindow(), "Le serveur n'existe pas où n'a pas répondu à temps.", "Timeout", JOptionPane.ERROR_MESSAGE);
			}
		}.setNetwork(this).start();
	}

    /**
     * Analyzes the message sent by the server to the client.
     * @param message Message received.
     */
	void printServerMessage (String message)
	{
		System.out.println(message);
		Client.getWindow().addLog(message);

		if      (message.startsWith("01"))
		{
			Client.getWindow().setGamePanel();
			if (Client.getIA() != null)
			    Client.getIA().receiveContainers(message);
		}
		else if (message.startsWith("10"))
		{
		    if (Client.getIA() == null)
			    Client.getWindow().canPlay(true);
		    else
			    this.sendMessage( Client.getIA().calcResponse() );
		}
		else if (message.startsWith("20"))
        {
            if (Client.getIA() != null)
                Client.getIA().saveAction(message.split(":")[1], 1);
        }
        // TODO : 22
	}

    /**
     * Send a message to the server through a datagram packet.
     * @param message Message to send to the server.
     */
	public void sendMessage (String message)
	{
		DatagramPacket envoi = new DatagramPacket(
			message.getBytes(),
			message.length(),
			this.serverIp,
			this.port
		);

		try
		{
			System.out.println(message);
			this.dgSocket.send(envoi);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		DatagramPacket msg = new DatagramPacket(new byte[512], 512);
	}
}
