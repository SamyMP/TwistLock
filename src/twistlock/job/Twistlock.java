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

	public Twistlock()
	{
		player = -1;
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
