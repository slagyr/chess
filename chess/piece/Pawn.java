package chess.piece;

import chess.move.*;
import chess.board.*;

import java.util.*;

public class Pawn extends GamePiece
{
	private Piece itsWestEnpassant;
	private Piece itsEastEnpassant;

	public Pawn(String color)
	{
		super(color);
		itsType = PieceGlobals.PAWN;
		itsWestEnpassant = new MockPiece();
		itsEastEnpassant = new MockPiece();
	}
	
	public void findMoves()
	{
		clearAttacks();
		itsMoveOptions.clear();
		checkOneStep();
		checkTwoStep();
		checkTakes();
		checkEnpassants();
		setEnPassants();
		removeCheckRevealingMoves();
	}
	
	public void findMoves_safe()
	{
		itsMoveOptions.clear();
		checkOneStep();
		checkTwoStep();
		checkTakes();
		checkEnpassants();
		removeCheckRevealingMoves();
	}
	
	public AbstractMove makeMove(SquareInterface square)
	{
		itsAttackers.clear();
		AbstractMove move = new IllegalMove();
		if(isEnpassantMove(square))
			move = performEnpassant(square);
		else if(isPromotionMove(square))
			move = doPromotion(square);	
		else
			move = super.makeMove(square);	
		return move;	
	}
	
	public char getTextType()
	{
		if(itsColor.equals(PieceGlobals.WHITE))
			return 'P';
		else
			return 'p';	
	}
	
	public boolean isPieceBlockingCheck(Piece p)
	{
		return false;
	}
	
	private void checkOneStep()
	{
		SquareInterface suspect;
		if (itsColor.equals(PieceGlobals.WHITE))
			suspect = itsLocation.getNeighbor(SquareGlobals.N);
		else
			suspect = itsLocation.getNeighbor(SquareGlobals.S);	
		addMoveBasedOnOccupant(suspect, false);
	}
	
	private void checkTwoStep()
	{
		SquareInterface suspect = new EmptySquare("Pawn");
		if (itsColor.equals(PieceGlobals.WHITE))
		{
			if ( itsLocation.getPrintableRank() == 2 && !itsLocation.getNeighbor(SquareGlobals.N).isOccupied())
				suspect = itsLocation.getNeighbor(SquareGlobals.N).getNeighbor(SquareGlobals.N);
		}
		else
		{
			if ( itsLocation.getPrintableRank() == 7 && !itsLocation.getNeighbor(SquareGlobals.S).isOccupied())
				suspect = itsLocation.getNeighbor(SquareGlobals.S).getNeighbor(SquareGlobals.S);
		}
		addMoveBasedOnOccupant(suspect, false);
	}
	
	private void checkTakes()
	{
		if (itsColor.equals(PieceGlobals.WHITE))
		{
			checkMoveForTake(SquareGlobals.NE);
			checkMoveForTake(SquareGlobals.NW);
		}
		else
		{
			checkMoveForTake(SquareGlobals.SE);
			checkMoveForTake(SquareGlobals.SW);
		}
	}
	
	private void checkMoveForTake(int direction)
	{
		SquareInterface suspect = itsLocation.getNeighbor(direction);
		if(suspect.isValid())
			addAttack(suspect);
		Piece occupant = suspect.isValid() ? suspect.getOccupant() : new MockPiece();
		if(occupant.isValid() && ! itsColor.equals(occupant.getColor()) && !occupant.getType().equals(PieceGlobals.KING))
			itsMoveOptions.add(suspect);
	}
		
	public void checkEnpassants()
	{
		SquareInterface westSquare = itsLocation.getNeighbor(SquareGlobals.W);
		Piece westNeighbor = westSquare.isValid() ? westSquare.getOccupant() : new MockPiece();
		if (itsWestEnpassant.isValid() && westNeighbor.isValid() && itsWestEnpassant == westNeighbor)
		{
			if(itsColor.equals(PieceGlobals.WHITE))
				itsMoveOptions.add(itsLocation.getNeighbor(SquareGlobals.NW));
			else
				itsMoveOptions.add(itsLocation.getNeighbor(SquareGlobals.SW));	
		}
		SquareInterface eastSquare = itsLocation.getNeighbor(SquareGlobals.E);
		Piece eastNeighbor = eastSquare.isValid() ? eastSquare.getOccupant() : new MockPiece();
		if (itsEastEnpassant.isValid() && eastNeighbor.isValid() && itsEastEnpassant == eastNeighbor)
		{
			if(itsColor.equals(PieceGlobals.WHITE))
				itsMoveOptions.add(itsLocation.getNeighbor(SquareGlobals.NE));
			else
				itsMoveOptions.add(itsLocation.getNeighbor(SquareGlobals.SE));	
		}
	}
	
