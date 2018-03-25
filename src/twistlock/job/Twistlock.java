package twistlock.job;

/**
 * TODO
 */
public class Twistlock
{
	/**
	 * the id of the player currently controling the lock
	 * -1 if not controlled yet
	 */
	private int player;

	private Container[] containers;

	public Twistlock()
	{
		player = -1;
		containers = new Container[0];
	}

	public void addContainer(Container container)
	{
		Container[] tmp = new Container[containers.length + 1];
		for (int i = 0; i < containers.length; i++)
		{
			tmp[i] = containers[i];
		}
		tmp[tmp.length - 1] = container;
		containers = tmp;
	}

	/**
	 * TODO
	 * @param player [description]
	 */
	public void capture(Player player)
	{
		player.useTwistlock();
		if (this.player == -1)
		{
			this.player = player.getId();
			for (Container container : containers) { container.calcOwner(); }
		}
		else
		{
			player.useTwistlock();
		}
	}

	public int getPlayer()
	{
		return player;
	}
}
