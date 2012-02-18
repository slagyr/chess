package chess.move;

import chess.notation.*;
import chess.board.SquareInterface;
import chess.piece.Piece;

public abstract class AbstractMove
{
	protected Piece itsPiece;
	protected SquareInterface itsStartSquare;
	protected SquareInterface itsEndSquare;
	protected Piece itsTakenPiece;
	protected String itsType;
	protected int itsScore = -1;
	protected String EOG = "";
	protected boolean isEOG = false;
	
	public abstract boolean isValid();
	public abstract void check();
	public abstract boolean isCheck();
	
	public Piece getPiece()
	{
		return itsPiece;
	}
	
	public Piece getTakenPiece()
	{
		return itsTakenPiece;
	}
	
	public SquareInterface getStartSquare()
	{
		return itsStartSquare;
	}
	
	public SquareInterface getEndSquare()
	{
		return itsEndSquare;
	}
	
	public  String getType()
	{
		return itsType; 
	}
	
	public void setScore(int score)
	{
		itsScore = score;
	}
	
	public int getScore()
	{
		return itsScore;
	}
	
	public boolean endOfGame()
	{
		return isEOG;
	}
	
	public void setEndOfGame(String s)
	{
		EOG = s;
		isEOG = true;
	}
	
	public String getEndOfGame()
	{
		return EOG;
	}
	
	public String getNotation()
    {
        Notation n = new ShortAlgebraicNotation();
        return n.getNotation(this);
    }
}
