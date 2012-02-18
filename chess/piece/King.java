package chess.piece;

import chess.board.*;
import chess.move.*;

import java.util.Iterator;

public class King extends GamePiece
{
	public King(String color)
	{
		super(color);
		itsType = PieceGlobals.KING;
	}
	
	public void findMoves()
	{
		clearAttacks();
		itsMoveOptions.clear();
		checkSquare(itsLocation.getNeighbor(SquareGlobals.N));
		checkSquare(itsLocation.getNeighbor(SquareGlobals.NE));
		checkSquare(itsLocation.getNeighbor(SquareGlobals.E));
		checkSquare(itsLocation.getNeighbor(SquareGlobals.SE));
		checkSquare(itsLocation.getNeighbor(SquareGlobals.S));
		checkSquare(itsLocation.getNeighbor(SquareGlobals.SW));
		checkSquare(itsLocation.getNeighbor(SquareGlobals.W));
		checkSquare(itsLocation.getNeighbor(SquareGlobals.NW));
		checkForCastle();
	}
	
	public AbstractMove makeMove(SquareInterface square)
	{
		itsAttackers.clear();
		AbstractMove move = new IllegalMove();
		SquareInterface startSquare = itsLocation;
		if (isKingSideCastle(square))
		{
			performKingSideCastle();
			move = new KingSideCastleMove(this, startSquare, itsLocation);
		}
		else if (isQueenSideCastle(square))
		{
			performQueenSideCastle();
			move = new QueenSideCastleMove(this, startSquare, itsLocation);
		}
		else
			move = super.makeMove(square);
		return move;	
	}
	
	private void checkSquare(SquareInterface suspect)
	{
		if ( suspect.isValid())
		{
			addAttack(suspect);
			if(! suspect.isAttackedBy(enemyColor()) )
				addMoveBasedOnOccupant(suspect);
		}	
	}
	
	public char getTextType()
	{
		if(itsColor.equals(PieceGlobals.WHITE))
			return 'K';
		else
			return 'k';	
	}
	
	public boolean isPieceBlockingCheck(Piece p)
	{
		return false;
	}
	
	public void reverseMove(AbstractMove move)
	{
		if(move.getType().equals(MoveGlobals.KING_SIDE_CASTLE))
			undoKingSideCastle();
		else if(move.getType().equals(MoveGlobals.QUEEN_SIDE_CASTLE))
			undoQueenSideCastle();
		else
			super.reverseMove(move);	
	}
	
	private void checkForCastle()
	{
		if (canCastle())
		{
			Piece kingsRook = 
				itsLocation.getNeighbor(SquareGlobals.E).getNeighbor(SquareGlobals.E).getNeighbor(SquareGlobals.E).getOccupant();
			Piece queensRook = 
				itsLocation.getNeighbor(SquareGlobals.W).getNeighbor(SquareGlobals.W).getNeighbor(SquareGlobals.W).getNeighbor(SquareGlobals.W).getOccupant();
			
			if ( kingsRook.isValid() && kingsRook.getTrail().size() == 1 )
			{
				boolean fIsOpen = isSquareSafeForCastle(itsLocation.getNeighbor(SquareGlobals.E), enemyColor());
				boolean gIsOpen = isSquareSafeForCastle(itsLocation.getNeighbor(SquareGlobals.E).getNeighbor(SquareGlobals.E), enemyColor());
				if( fIsOpen && gIsOpen )
					itsMoveOptions.add(itsLocation.getNeighbor(SquareGlobals.E).getNeighbor(SquareGlobals.E));
			}
			
			if ( queensRook.isValid() && queensRook.getTrail().size() == 1 )
			{
				boolean dIsOpen = isSquareSafeForCastle(itsLocation.getNeighbor(SquareGlobals.W), enemyColor());
				boolean cIsOpen = isSquareSafeForCastle(itsLocation.getNeighbor(SquareGlobals.W).getNeighbor(SquareGlobals.W), enemyColor());
				boolean bIsOpen = isSquareSafeForCastle(itsLocation.getNeighbor(SquareGlobals.W).getNeighbor(SquareGlobals.W).getNeighbor(SquareGlobals.W), enemyColor());
				if( dIsOpen && cIsOpen && bIsOpen)
					itsMoveOptions.add(itsLocation.getNeighbor(SquareGlobals.W).getNeighbor(SquareGlobals.W));
			}
		}
	}
	
