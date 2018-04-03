package twistlock.util;

import java.net.InetAddress;

public class InetAddressWithPort
{
	private InetAddress inetAddress;
	private int port;
	
	public InetAddressWithPort(InetAddress inetAddress, int port)
	{
		this.inetAddress = inetAddress;
		this.port = port;
	}
	
	public InetAddress getInetAddress()
	{
		return this.inetAddress;
	}
	
	public int getPort()
	{
		return this.port;
	}
	
	public boolean equals(Object obj)
	{
		if (! (obj instanceof InetAddressWithPort))
			return false;
		
		InetAddressWithPort other = (InetAddressWithPort) obj;
		
		return this.inetAddress.equals(other.inetAddress) && this.port == other.port;
	}
	
	public boolean equals(InetAddress inetAddress, int port)
	{
		return this.equals( new InetAddressWithPort(inetAddress, port) );
	}
}