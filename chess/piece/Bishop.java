package chess.piece;

import chess.board.SquareGlobals;

public class Bishop extends GamePiece
{
	public Bishop(String color)
	{
		super(color);
		itsType = PieceGlobals.BISHOP;
	}
	
	public void findMoves()
	{
		clearAttacks();
		itsMoveOptions.clear();
		findMovesInDirection(SquareGlobals.NE);
		findMovesInDirection(SquareGlobals.SE);
		findMovesInDirection(SquareGlobals.SW);
		findMovesInDirection(SquareGlobals.NW);
		removeCheckRevealingMoves();
	}
	
	public char getTextType()
	{
		if(itsColor.equals(PieceGlobals.WHITE))
			return 'B';
		else
			return 'b';	
	}
	
	public boolean canMoveInDirection(int direction)
	{
		if ( direction == SquareGlobals.NE ||
			 direction == SquareGlobals.SE ||
			 direction == SquareGlobals.SW ||
			 direction == SquareGlobals.NW )
			return true;
		else
			return false;			
	}
}
