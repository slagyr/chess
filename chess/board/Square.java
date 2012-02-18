package chess.board;

import chess.piece.*;

import java.util.*;

public class Square implements SquareInterface
{
	private static char[] files;
	
	static
	{
		files = new char[8];
		files[0] = 'a'; 
		files[1] = 'b'; 
		files[2] = 'c'; 
		files[3] = 'd'; 
		files[4] = 'e'; 
		files[5] = 'f'; 
		files[6] = 'g'; 
		files[7] = 'h'; 
	}
	
	private int itsFile;
	private int itsRank;
	private Piece itsOccupant;
	private SquareInterface[] itsNeighbors;
	private HashSet itsAttackers;

	public Square(int file, int rank)
	{
		itsFile = file;
		itsRank = rank;
		itsOccupant = new MockPiece();
		setDefaultNeighbors();
		itsAttackers = new HashSet();
	}
	
	public int getFile()
	{
		return itsFile;
	}
	
	public int getRank()
	{
		return itsRank;
	}
	
	public char getPrintableFile()
	{
		return files[itsFile];
	}
	
	public int getPrintableRank()
	{
		return itsRank + 1;
	}
	
	public String toString()
	{
		return "" + getPrintableFile() + getPrintableRank();
	}
	
	public void setOccupant(Piece p)
	{
		itsOccupant = p;
	}
	
	public boolean isOccupied()
	{
		return (itsOccupant.isValid());
	}
	
	public Piece getOccupant()
	{
		return itsOccupant;
	}
	
	public Piece removeOccupant()
	{
		Piece o = itsOccupant;
		itsOccupant = null;
		return o;
	}
	
	public Piece swapOccupant(Piece newOccupant)
	{
		Piece oldOccupant = removeOccupant();
		setOccupant(newOccupant);
		return oldOccupant;
	}
	
	public boolean isValid()
	{
		return true;
	}
	
	public void setNeighbors(SquareInterface n, SquareInterface ne, SquareInterface e, SquareInterface se, SquareInterface s, SquareInterface sw, SquareInterface w, SquareInterface nw)
	{
		itsNeighbors[SquareGlobals.N]  = n;
		itsNeighbors[SquareGlobals.NE] = ne;
		itsNeighbors[SquareGlobals.E]  = e;
		itsNeighbors[SquareGlobals.SE] = se;
		itsNeighbors[SquareGlobals.S]  = s;
		itsNeighbors[SquareGlobals.SW] = sw;
		itsNeighbors[SquareGlobals.W]  = w;
		itsNeighbors[SquareGlobals.NW] = nw;
	}
	
	public SquareInterface getNeighbor(int direction)
	{
		return itsNeighbors[direction];
	}
	
	public void addAttacker(Piece p)
	{
		itsAttackers.add(p);
		if(itsOccupant.isValid())
			itsOccupant.addAttacker(p);
	}
	
	public Collection getAttackers()
	{
		return itsAttackers;
	}
	
	public boolean isAttackedBy(String color)
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
	
	public void removeAttacker(Piece p)
	{
		itsAttackers.remove(p);
		if(itsOccupant.isValid())
			itsOccupant.removeAttacker(p);
	}
	
	public boolean equals(Object o)
	{
		return toString().equals(o.toString());
	}
	
	private void setDefaultNeighbors()
	{
		itsNeighbors = new SquareInterface[8];
		EmptySquare e = new EmptySquare(this.toString());
		setNeighbors(e, e, e, e, e, e, e, e);
	}
}
