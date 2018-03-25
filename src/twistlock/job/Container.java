package twistlock.job;

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
		corners = new Twistlock[] { nw, ne, sw, se };
		value = (int)(Math.random() * (MAX_VALUE - MIN_VALUE + 1)) + MIN_VALUE;
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
