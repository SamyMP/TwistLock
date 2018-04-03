package client.job.ia;

import java.util.List;
import java.util.ArrayList;

/**
 * IA of a player.
 */
public class IA2 extends IA
{
	/**
	 * Plays for the player, according to its IA level.
	 */
	@Override
	public String calcResponse ()
    {
        Integer[] answer;

        if (this.containers == null)
            return "";


        // Looks for all the possible moves and saves them
        List<Integer[]> moves = new ArrayList<Integer[]>();
        for (int i = 0; i < this.containers.length; i++)
        {
            for (int j = 0; j < this.containers[i].length; j++)
            {
                for (int k = 1; k <= 4; k++)
                {
                    if ( this.containers[i][j].getLock(k).getOwner() == -1 )
                        moves.add( new Integer[] { i, j, k } );

                }
            }
        }

        // Level 2 : Random but not the one already taken
        answer = moves.get( (int)(Math.random()*moves.size()) );

        String toSend = String.format("%d%c%d", answer[0] + 1, answer[1]+'A', answer[2]);
        this.saveAction(toSend, 0);
        return toSend;
    }
}
