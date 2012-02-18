package chess.move;

import chess.piece.*;
import chess.board.SquareInterface;

public class QueenSideCastleMove extends AbstractMove
{
	private boolean isCheck;

	public QueenSideCastleMove(Piece piece, SquareInterface start, SquareInterface end)
	{
		itsType = MoveGlobals.QUEEN_SIDE_CASTLE;
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
		return "0-0-0";
	}
}