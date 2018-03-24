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
	private int controllingPlayer;

	public Twistlock()
	{
		controllingPlayer = -1;
	}

	/**
	 * TODO
	 * @param player [description]
	 */
	public void capture(Player player)
	{
		player.useTwistlock();
		if (controllingPlayer == -1)
		{
			controllingPlayer = player.getId();
		}
		else
		{
			player.useTwistlock();
		}
	}
}
