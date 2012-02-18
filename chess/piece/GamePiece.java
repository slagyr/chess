package chess.piece;

import chess.board.*;
import chess.move.*;

import java.util.*;

public class GamePiece implements Piece
{
	protected String itsType;
	protected String itsColor;
	protected SquareInterface itsLocation;
	protected HashSet itsMoveOptions;
	protected ArrayList itsTrail;
	protected HashSet itsAttacks;
	protected HashSet itsAttackers;
	protected HashSet itsAttackedPieces;
	
	public GamePiece(String color)
	{
		itsType = "GamePiece";
		itsColor = color;
		itsMoveOptions = new HashSet();
		itsTrail = new ArrayList();
		itsAttacks = new HashSet();
		itsAttackers = new HashSet();
		itsAttackedPieces = new HashSet();
	}

	public boolean isValid()
	{
		return true;
	}
	
	public String getType()
	{
		return itsType;
	}
	
	public String getColor()
	{
		return itsColor;
	}
	
	public void setLocation(SquareInterface square)
	{
		itsLocation = square;
		itsTrail.add(square);
	}
	
	public void stepBack()
	{
		clearAttacks();
		int lastIndex = itsTrail.size() - 1;
		itsTrail.remove(lastIndex);
		if(lastIndex > 0)
			itsLocation = (SquareInterface)itsTrail.get(lastIndex - 1);
		else
		{
			try
			{
				throw new Exception("lost trail");
			}
			catch(Exception e)
			{
				System.err.println(this + " " + itsLocation);
				e.printStackTrace();
			}
		}		
	}
	
	public SquareInterface getLocation()
	{
		return itsLocation;
	}
	
	public void findMoves()
	{
		printError("findMoves()");
	}
	
	public void findMoves_safe()
	{
		findMoves();
	}
	
	public void findMovesOutOfCheck(Piece king)
	{
		findMoves();
		removeNonSavingMoves(king);
	}
	
	public Collection getMoveOptions()
	{
		return itsMoveOptions;
	}
	
	public Collection getAttacks()
	{
		return itsAttacks;
	}
	
	public Collection getTrail()
	{
		return itsTrail;
	}
	
	public String toString()
	{
		return itsColor + " " + itsType;
	}
	
	public boolean canMoveTo(SquareInterface s)
	{
		return (s.isValid() && itsMoveOptions.contains(s));
	}
	
	public AbstractMove makeMove(SquareInterface square)
	{
		itsAttackers.clear();
		SquareInterface startSquare = itsLocation;
		itsLocation.setOccupant(new MockPiece());
		Piece removedPiece = square.getOccupant();
		square.setOccupant(this);
		setLocation(square);
		return new Move(this, startSquare, square, removedPiece);
	}
	
	public void reverseMove(AbstractMove move)
	{
		if (move.isValid())
		{
			move.getStartSquare().setOccupant(this);
			move.getEndSquare().setOccupant(move.getTakenPiece());
			stepBack();
		}
		else
		{
			Exception e = new Exception("Illegal Move being reversed: " + move);
		}
	}
	
	public char getTextType()
	{
		return 'g';
	}
	
	public void addAttack(SquareInterface square)
	{
		itsAttacks.add(square);
		square.addAttacker(this);
		Piece occupant = square.getOccupant();
		if(occupant.isValid())
			itsAttackedPieces.add(occupant);
	}
	
	public void addAttacker(Piece p)
	{
		itsAttackers.add(p);
	}
	
	public void removeAttacker(Piece p)
	{
		itsAttackers.remove(p);
	}
	
	public Collection getAttackers()
	{
		return itsAttackers;
	}
	
	public Collection getAttackers(String color)
	{
		Collection attackers = new HashSet();
		for(Iterator i = itsAttackers.iterator(); i.hasNext();)
		{
			Piece piece = (Piece)i.next();
			if(piece.getColor().equals(color))
				attackers.add(piece);
		}
		return attackers;
	}
	
	public Collection getAttackedPieces()
	{
		return itsAttackedPieces;
	}
	
	public boolean isPieceBlockingCheck(Piece blocker)
	{
		int direction = getDirectionToSquare(blocker.getLocation());
		int blockerDist = -1;
		int kingDist = -1;
		boolean otherBlocker = false;
		if (canMoveInDirection(direction))
		{
			SquareInterface square = itsLocation.getNeighbor(direction);
			for(int i = 0; square.isValid(); i++)
			{
				Piece occupant = square.getOccupant();
				if(occupant.isValid() && occupant == blocker)
					blockerDist = i;
				else if(occupant.isValid() && occupant.getType().equals(PieceGlobals.KING) && occupant.getColor().equals(enemyColor()))
					kingDist = i;	
				else if(occupant.isValid() && kingDist == -1)
					otherBlocker = true;	
				square = square.getNeighbor(direction);
			}	
		}
		if(kingDist != -1 && blockerDist != -1 && !otherBlocker && kingDist > blockerDist)
			return true;
		else
			return false;	
	}
	
	public boolean canMoveInDirection(int direction)
	{
		return false;
	}
	
	public void clearAttacks()
	{
		itsAttackedPieces.clear();
		for(Iterator i = itsAttacks.iterator(); i.hasNext(); )
		{
			SquareInterface s = (SquareInterface)i.next();
			s.removeAttacker(this);
			i.remove();
		}
	}
	
