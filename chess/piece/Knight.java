package chess.piece;

import chess.board.*;

public class Knight extends GamePiece
{
	public Knight(String color)
	{
		super(color);
		itsType = PieceGlobals.KNIGHT;
	}
	
	public void findMoves()
	{
		clearAttacks();
		itsMoveOptions.clear();
		SquareInterface suspect = itsLocation.getNeighbor(SquareGlobals.NE);
		if (suspect.isValid())
		{
			addMoveBasedOnOccupant(suspect.getNeighbor(SquareGlobals.N));
			addMoveBasedOnOccupant(suspect.getNeighbor(SquareGlobals.E));
		}
		suspect = itsLocation.getNeighbor(SquareGlobals.SE);
		if (suspect.isValid())
		{
			addMoveBasedOnOccupant(suspect.getNeighbor(SquareGlobals.E));
			addMoveBasedOnOccupant(suspect.getNeighbor(SquareGlobals.S));
		}
		suspect = itsLocation.getNeighbor(SquareGlobals.SW);
		if (suspect.isValid())
		{
			addMoveBasedOnOccupant(suspect.getNeighbor(SquareGlobals.S));
			addMoveBasedOnOccupant(suspect.getNeighbor(SquareGlobals.W));
		}
		suspect = itsLocation.getNeighbor(SquareGlobals.NW);
		if (suspect.isValid())
		{
			addMoveBasedOnOccupant(suspect.getNeighbor(SquareGlobals.W));
			addMoveBasedOnOccupant(suspect.getNeighbor(SquareGlobals.N));
		}
		removeCheckRevealingMoves();
	}
	
	public char getTextType()
	{
		if(itsColor.equals(PieceGlobals.WHITE))
			return 'N';
		else
			return 'n';	
	}
	
	public boolean isPieceBlockingCheck(Piece p)
	{
		return false;
	}
}
