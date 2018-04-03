package client.job;


/**
 * Twistlock located in the corner of a container.
 * @author Javane
 * @version 2018-03-26
 */
public class Twistlock
{
	/**
	 * The id of the owner owning the lock, equals to -1 if the lock is not owned.
	 */
	private int 		owner;
	/** Containers adjacent to the lock. */
	private Container[] containers;


	/**
	 * Creates a new twistlock with no owner and an empty
	 */
	public Twistlock ()
	{
		this.owner 	= -1;
		this.containers = new Container[0];
	}

	/**
	 * Add a neighbouring container to the twistlock.
	 * @param container Container to add as a neighbour of this twistlock.
	 */
	public void addContainer (Container container)
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
	 * Makes the owner whose index is specified capturing this lock.
	 * @param owner Index of the new owner.
     * @return True if the capture succeeded, otherwise false.
	 */
	public boolean capture (int owner)
	{
		if (this.owner == -1)
		{
			this.owner = owner;
			for (Container container : containers) { container.calcOwner(); }
			return true;
		}
		else
		{
			return false;
		}
	}

	public int getOwner()
	{
		return owner;
	}
}