	private boolean canCastle()
	{
		String homeSquare = itsColor.equals(PieceGlobals.WHITE) ? "e1" : "e8";
		boolean isHome = itsLocation.toString().equals(homeSquare);
		boolean unMoved = itsTrail.size() == 1;
		boolean notInCheck = ! itsLocation.isAttackedBy(enemyColor());
		return (isHome && unMoved && notInCheck);
	}
	
	private boolean isSquareSafeForCastle(SquareInterface square, String enemyColor)
	{
		boolean notOccupied = ! square.isOccupied();
		boolean notAttacked = ! square.isAttackedBy(enemyColor);
		return (notOccupied && notAttacked);
	}
	
	private boolean isKingSideCastle(SquareInterface square)
	{
		boolean returnValue = false;
		if (itsLocation.toString().equals("e1") && square.toString().equals("g1"))
			returnValue = true;
		if (itsLocation.toString().equals("e8") && square.toString().equals("g8"))
			returnValue = true;	
		return returnValue;
	}
	
	private boolean isQueenSideCastle(SquareInterface square)
	{
		boolean returnValue = false;
		if (itsLocation.toString().equals("e1") && square.toString().equals("c1"))
			returnValue = true;
		if (itsLocation.toString().equals("e8") && square.toString().equals("c8"))
			returnValue = true;	
		return returnValue;
	}
	
	private void performKingSideCastle()
	{
		SquareInterface kingToSquare = itsLocation.getNeighbor(SquareGlobals.E).getNeighbor(SquareGlobals.E);
		SquareInterface rookFromSquare = itsLocation.getNeighbor(SquareGlobals.E).getNeighbor(SquareGlobals.E).getNeighbor(SquareGlobals.E);
		SquareInterface rookToSquare = itsLocation.getNeighbor(SquareGlobals.E);
		Piece rook = rookFromSquare.getOccupant();
		itsLocation.setOccupant(new MockPiece());
		kingToSquare.setOccupant(this);
		setLocation(kingToSquare);
		rookFromSquare.setOccupant(new MockPiece());
		rookToSquare.setOccupant(rook);
		rook.setLocation(rookToSquare);
	}
	
	private void performQueenSideCastle()
	{
		SquareInterface kingToSquare = itsLocation.getNeighbor(SquareGlobals.W).getNeighbor(SquareGlobals.W);
		SquareInterface rookFromSquare = itsLocation.getNeighbor(SquareGlobals.W).getNeighbor(SquareGlobals.W).getNeighbor(SquareGlobals.W).getNeighbor(SquareGlobals.W);
		SquareInterface rookToSquare = itsLocation.getNeighbor(SquareGlobals.W);
		Piece rook = rookFromSquare.getOccupant();
		itsLocation.setOccupant(new MockPiece());
		kingToSquare.setOccupant(this);
		setLocation(kingToSquare);
		rookFromSquare.setOccupant(new MockPiece());
		rookToSquare.setOccupant(rook);
		rook.setLocation(rookToSquare);
	}
	
	private void undoKingSideCastle()
	{
		Piece rook = itsLocation.getNeighbor(SquareGlobals.W).getOccupant();
		itsLocation.setOccupant(new MockPiece());
		rook.getLocation().setOccupant(new MockPiece());
		SquareInterface homeSquare = itsLocation.getNeighbor(SquareGlobals.W).getNeighbor(SquareGlobals.W);
		SquareInterface rookSquare = itsLocation.getNeighbor(SquareGlobals.E);
		homeSquare.setOccupant(this);
		rookSquare.setOccupant(rook);
		rook.stepBack();
		stepBack();
	}
	
	private void undoQueenSideCastle()
	{
		Piece rook = itsLocation.getNeighbor(SquareGlobals.E).getOccupant();
		itsLocation.setOccupant(new MockPiece());
		rook.getLocation().setOccupant(new MockPiece());
		SquareInterface homeSquare = itsLocation.getNeighbor(SquareGlobals.E).getNeighbor(SquareGlobals.E);
		SquareInterface rookSquare = itsLocation.getNeighbor(SquareGlobals.W).getNeighbor(SquareGlobals.W);
		homeSquare.setOccupant(this);
		rookSquare.setOccupant(rook);
		rook.stepBack();
		stepBack();
	}
}
