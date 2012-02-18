package chess.move;

import chess.piece.*;
import chess.board.*;

public class IllegalMove extends AbstractMove
{
	public IllegalMove()
	{
		itsType = MoveGlobals.ILLEGAL;
		itsPiece = new MockPiece();
		itsTakenPiece = new MockPiece();
		itsStartSquare = new EmptySquare("IllegalMove");
		itsEndSquare = new EmptySquare("IllegalMove");;
	}
	
	public IllegalMove(Piece piece, SquareInterface start, SquareInterface end)
	{
		itsType = MoveGlobals.ILLEGAL;
		itsPiece = piece;
		itsTakenPiece = new MockPiece();
		itsStartSquare = start;
		itsEndSquare = end;
	}
	
	public boolean isValid()
	{
		return false;
	}
	
	public boolean endOfGame()
	{
		return false;
	}
	
	public void check()
	{
	}
	
	public boolean isCheck()
	{
		return false;
	}
	
	public String toString()
	{
		StringBuffer buffer = new StringBuffer(10);
		buffer.append("Illegal move: ");
		buffer.append(itsStartSquare.toString());
		buffer.append(" ");
		buffer.append(itsEndSquare.toString());	
		return buffer.toString();
	}
}
