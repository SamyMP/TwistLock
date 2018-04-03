package twistlock.job;

import twistlock.Controller;
import twistlock.util.InetAddressWithPort;

/**
 * class representing player
 * contain pseudo, score, ...
 * @author Javane
 * @version 2018-03-24
 */
public class Player
{
	/**
	 * The id of the player (used to get his color).
	 */
	private int 		id;
	/**
	 * Incremental value used to set id of new instances.
	 */
	private static int 	staticId = 0;
	/**
	 * The pseudo of the player.
	 */
	private String 		pseudo;
	/**
	 * The score of the player.
	 */
	private int 		score;
	/**
	 * The number of twistlocks left for this player.
	 */
	private int 		twistlock;
	/**
	 * The number of twistlocks the player has at it's creation.
	 */
	private int 		initialTwistlock;

	private InetAddressWithPort inetAddressWithPort;


	/**
	 * Creates a new instance of player.
	 * @param pseudo            The pseudo of the player to create.
	 * @param initialTwistlock	The number of twistlock the player possess at its creation.
	 */
	public Player (String pseudo, int initialTwistlock, InetAddressWithPort address)
	{
		this.id 		= staticId++;
		this.pseudo 	= pseudo;
		this.initialTwistlock = initialTwistlock;
		this.twistlock 	= initialTwistlock;
		this.score 		= 0;
		this.inetAddressWithPort = address;
	}

	/**
	 * Creates a new instance of player.
	 * @param pseudo The pseudo of the player to create.
	 */
	public Player (String pseudo, InetAddressWithPort address)
	{
		this(pseudo, 20, address);
	}

	public InetAddressWithPort getInetAddress()
	{
		return inetAddressWithPort;
	}

	/**
	 * Reset the static id to create a new game.
	 */
	public static void resetId ()
	{
		staticId = 0;
	}

	/**
	 * Resets the number of twistlocks to its initial value.
	 */
	public void resetTwistlock ()
	{
		resetTwistlock(initialTwistlock);
	}

	/**
	 * Sets the number of twistlocks to the specified value.
	 * @param value The new value of twistlocks.
	 */
	public void resetTwistlock (int value)
	{
		twistlock = value;
	}

	/**
	 * Method called when the twislock is used.
	 */
	public void useTwistlock ()
	{
		this.useTwistlock(1);
	}

	/**
	 * Spends a specified number of twistlocks.
	 * @param number The number of twistlocks to consume.
	 */
	public void useTwistlock (int number)
	{
		this.twistlock = this.twistlock - number;
	}

	/**
	 * Check if the player has twistlocks left.
	 * @return True if the player still has twistlocks, otherwise false.
	 */
	public boolean hasTwistlock ()
	{
		return twistlock > 0;
	}

	/**
	 * Resets the score of this player to 0
	 */
	public void resetScore ()
	{
		score = 0;
	}

	/**
	 * Adds the specified value to the score of this player.
	 * @param value Value to add to the player's score.
	 */
	public void addScore (int value)
	{
		score += value;
	}


	/*---------------*/
	/*    GETTERS    */
	/*---------------*/

	/**
	 * return the number of locks lefts
	 * @return the number of licks lefts
	 */
	public int getTwistLock()
	{
		return twistlock;
	}

	/**
	 * Gets the id of the player.
	 * @return Id of the player.
	 */
	public int getId ()
	{
		return id;
	}

	/**
	 * Returns the pseudo.
	 * @return The pseudo of the player.
	 */
	public String getPseudo()
	{
		return pseudo;
	}

	/**
	 * Returns the score.
	 * @return The score of the player.
	 */
	public int getScore()
	{
		return score;
	}
}