	public void reverseMove(AbstractMove move)
	{
		if(move.getType().equals(MoveGlobals.ENPASSANT))
			undoEnpassant(move);
		else
			super.reverseMove(move);	
	}
	
	private void setEnPassants()
	{
		itsWestEnpassant = new MockPiece();
		itsEastEnpassant = new MockPiece();
		
		if (itsColor.equals(PieceGlobals.WHITE) && itsLocation.getPrintableRank() == 5)
		{
			checkWestEnpassantVictim(SquareGlobals.NW, SquareGlobals.N);
			checkEastEnpassantVictim(SquareGlobals.NE, SquareGlobals.N);
		}
		else if(itsLocation.getPrintableRank() == 4)
		{
			checkWestEnpassantVictim(SquareGlobals.SW, SquareGlobals.S);
			checkEastEnpassantVictim(SquareGlobals.SE, SquareGlobals.S);	
		}
	}
	
	private void checkWestEnpassantVictim(int direction1, int direction2)
	{
		SquareInterface square = itsLocation.getNeighbor(direction1);
		if (square.isValid())
		{
			square = square.getNeighbor(direction2);
			Piece victim = square.isValid() ? square.getOccupant() : new MockPiece();
			if(victim.isValid() && victim.getColor().equals(enemyColor()) && victim.getType().equals(PieceGlobals.PAWN))
				itsWestEnpassant = victim;
		}
	}
	
	private void checkEastEnpassantVictim(int direction1, int direction2)
	{
		SquareInterface square = itsLocation.getNeighbor(direction1);
		if (square.isValid())
		{
			square = square.getNeighbor(direction2);
			Piece victim = square.isValid() ? square.getOccupant() : new MockPiece();
			if(victim.isValid() && victim.getColor().equals(enemyColor()) && victim.getType().equals(PieceGlobals.PAWN))
				itsEastEnpassant = victim;
		}
	}
	
	private boolean isEnpassantMove(SquareInterface square)
	{
		boolean returnValue = false;
		if (square.isValid())
		{
			boolean  unOccupied = ! square.getOccupant().isValid();
			int itsFile = itsLocation.getFile();
			int squareFile = square.getFile();
			if(unOccupied && itsFile != squareFile)
				returnValue = true;
		}
		return returnValue;
	}
	
	private AbstractMove performEnpassant(SquareInterface square)
	{
		SquareInterface startSquare = itsLocation;
	
		SquareInterface SW = itsLocation.getNeighbor(SquareGlobals.SW);
		SquareInterface NW = itsLocation.getNeighbor(SquareGlobals.NW);
	
		SquareInterface removedPieceLocation;
		if (square == NW || square == SW)
			removedPieceLocation = itsLocation.getNeighbor(SquareGlobals.W);
		else	
			removedPieceLocation = itsLocation.getNeighbor(SquareGlobals.E);
		Piece removedPiece = removedPieceLocation.getOccupant();
		itsLocation.setOccupant(new MockPiece());
		square.setOccupant(this);
		setLocation(square);
		removedPieceLocation.setOccupant(new MockPiece());
		return new EnpassantMove(this, startSquare, square, removedPiece, removedPieceLocation);
	}
	
	private void undoEnpassant(AbstractMove move)
	{
		EnpassantMove enpassant = (EnpassantMove)move;
		
		enpassant.getStartSquare().setOccupant(this);
		enpassant.getEndSquare().setOccupant(new MockPiece());
		stepBack();
		enpassant.getTakensSquare().setOccupant(enpassant.getTakenPiece());
	}
	
	private boolean isPromotionMove(SquareInterface square)
	{
		boolean retVal = false;
		if(itsColor.equals(PieceGlobals.WHITE))
		{
			if (itsLocation.getPrintableRank() == 7 && square.getPrintableRank() == 8)
				retVal = true;
		}
		else
		{
			if (itsLocation.getPrintableRank() == 2 && square.getPrintableRank() == 1)
				retVal = true;
		}
		return retVal;
	}
	
	private AbstractMove doPromotion(SquareInterface square)
	{
		itsLocation.setOccupant(new MockPiece());
		AbstractMove move = new PromotionMove(this, itsLocation, square, square.getOccupant());
		setLocation(square);
		return move;
	}
}
