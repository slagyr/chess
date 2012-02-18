package chess.piece;

import chess.board.*;
import chess.move.*;

import java.util.*;

public class MockPiece implements Piece
{
	public boolean isValid()
	{
		return false;
	}
	
	public String getType()
	{
		printError("getType()");
		return "none";
	}
	
	public String getColor()
	{
		printError("getColor()");
		return "none";
	}
	
	public void setLocation(SquareInterface s)
	{
		printError("setLocation(" + s + ")");
	}
	
	public SquareInterface getLocation()
	{
		printError("getLocation()");
		return new EmptySquare("MockPiece");
	}	
	
	public void findMoves()
	{
		printError("findMoves()");
	}	
	
	public void findMoves_safe()
	{
		printError("findMoves_safe()");
	}
	
	public void findMovesOutOfCheck(Piece king)
	{
		printError("findMovesOutOfCheck(" + king + ")");
	}

	public Collection getMoveOptions()
	{
		printError("getMoveOptions()");
		return new HashSet();
	}
	
	public Collection getAttacks()
	{
		printError("getAttacks()");
		return new HashSet();
	}

	public Collection getTrail()
	{
		printError("getTrail()");
		return new ArrayList();
	}
	
	public String toString()
	{
		return "MockPiece";
	}
	
	public boolean canMoveTo(SquareInterface s)
	{
		printError("canMoveTo(" + s + ")");
		return false;
	}
	
	public AbstractMove makeMove(SquareInterface s)
	{
		printError("makeMove(" + s + ")");
		return new IllegalMove();
	}
	
	public char getTextType()
	{
		return ' ';
	}
	
	public void addAttack(SquareInterface square)
	{
		printError("addAttack(" + square + ")");
	}
	
	public void addAttacker(Piece p)
	{
		printError("addAttacker(" + p + ")");
	}
	
	public void removeAttacker(Piece p)
	{
		printError("removeAttacker(" + p + ")");
	}
	
	public Collection getAttackers()
	{
		printError("getAttackers()");
		return new HashSet();
	}
	
	public Collection getAttackers(String color)
	{
		printError("getAttackers(" + color + ")");
		return new HashSet();
	}
	
	public boolean isAttacked()
	{
		printError("isAttacked()");
Exception e = new Exception("MockPiece.isAttacked()");
e.printStackTrace();
		return false;
	}
	
	public boolean isProtected()
	{
		printError("isProtected()");
		return false;
	}
	
	public Collection getAttackedPieces()
	{
		printError("getAttackedPieces()");
		return new HashSet();
	}
	
	public void clearAttacks()
	{
		printError("clearAttacks()");
	}
	
	public int getDirectionToSquare(SquareInterface s)
	{
		printError("getDirectionToSquare(" + s + ")");
		return -1;
	}
	
	public boolean isPieceBlockingCheck(Piece p)
	{
		printError("isPieceBlockingCheck(" + p + ")");
		return false;
	}
	
	public boolean canMoveInDirection(int direction)
	{
		printError("canMoveInDirection(" + direction + ")");
		return false;
	}
	
	public boolean willMoveUnblockCheck(Piece p, SquareInterface s)
	{
		printError("willMoveUnblockCheck(" + p + ", " + s + ")");
		return false;
	}
	
	public boolean isCheck()
	{
		printError("isCheck()");
		return false;
	}
	
	public void reverseMove(AbstractMove move)
	{
		printError("reverseMove(" + move +")");
	}
	
	public void stepBack()
	{
		printError("stepBack()");
	}
	
	private void printError(String method)
	{
		System.err.println("MockPiece." + method);
	}
}