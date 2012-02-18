package chess.piece;

import chess.board.SquareGlobals;

public class Rook extends GamePiece
{
	public Rook(String color)
	{
		super(color);
		itsType = PieceGlobals.ROOK;
	}
	
	public void findMoves()
	{
		clearAttacks();
		itsMoveOptions.clear();
		findMovesInDirection(SquareGlobals.N);
		findMovesInDirection(SquareGlobals.E);
		findMovesInDirection(SquareGlobals.S);
		findMovesInDirection(SquareGlobals.W);
		removeCheckRevealingMoves();
	}
	
	public char getTextType()
	{
		if(itsColor.equals(PieceGlobals.WHITE))
			return 'R';
		else
			return 'r';	
	}
	
	public boolean canMoveInDirection(int direction)
	{
		if ( direction == SquareGlobals.N ||
			 direction == SquareGlobals.E ||
			 direction == SquareGlobals.S ||
			 direction == SquareGlobals.W )
			return true;
		else
			return false;			
	}
}
