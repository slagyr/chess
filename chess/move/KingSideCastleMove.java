package chess.move;

import chess.board.SquareInterface;
import chess.piece.*;

public class KingSideCastleMove extends AbstractMove
{
	private boolean isCheck;

	public KingSideCastleMove(Piece piece, SquareInterface start, SquareInterface end)
	{
		itsType = MoveGlobals.KING_SIDE_CASTLE;
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
		return "0-0";
	}
}
