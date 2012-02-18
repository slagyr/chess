package chess.scoring;

import chess.util.Props;
import chess.board.ChessBoard;
import chess.player.Player;
import chess.piece.*;

import java.util.*;

public class MaterialScorer implements Scorer
{	
	private float queenValue;
	private float rookValue ;
	private float bishopValue;
	private float knightValue;
	private float pawnValue;
	
	public MaterialScorer()
	{
		try
		{
			queenValue  = new Float(Props.get("queen.value")).floatValue();
			rookValue   = new Float(Props.get("rook.value")).floatValue();
			bishopValue = new Float(Props.get("bishop.value")).floatValue();
			knightValue = new Float(Props.get("knight.value")).floatValue();
			pawnValue   = new Float(Props.get("pawn.value")).floatValue();
		}
		catch(Exception e)
		{
			System.err.println("Corrupted piece values in properties file");
			System.err.println(e);
		}
	}
	
	public int getScore(ChessBoard board, Player player)
	{
		float playerPoints = addPiecePoints(player);
		float opponentPoints = addPiecePoints(player.getOpponent());
		float totalPoints = playerPoints + opponentPoints;
		
		return (int)(1000 * (playerPoints / totalPoints));
	}
	
	private float addPiecePoints(Player player)
	{
		float points = 0;
		for(Iterator i = player.getPieces().iterator(); i.hasNext();)
		{
			Piece piece = (Piece)i.next();
			String type = piece.getType();
			if(PieceGlobals.QUEEN.equals(type))
				points += queenValue;
			else if(PieceGlobals.ROOK.equals(type))
				points += rookValue;
			else if(PieceGlobals.BISHOP.equals(type))
				points += bishopValue;
			else if(PieceGlobals.KNIGHT.equals(type))
				points += knightValue;
			else if(PieceGlobals.PAWN.equals(type))
				points += pawnValue;
		}
		return points;
	}

    //used for testing
    public float getValueSum()
    {
        float sum = queenValue;
        sum += rookValue;
        sum += bishopValue;
        sum += knightValue;
        sum += pawnValue;
        return sum;
    }
}
 