package chess.scoring;

import chess.util.Props;
import chess.board.*;
import chess.player.Player;
import chess.piece.*;

import java.util.*;

public class PawnScorer implements Scorer
{	
	private float doubledPawnValue;
	private float passedPawnValue;
	private float passedPawnPromotionValue;
	
	private float temp1 = 0;
	private float temp2 = 0;
	
	public PawnScorer()
	{
		try
		{
			doubledPawnValue = new Float(Props.get("doubled.pawn.value")).floatValue();
			passedPawnValue = new Float(Props.get("passed.pawn.value")).floatValue();
			passedPawnPromotionValue = new Float(Props.get("passed.pawn.promotion.value")).floatValue();
		}
		catch(Exception e)
		{
			System.err.println("Corrupted development value in properties file");
			System.err.println(e);
		}
	}
	
	public int getScore(ChessBoard board, Player player)
	{
		float playerPoints = 1;
		float opponentPoints = 1;
		
		addPoints(player);
		playerPoints += temp1;
		opponentPoints += temp2;
		
		addPoints(player.getOpponent());
		opponentPoints += temp1;
		playerPoints += temp2;	
		
		float totalPoints = playerPoints + opponentPoints;
		
		return (int)(1000 * (playerPoints / totalPoints));
	}			   
	
	private void addPoints(Player player)
	{
		temp1 = 0;
		temp2 = 0;
		for(Iterator i = player.getPieces().iterator(); i.hasNext(); )
		{
			Piece piece = (Piece)i.next();
			if (piece.getType().equals(PieceGlobals.PAWN))
			{
				temp2 += pointsForDoubledPawn(piece);
				if (isPawnPassed(piece))
				{
					temp1 += passedPawnValue;
					temp1 += pointsForPromotionDistance(piece);
				}
			}
		}
	}
	
	private float pointsForDoubledPawn(Piece pawn)
	{
		float retVal = 0;
		int direction = 0;
		String color = pawn.getColor();
		if(color.equals(PieceGlobals.WHITE))
			direction = SquareGlobals.S;
		else
			direction = SquareGlobals.N;
			
		for(SquareInterface square = pawn.getLocation().getNeighbor(direction);
			square.isValid();
			square = square.getNeighbor(direction))
		{
			Piece piece = square.getOccupant();
			if (piece.isValid() && piece.getColor().equals(color) && 
				piece.getType().equals(PieceGlobals.PAWN))
			{
				retVal = doubledPawnValue;
				break;
			}
		}
		return retVal;	
	}
	
	private boolean isPawnPassed(Piece pawn)
	{
		int direction = 0;
		String color = pawn.getColor();
		if(color.equals(PieceGlobals.WHITE))
			direction = SquareGlobals.N;
		else
			direction = SquareGlobals.S;
		
		boolean fOpposed = 	isPawnOpposed(pawn.getLocation(), direction, color);
		
		boolean wOpposed = false;
		SquareInterface square = pawn.getLocation().getNeighbor(SquareGlobals.W);
		if(square.isValid())
			wOpposed = isPawnOpposed(square, direction, color);
			
		boolean eOpposed = false;
		square = pawn.getLocation().getNeighbor(SquareGlobals.E);
		if(square.isValid())
			eOpposed = isPawnOpposed(square, direction, color);
	
		if( !fOpposed && !wOpposed && !eOpposed)
			return true;
		else
			return false;	
	}
	
	private boolean isPawnOpposed(SquareInterface location, int forward, String color)
	{
		boolean retVal = false;
		for(SquareInterface square = location.getNeighbor(forward);
			square.isValid();
			square = square.getNeighbor(forward))
		{
			Piece piece = square.getOccupant();
			if (piece.isValid() && !piece.getColor().equals(color) &&
				piece.getType().equals(PieceGlobals.PAWN))
			{
				retVal = true;
				break;
			}
		}
		return retVal;
	}
	
	private float pointsForPromotionDistance(Piece pawn)
	{
		int direction = 0;
		String color = pawn.getColor();
		if(color.equals(PieceGlobals.WHITE))
			direction = SquareGlobals.N;
		else
			direction = SquareGlobals.S;
		
		int distance = 0;
		for(SquareInterface square = pawn.getLocation().getNeighbor(direction);
			square.isValid();
			square = square.getNeighbor(direction))
		{
			distance++;
		}
		
		float value = ((6 - distance) * passedPawnPromotionValue);
		return value;
	}

    //used for testing
    public float getValueSum()
    {
        float sum = passedPawnPromotionValue;
        sum += passedPawnValue;
        sum += doubledPawnValue;
        return sum;
    }
}
 