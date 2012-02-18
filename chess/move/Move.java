package chess.move;

import chess.board.SquareInterface;
import chess.piece.*;

public class Move extends AbstractMove
{
	protected boolean isCheck;

	public Move(Piece piece, SquareInterface start, SquareInterface end, Piece taken)
	{
		itsType = MoveGlobals.MOVE;
		itsPiece = piece;
		itsTakenPiece = taken;
		itsStartSquare = start;
		itsEndSquare = end;
		isCheck = false;
	}
	
	public Move(Piece piece, SquareInterface start, SquareInterface end)
	{
		itsType = MoveGlobals.MOVE;
		itsPiece = piece;
		itsTakenPiece = new MockPiece();
		itsStartSquare = start;
		itsEndSquare = end;
		isCheck = false;
	}
	
	public boolean isValid()
	{
		return true;
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
		if(itsTakenPiece.isValid())
			buffer.append("x");
		else
			buffer.append(" ");
		buffer.append(itsEndSquare.toString());	
		if(isCheck)
			buffer.append("+");
		return buffer.toString();
	}
}
