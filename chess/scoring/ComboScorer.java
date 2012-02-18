package chess.scoring;

import chess.board.ChessBoard;
import chess.player.Player;

import java.util.*;

public class ComboScorer implements Scorer
{
	private HashMap itsScorers;
	
	public ComboScorer()
	{
		itsScorers = new HashMap();
	}

	public int getScore(ChessBoard board, Player player)
	{
		int weightSum = 0;
		int scoreSum = 0;
		Set scorers = itsScorers.keySet();
		for(Iterator i = scorers.iterator(); i.hasNext();)
		{
			Scorer scorer = (Scorer)i.next();
			int weight = ((Integer)itsScorers.get(scorer)).intValue();
			weightSum += weight;
			int score = scorer.getScore(board, player);
			scoreSum += (score * weight);
		}
		return (scoreSum / weightSum);
	}
	
	public void addScorer(Scorer scorer, int weight)
	{
		itsScorers.put(scorer, new Integer(weight));
	}

    //used for testing
    public float getValueSum()
    {
        float sum = 0;
		Set scorers = itsScorers.keySet();
        for(Iterator i = scorers.iterator(); i.hasNext();)
        {
            sum += ((Scorer)i.next()).getValueSum();
        }
        return sum;
    }
}
 