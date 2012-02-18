package chess.board;

import chess.piece.*;

import java.util.*;

public class EmptySquare implements SquareInterface
{
	private String itsOwner;

	public EmptySquare(String owner)
	{
		itsOwner = owner;
	}
	
	public int getFile()
	{
		printError("getFile()");
		return -1;
	}
	
	public int getRank()
	{
		printError("getRank()");
		return -1;
	}
	
	public char getPrintableFile()
	{
		printError("getPrintableFile()");
		return 'X';
	}
	
	public int getPrintableRank()
	{
		printError("getPrintableRank()");
		return -1;
	}
	
	public String toString()
	{
		return "empty(" + itsOwner + ")";
	}
	
	public void setOccupant(Piece p)
	{
		printError("setOccupant(" + p + ")");
	}
	
	public boolean isOccupied()
	{
		printError("isOccupied()");
		return false;
	}
	
	public Piece getOccupant()
	{
		printError("getOccupant()");
		return new MockPiece();
	}
	
	public Piece removeOccupant()
	{
		printError("removeOccupant()");
		return new MockPiece();
	}
	
	public Piece swapOccupant(Piece newOccupant)
	{
		printError("swapOccupant(" + newOccupant + ")");
		return new MockPiece();
	}
	
	public boolean isValid()
	{
		return false;
	}
	
	public void setNeighbors(SquareInterface s1, SquareInterface s2, SquareInterface s3, SquareInterface s4, SquareInterface s5, SquareInterface s6, SquareInterface s7, SquareInterface s8)
	{
		printError("setNeighbors()");
	}
	
	public SquareInterface getNeighbor(int i)
	{
		printError("getNeighbor(" + i + ")");
		return new EmptySquare("unknown");
	}
	
	public void addAttacker(Piece p)
	{
		printError("addAttacker(" + p + ")");
	}
	
	public Collection getAttackers()
	{
		printError("getAttackers()");
		return new HashSet();
	}
	
	public boolean isAttackedBy(String color)
	{
		printError("isAttackedBy(" + color + ")");
		return false;
	}
	
	public void removeAttacker(Piece p)
	{
		printError("removeAttacker(" + p + ")");
	}
	
	public boolean equals(Object o)
	{
		return toString().equals(o.toString());
	}
	
	private void printError(String method)
	{
		System.err.println("EmptySquare." + method + ", owner: " + itsOwner);
	}
}