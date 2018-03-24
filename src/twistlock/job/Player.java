package twistlock.job;

/**
 * class representing player
 * contain pseudo, score, ...
 * @author Javane
 * @version 2018-03-24
 */
public class Player
{
	/**
	 * the id of the player (used to get his color)
	 */
	private int id;
	/**
	 * incremental value used to set id of new instances
	 */
	private static int staticId = 0;

	/**
	 * the pseudo of the player
	 */
	private String pseudo;

	/**
	 * the score of the player
	 */
	private int score;

	/**
	 * the number of twistlocks left for this player
	 */
	private int twistlock;
	/**
	 * the number of twistlocks the player has at it's creation
	 */
	private int initialTwistlock;

	/**
	 * create a new instance of player
	 * @param pseudo            the pseudo of the player to create
	 * @param twistlockQuantity the number of twistlock the player posses at its creation
	 */
	public Player(String pseudo, int initialTwistlock)
	{
		id = staticId++;
		this.pseudo = pseudo;
		this.initialTwistlock = initialTwistlock;
		twistlock = initialTwistlock;
		score = 0;
	}

	/**
	 * create a new instance of player
	 * @param pseudo the pseudo of the player to create
	 */
	public Player(String pseudo)
	{
		this(pseudo, 20);
	}

	public int getId()
	{
		return id;
	}

	/**
	 * reset the number of twistlocks to it's value at player's creation
	 */
	public void resetTwistlock()
	{
		resetTwistlock(initialTwistlock);
	}

	/**
	 * set the number of twistlocks to the specified value
	 * @param value the value to set twistlocks to
	 */
	public void resetTwistlock(int value)
	{
		twistlock = value;
	}

	/**
	 * method called when a twistlock is used
	 * 	do : twistlock--
	 */
	public void useTwistlock()
	{
		twistlock--;
	}

	/**
	 * method used to know if player has at least one twistlock left
	 * @return [description]
	 */
	public boolean hasTwistlock()
	{
		return twistlock > 0;
	}

	/**
	 * reset the score of this player to 0
	 */
	public void resetScore()
	{
		score = 0;
	}

	/**
	 * add the specified value to the score of this player
	 * @param value value to add to player score
	 */
	public void addScore(int value)
	{
		score += value;
	}
}
