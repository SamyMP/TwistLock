package client.job.ia;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;


/**
 * IA of a player.
 */
public class IA3 extends IA
{
    private class Move
    {
        private List<Move>  next;
        private Move        previous;
        private int[]       move;

		public Move ()
		{
			this(-1,-1,-1);
		}
		
        public Move (int row, int col, int corner)
        {
            this.move = new int[] { row, col, corner };
			this.next = new ArrayList<Move>();
        }

        public void addNext (Move next)
        {
            this.next.add(next);
			next.setPrevious(this);
        }

        public void setPrevious (Move previous)
        {
            this.previous = previous;
        }
		
		public int[] getMove()
		{
			return move;
		}

        public Move getNext (int index)
        {
            return this.next.get(index);
        }
		
		public int nbNext ()
        {
            return this.next.size();
        }

        public Move getPrevious ()
        {
            return this.previous;
        }
    }

	/**
	 * Plays for the player, according to its IA level.
	 */
	@Override
	public String calcResponse ()
    {
		Move racine = new Move();
        Move answer;

        // Level 3 : Intelligent IA
        if (this.containers == null)
            return "";


        // Looks for all the possible moves and saves them
        for (int i = 0; i < this.containers.length; i++)
        {
            for (int j = 0; j < this.containers[i].length; j++)
            {
                for (int k = 1; k <= 4; k++)
                {
                    if ( this.containers[i][j].getLock(k).getOwner() == -1 )
					{
						Move next = new Move(i, j, k);
                        racine.addNext( next );
						
						for (int i2 = 0; i2 < this.containers.length; i2++)
						{
							for (int j2 = 0; j2 < this.containers[i2].length; j2++)
							{
								for (int k2 = 1; k2 <= 4; k2++)
								{
									if ( this.containers[i][j].getLock(k).getOwner() == -1 && this.containers[i][j].getLock(k) != this.containers[i2][j2].getLock(k2) )
									{
										Move nextNext = new Move(i, j, k);
										next.addNext( nextNext );
									}
								}
							}
						}
					}
                }
            }
        }
		
		Move moveMax = null;
		int diffMax = -1;
		
		for (int i = 0; i < racine.nbNext() ; i++)
		{
			
			Move next = racine.getNext(i);
			
			int[] move = next.getMove();
			
			int currentValue;
			
			switch (this.containers[move[0]][move[1]].getOwner())
			{
				case 0:
					currentValue = this.containers[move[0]][move[1]].getValue();
					break;
					
				case 1:
					currentValue = -1 * this.containers[move[0]][move[1]].getValue();
					break;
					
				default:
					currentValue = 0;
					break;
			}
			
			int newValeur;
			Map<Integer, Integer> controlSupp = new HashMap<Integer, Integer>();
			controlSupp.put(0,1);
			switch (this.containers[move[0]][move[1]].calcOwner(controlSupp))
			{
				case 0:
					newValeur = this.containers[move[0]][move[1]].getValue();
					break;
					
				case 1:
					newValeur = -1 * this.containers[move[0]][move[1]].getValue();
					break;
					
				default:
					newValeur = 0;
					break;
			}
			
			int diff = newValeur - currentValue ;
			
			if (diff > diffMax)
			{
				diffMax = diff;
				moveMax = next;
			}
		}


        answer = moveMax;
		System.out.println(answer);
        String toSend = String.format("%d%c%d", answer.getMove()[0] + 1, answer.getMove()[1]+'A', answer.getMove()[2]);

        try
        {
            Thread.sleep(20);
        } catch (Exception e) {}
        this.saveAction(toSend, 0);
        return toSend;
    }
}
