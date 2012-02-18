package chess.piece;

import chess.board.SquareInterface;
import chess.move.AbstractMove;

import java.util.Collection;

public interface Piece
{
	public abstract boolean    isValid();
	public abstract String     getType();
	public abstract String     getColor();
	public abstract void       setLocation(SquareInterface s);
	public abstract void       stepBack();
	public abstract SquareInterface getLocation();
	public abstract void       findMoves();
	public abstract void       findMoves_safe();
	public abstract void       findMovesOutOfCheck(Piece king);
	public abstract Collection getMoveOptions();
	public abstract Collection getAttacks();
	public abstract Collection getTrail();
	public abstract boolean    canMoveTo(SquareInterface s);
	public abstract AbstractMove makeMove(SquareInterface s);
	public abstract char       getTextType();
	public abstract void       addAttack(SquareInterface s);
	public abstract void       addAttacker(Piece p);
	public abstract void       removeAttacker(Piece p);
	public abstract Collection getAttackers();
	public abstract Collection getAttackers(String color);
	public abstract boolean    isAttacked();
	public abstract boolean    isProtected();
	public abstract Collection getAttackedPieces();
	public abstract void       clearAttacks();
	public abstract int        getDirectionToSquare(SquareInterface s);
	public abstract boolean    isPieceBlockingCheck(Piece p);
	public abstract boolean    canMoveInDirection(int direction);
	public abstract boolean    willMoveUnblockCheck(Piece p, SquareInterface s);
	public abstract boolean    isCheck();
	public abstract void       reverseMove(AbstractMove move);
}
