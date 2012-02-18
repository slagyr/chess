package chess.scoring;

import chess.util.Props;
import chess.board.*;
import chess.player.Player;
import chess.piece.Piece;

import java.util.*;

public class CoverageScorer implements Scorer
{	
	private float centerValue;
	private float semiCenterValue;
	private float nonCenterValue;
	
	public static int count = 0;
	
	public CoverageScorer()
	{
		try
		{
			centerValue     = new Float(Props.get("center.value")).floatValue();
			semiCenterValue = new Float(Props.get("semiCenter.value")).floatValue();
			nonCenterValue  = new Float(Props.get("nonCenter.value")).floatValue();
		}
		catch(Exception e)
		{
			System.err.println("Corrupted square values in properties file");
			System.err.println(e);
		}
	}
	
	public int getScore(ChessBoard board, Player player)
	{
		count++;
		float playerPoints = addCoveragePoints(player);
		float opponentPoints = addCoveragePoints(player.getOpponent());
		float totalPoints = playerPoints + opponentPoints;
		
		return (int)(1000 * (playerPoints / totalPoints));
	}			   
	
	private float addCoveragePoints(Player player)
	{
		float points = 0;
		for(Iterator p = player.getPieces().iterator(); p.hasNext();)
		{
			Piece piece = (Piece)p.next();
			for(Iterator s = piece.getAttacks().iterator(); s.hasNext();)
			{
				SquareInterface square = (SquareInterface)s.next();
				if(isCenterSquare(square, player.getBoard()))
					points += centerValue;
				else if(isSemiCenterSquare(square, player.getBoard()))
					points += semiCenterValue;
				else
					points += nonCenterValue;	
			}
		}
		return points;
	}
	
	private boolean isCenterSquare(SquareInterface square, ChessBoard board)
	{
		boolean returnValue = false;
		if(square == board.getSquare("d4"))
			returnValue = true;
		else if(square == board.getSquare("d5"))
			returnValue = true;
		else if(square == board.getSquare("e4"))
			returnValue = true;
		else if(square == board.getSquare("e5"))
			returnValue = true;
		return returnValue;	
	}
	
	private boolean isSemiCenterSquare(SquareInterface square, ChessBoard board)
	{
		boolean returnValue = false;
		if(square == board.getSquare("c6"))
			returnValue = true;
		else if(square == board.getSquare("d6"))
			returnValue = true;
		else if(square == board.getSquare("e6"))
			returnValue = true;
		else if(square == board.getSquare("f6"))
			returnValue = true;
		else if(square == board.getSquare("f5"))
			returnValue = true;
		else if(square == board.getSquare("f4"))
			returnValue = true;
		else if(square == board.getSquare("f3"))
			returnValue = true;
		else if(square == board.getSquare("e3"))
			returnValue = true;
		else if(square == board.getSquare("d3"))
			returnValue = true;
		else if(square == board.getSquare("c3"))
			returnValue = true;
		else if(square == board.getSquare("c4"))
			returnValue = true;
		else if(square == board.getSquare("c5"))
			returnValue = true;
		return returnValue;	
	}

    //used for testing
    public float getValueSum()
    {
        float sum = centerValue;
        sum += semiCenterValue;
        sum += nonCenterValue;
        return sum;
    }
}
 