package chess.piece;

import chess.board.SquareGlobals;

public class Queen extends GamePiece
{
	public Queen(String color)
	{
		super(color);
		itsType = PieceGlobals.QUEEN;
	}
	
	public void findMoves()
	{
		clearAttacks();
		itsMoveOptions.clear();
		findMovesInDirection(SquareGlobals.N);
		findMovesInDirection(SquareGlobals.NE);
		findMovesInDirection(SquareGlobals.E);
		findMovesInDirection(SquareGlobals.SE);
		findMovesInDirection(SquareGlobals.S);
		findMovesInDirection(SquareGlobals.SW);
		findMovesInDirection(SquareGlobals.W);
		findMovesInDirection(SquareGlobals.NW);
		removeCheckRevealingMoves();
	}
	
	public char getTextType()
	{
		if(itsColor.equals(PieceGlobals.WHITE))
			return 'Q';
		else
			return 'q';	
	}
	
	public boolean canMoveInDirection(int direction)
	{
		if ( direction != -1)
			return true;
		else
			return false;			
	}
}
