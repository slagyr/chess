package chess.scoring;

import chess.util.Props;
import chess.board.ChessBoard;
import chess.player.Player;
import chess.piece.*;

import java.util.*;

public class PieceDevelopmentScorer implements Scorer
{
	private float developmentValue;

	public PieceDevelopmentScorer()
	{
		try
		{
			developmentValue = new Float(Props.get("piece.development.value")).floatValue();
		}
		catch(Exception e)
		{
			System.err.println("Corrupted development value in properties file");
			System.err.println(e);
		}
	}

	public int getScore(ChessBoard board, Player player)
	{
		float playerPoints = addPoints(player);
		float opponentPoints = addPoints(player.getOpponent());
		float totalPoints = playerPoints + opponentPoints;

		return (int)(1000 * (playerPoints / totalPoints));
	}

	private float addPoints(Player player)
	{
		float points = 1;
		for(Iterator p = player.getPieces().iterator(); p.hasNext();)
		{
			Piece piece = (Piece)p.next();
			if (piece.getType().equals(PieceGlobals.BISHOP) ||
			    piece.getType().equals(PieceGlobals.KNIGHT) )
			{
				Collection trail = piece.getTrail();
				if(trail.size() > 1)
					points += developmentValue;
			}
		}
		return points;
	}

    //used for testing
    public float getValueSum()
    {
        return developmentValue;
    }
}

