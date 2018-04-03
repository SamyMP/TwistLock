package twistlock.job;

import java.net.DatagramSocket;
import java.net.DatagramPacket;

import twistlock.util.InetAddressWithPort;
import twistlock.Controller;


/**
 * Network manager of the twistlock.
 */
public class Network
{
	/** Port to connect to. */
	public static final int DEFAULT_PORT = 2009;
	/** Datagram socket to send to the players. */
	private DatagramSocket ds;

    /**
     * Creates a network manager to the default port.
     */
	public Network ()
	{
		this(Network.DEFAULT_PORT);
	}

    /**
     * Creates a network manager attached to the specified port.
     * @param port The port of the socket.
     */
	public Network (int port)
	{
		try
		{
			this.ds = new DatagramSocket(port);
		}
		catch(Exception e) {}
	}

    /**
     * Gets the message sent from the datagram socket.
     * @param inetAddressWithPort
     * @return The message received.
     */
	public String getMessage (InetAddressWithPort inetAddressWithPort)
	{
		DatagramPacket msg = null;
		
		do
		{
			if (msg != null && ! inetAddressWithPort.equals(msg.getAddress(), msg.getPort() ))
				Controller.getController().sendMessage("91",new InetAddressWithPort(msg.getAddress(), msg.getPort()));
			
			msg = new DatagramPacket(new byte[512], 512);
			
			try
			{
				this.ds.receive(msg);
			}
			catch(Exception e) {}
			
		} while( ! inetAddressWithPort.equals(msg.getAddress(), msg.getPort() ));
		
		byte[] data = msg.getData();
		
		int i = 0;
		for (;i < data.length && data[i] != 0; i++);
			
		byte[] newData = new byte[i];
		
		for (int j = 0 ; j < newData.length ; j++)
			newData[j] = data[j];
		
		msg.setData(newData);
		
		try
		{
			return new String( msg.getData() ,"UTF-8");
		}
		catch (Exception e)
		{}
		
		return null;
	}

    /**
     * Gets the message from the datagram socket.
     * @return The message received.
     */
	public DatagramPacket getMessage ()
	{
		DatagramPacket msg = null;
		
		
		try
		{
			msg = new DatagramPacket(new byte[512], 512);
			ds.receive(msg);
		}
		catch(Exception e) {}
		
		byte[] data = msg.getData();
		
		int i = 0;
		for (;i < data.length && data[i] != 0; i++);
			
		byte[] newData = new byte[i];
		
		for (int j = 0 ; j < newData.length ; j++)
			newData[j] = data[j];
		
		msg.setData(newData);
		return msg;
	}

    /**
     * Sends a message to the specified address.
     * @param inetAddressWithPort IP address to send the message to.
     * @param message Message to send.
     */
	public void sendMessage (InetAddressWithPort inetAddressWithPort, String message)
	{
		try
		{
			DatagramPacket reponse = new DatagramPacket(message.getBytes("UTF-8"), new String( message.getBytes("UTF-8") ).length(), inetAddressWithPort.getInetAddress(), inetAddressWithPort.getPort() );
		
			this.ds.send(reponse);
			System.out.println("message envoyer : " + new String( message.getBytes("UTF-8") ) + " to " + inetAddressWithPort.getInetAddress() + ":" + inetAddressWithPort.getPort());
		}
		catch(Exception e) {}
	}
	
	
}
