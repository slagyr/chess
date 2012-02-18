package chess.move;

import chess.piece.Piece;
import chess.board.SquareInterface;

public class EnpassantMove extends AbstractMove
{
	private boolean isCheck;
	private SquareInterface itsTakensSquare;

	public EnpassantMove(Piece piece, SquareInterface start, SquareInterface end, Piece taken, SquareInterface takensSquare)
	{
		itsType = MoveGlobals.ENPASSANT;
		itsPiece = piece;
		itsTakenPiece = taken;
		itsStartSquare = start;
		itsEndSquare = end;
		itsTakensSquare = takensSquare;
		isCheck = false;
	}
	
	public SquareInterface getTakensSquare()
	{
		return itsTakensSquare;
	}
	
	public boolean isValid()
	{
		return true;
	}
	
	public boolean endOfGame()
	{
		return false;
	}
	
	public void check()
	{
		isCheck = true;
	}
	
	public boolean isCheck()
	{
		return isCheck;
	}
	
	public String toString()
	{
		StringBuffer buffer = new StringBuffer(10);
		buffer.append(itsStartSquare.toString()).append("");
		buffer.append("x");
		buffer.append(itsEndSquare.toString());	
		if(isCheck)
			buffer.append("+");
		return buffer.toString();
	}
}
