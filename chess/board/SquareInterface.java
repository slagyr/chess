package chess.board;

import chess.piece.*;
import java.util.Collection;

public interface SquareInterface
{
	public abstract int getFile();	
	public abstract int getRank();	
	public abstract char getPrintableFile();	
	public abstract int getPrintableRank();
	public abstract void setOccupant(Piece p);	
	public abstract boolean isOccupied();
	public abstract Piece getOccupant();	
	public abstract Piece removeOccupant();
	public abstract Piece swapOccupant(Piece newOccupant);
	public abstract void setNeighbors(SquareInterface s1, SquareInterface s2, SquareInterface s3, SquareInterface s4, SquareInterface s5, SquareInterface s6, SquareInterface s7, SquareInterface s8);
	public abstract SquareInterface getNeighbor(int i);
	public abstract boolean isValid();
	public abstract void addAttacker(Piece p);
	public abstract Collection getAttackers();
	public abstract boolean isAttackedBy(String color);
	public abstract void removeAttacker(Piece p);
	public abstract boolean equals(Object o);
}
