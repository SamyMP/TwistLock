package client.job.ia;


/**
 * IA of a player.
 */
public class IA1 extends IA
{
	/**
	 * Plays for the player, according to its IA level.
	 */
	@Override
	public String calcResponse ()
    {
        int[] answer = new int[3];

        if (this.containers == null)
            return "";


        int nbRow 	= this.containers.length;
        int nbCol 	= this.containers[0].length;

        // Level 1 : Complete Random
        answer[0]	= (int)(Math.random()*(nbRow - 1)) + 1;
        answer[1]	= (int)(Math.random()*(nbCol - 1)) + 1;
        answer[2]	= (int)(Math.random()*4) + 1;

        String toSend = String.format("%d%c%d", answer[0], answer[1]+'A', answer[2]);

		try
		{
			Thread.sleep(400);
		} catch (Exception e) {}
		this.saveAction(toSend, 0);
        return toSend;
    }
}
