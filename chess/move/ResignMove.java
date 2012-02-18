package chess.move;

import chess.piece.*;
import chess.board.EmptySquare;

public class ResignMove extends AbstractMove
{
	public ResignMove(Piece piece)
	{
		itsType = MoveGlobals.RESIGN;
		itsPiece = piece;
		itsStartSquare = new EmptySquare("ResignMove");
		itsEndSquare = new EmptySquare("ResignMove");
		itsTakenPiece = new MockPiece();
        EOG = MoveGlobals.RESIGN;
	}
	
	public boolean isValid()
	{
		return true;
	}
	
	public boolean endOfGame()
	{
		return true;
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
		buffer.append(itsPiece.getColor()).append(" ");
		buffer.append("resigns.");
		return buffer.toString();
	}
}
