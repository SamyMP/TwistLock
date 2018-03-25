package twistlock.job;


import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 */
public class Container
{
	/**
	* the minimal value a container can hold
	*/
	private final int MIN_VALUE = 5;

	/**
	* the maximum value a container can hold
	*/
	private final int MAX_VALUE = 54;

	private int player;

	/**
	 * array containing the {@link Twistlock} of each corner of this container
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
	 * create a container
	 */
	public Container(int row, int column, Twistlock nw, Twistlock ne, Twistlock sw, Twistlock se)
	{
		player = -1;
		this.row = row;
		this.column = column;
		nw.addContainer(this);
		ne.addContainer(this);
		se.addContainer(this);
		sw.addContainer(this);
		corners = new Twistlock[] { nw, ne, se, sw };
		value = (int)(Math.random() * (MAX_VALUE - MIN_VALUE + 1)) + MIN_VALUE;
	}

	/**
	 * get the controlling player id
	 * @return the controlling player id
	 */
	public int getPlayer()
	{
		return player;
	}

	/**
	 * get the controlling player id
	 * @return the controlling player id
	 */
	public Twistlock getLock(int pos)
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

	public void capture(int pos, Player player)
	{
		Twistlock lock = getLock(pos);
		if (lock != null)
		{
			lock.capture(player);
		}
	}

	void calcOwner()
	{
		Map<Integer, Integer> control = new HashMap<Integer, Integer>();
		for (Twistlock twistlock : corners)
		{
			if (control.get(twistlock.getPlayer()) == null)
			{
				control.put(twistlock.getPlayer(), 0);
			}
			control.put(twistlock.getPlayer(), control.get(twistlock.getPlayer()) + 1);
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
		player = maxId;
	}

	/**
	 * get the container value
	 * @return the container value
	 */
	public int getValue()
	{
		return value;
	}
}