	public boolean isCheck()
	{
		boolean check = false;
		for(Iterator i = itsAttackedPieces.iterator(); i.hasNext(); )
		{
			Piece piece = (Piece)i.next();
			if (piece.getColor().equals(enemyColor()) && piece.getType().equals(PieceGlobals.KING))
			{
				check = true;
				break;
			}
		}
		return check;
	}
	
	public boolean isAttacked()
	{
		return isAttackedBy(enemyColor());
	}
	
	public boolean isProtected()
	{
		return isAttackedBy(itsColor);
	}
	
	private boolean isAttackedBy(String color)
	{
		boolean attacked = false;
		for(Iterator i = itsAttackers.iterator(); i.hasNext(); )
		{
			Piece p = (Piece)i.next();
			if (p.getColor().equals(color))
			{
				attacked = true;
				break;
			}
		}
		return attacked;
	}
	
	private void printError(String method)
	{
		System.err.println("GamePiece." + method);
	}
	
	protected void findMovesInDirection(int direction)
	{
		SquareInterface suspect = itsLocation.getNeighbor(direction);
		while (suspect.isValid())
		{
			if(addMoveBasedOnOccupant(suspect))
				break;
			suspect = suspect.getNeighbor(direction);			
		}
	}
	
	protected boolean addMoveBasedOnOccupant(SquareInterface suspect, boolean attack)
	{
		if (attack && suspect.isValid())
			addAttack(suspect);
		boolean occupied = false;
		Piece occupant = suspect.isValid() ? suspect.getOccupant() : new MockPiece();
		if (occupant.isValid())
		{
			if( ! occupant.getColor().equals(itsColor) && !occupant.getType().equals(PieceGlobals.KING) && attack)
				itsMoveOptions.add(suspect);
			occupied = true;	
		}
		else if(suspect.isValid())
			itsMoveOptions.add(suspect);
		return occupied;	
	}
	
	protected boolean addMoveBasedOnOccupant(SquareInterface suspect)
	{
		return addMoveBasedOnOccupant(suspect, true);
	}
	
	protected String enemyColor()
	{
		return itsColor.equals(PieceGlobals.WHITE) ? PieceGlobals.BLACK : PieceGlobals.WHITE;
	}
	
	protected void removeCheckRevealingMoves()
	{
		for(Iterator a = itsAttackers.iterator(); a.hasNext(); )
		{
			Piece attacker = (Piece)a.next();
			if (!itsColor.equals(attacker.getColor()) && attacker.isPieceBlockingCheck(this))
			{
				for(Iterator i = itsMoveOptions.iterator(); i.hasNext(); )
				{
					SquareInterface s = (SquareInterface)i.next();
					if (attacker.willMoveUnblockCheck(this, s))
						i.remove();
				}
			}
		}
	}
	
	public boolean willMoveUnblockCheck(Piece p, SquareInterface s)
	{
		boolean returnValue = false;
		if (isPieceBlockingCheck(p) && ! (itsLocation == s) && 
			! (getDirectionToSquare(p.getLocation()) == getDirectionToSquare(s)))
			returnValue = true;	
	
		return returnValue;	
	}
	
	public int getDirectionToSquare(SquareInterface s)
	{
		int direction = -1;
		int myRank = itsLocation.getRank();
		int myFile = itsLocation.getFile();
		int itsRank = s.getRank();
		int itsFile = s.getFile();
		if (myRank == itsRank)
		{
			if(myFile < itsFile)
				direction = SquareGlobals.E;
			else
				direction = SquareGlobals.W;	
		}
		else if (myFile == itsFile)
		{
			if(myRank < itsRank)
				direction = SquareGlobals.N;
			else
				direction = SquareGlobals.S;
		}
		else if (myFile < itsFile)
		{
			int fileDist = itsFile - myFile;
			if(itsRank - myRank == fileDist)
				direction = SquareGlobals.NE;
			else if(myRank - itsRank == fileDist)
				direction = SquareGlobals.SE;
		}
		else
		{
			int fileDist = myFile - itsFile;
			if(itsRank - myRank == fileDist)
				direction = SquareGlobals.NW;
			else if(myRank - itsRank == fileDist)
				direction = SquareGlobals.SW;
		}
		return direction;
	}
	
	private void removeNonSavingMoves(Piece king)
	{
		Collection attackers = king.getAttackers(enemyColor());
		if(attackers.size() > 1 && this != king)
			itsMoveOptions.clear();
		else
		{
			Iterator a = attackers.iterator();
			if (a.hasNext())
			{
				Piece attacker = (Piece)a.next();
				for(Iterator i = itsMoveOptions.iterator(); i.hasNext(); )
				{
					SquareInterface square = (SquareInterface)i.next();
					Piece taken = makeFakeMove(square);
					if (taken != attacker)
					{
						attacker.findMoves();
						if(attacker.isCheck())
							i.remove();
					}
					undoFakeMove(taken, square);
					attacker.findMoves();
				}
			}
		}
	}
	
	private Piece makeFakeMove(SquareInterface square)
	{
		Piece taken = square.getOccupant();
		square.setOccupant(this);
		itsLocation.setOccupant(new MockPiece());
		return taken;
	}
	
	private void undoFakeMove(Piece taken, SquareInterface square)
	{
		square.setOccupant(taken);
		itsLocation.setOccupant(this);
	}
	
}
