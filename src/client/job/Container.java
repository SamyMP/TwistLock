package client.job;


import java.util.HashMap;
import java.util.Map;

/**
 * Single twistlock container of the game.
 * @author Javane
 * @version 2018-03-27
 */
public class Container
{
	/**
	* The minimal value a container can hold.
	*/
	private final int MIN_VALUE = 5;
	/**
	* The maximum value a container can hold.
	*/
	private final int MAX_VALUE = 54;
	/** Index of the player owning the container */
	private int owner;
	/**
	 * Array containing the {@link Twistlock} of each corner of this container
	 */
	private Twistlock[] corners;
	/**
	 * the row coordinate of the container
	 */
	private int row;
	/**
	 * the column coordinate of the container
	 */
	private int column;
	/**
	 * the score gained when a player control this container
	 * is a random beetwen 5 and 54
	 */
	private int value;


	/**
	 * Creates a game container.
	 * @param row Row index of the container.
	 * @param column Col index of the container.
	 * @param nw Linked lock placed on the northwest.
	 * @param ne Linked lock placed on the northeast.
	 * @param sw Linked lock placed on the southwest.
	 * @param se Linked lock placed on the southeast.
	 */
	public Container (int row, int column, int value, Twistlock nw, Twistlock ne, Twistlock sw, Twistlock se)
	{
		this.owner 		= -1;
		this.row 		= row;
		this.column 	= column;

		nw.addContainer(this);
		ne.addContainer(this);
		se.addContainer(this);
		sw.addContainer(this);

		this.corners 	= new Twistlock[] { nw, ne, se, sw };
		this.value 		= value;
	}


	/**
	 * Changes the owner of the lock at the position passed as parameter.
	 * @param pos Index of the lock to change the owner of.
	 * @param player Player to set as the owner of the lock.
	 * @return True if the capture succeeded, otherwise false.
	 */
	public boolean capture (int pos, int player)
	{
		Twistlock lock = getLock(pos);
		if (lock != null)
		{
			return lock.capture(player);
		}
		return false;
	}

	/**
	 * Calculates the owner of the container according to the owner locks surrounding it.
	 */
	public void calcOwner ()
	{
		this.owner = calcOwner (new HashMap<Integer, Integer>());
	}

	public int calcOwner (Map<Integer, Integer> controlSupp)
	{

		Map<Integer, Integer> control = new HashMap<Integer, Integer>();
		for (Integer i : controlSupp.keySet())
			control.put(i, new Integer( controlSupp.get(i) ));

		for (Twistlock twistlock : corners)
		{
			if (control.get(twistlock.getOwner()) == null)
			{
				control.put(twistlock.getOwner(), 0);
			}
			control.put(twistlock.getOwner(), control.get(twistlock.getOwner()) + 1);
		}

		int max = 0;
		int maxId = -1;

		for (Integer i : control.keySet())
		{
			if (i != -1)
			{
				if (control.get(i) == max)
				{
					maxId = -1;
				}
				if (control.get(i) > max)
				{
					max = control.get(i);
					maxId = i;
				}
			}
		}

		return maxId;
	}


	/*--------------*/
	/*    GETTERS   */
	/*--------------*/

	/**
	 * Gets the owner id.
	 * @return The owner player's id.
	 */
	public int getOwner ()
	{
		this.calcOwner();
		return owner;
	}

	/**
	 * Gets the lock corresponding to the id passed as parameter.
	 * @param pos Index of the lock to return : 1 for the northwest one, 2 for the northeast one,
	 * 3 for the southeast one, 4 for the southwest one.
	 * @return The lock linked to the id passed as parameter.
	 */
	public Twistlock getLock (int pos)
	{
		try
		{
			return corners[pos - 1];
		}
		catch (Exception e)
		{
			return null;
		}
	}

	/**
	 * Gets the value of the container.
	 * @return The value of the container.
	 */
	public int getValue ()
	{
		return value;
	}
}
