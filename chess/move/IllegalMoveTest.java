package chess.move;

import junit.framework.*;
import chess.piece.*;
import chess.board.*;
import chess.move.*;

public class IllegalMoveTest extends TestCase
{
	public IllegalMoveTest(String name)
	{
		super(name);
	}
	
	public void testConstructor()
	{
		Piece piece = new MockPiece();
		SquareInterface start = new EmptySquare("IllegalMoveTest");
		SquareInterface end = new EmptySquare("IllegalMoveTest");
		AbstractMove move = new IllegalMove(piece, start, end);
		assertEquals(MoveGlobals.ILLEGAL, move.getType());
		assertNotNull(move);
		assertTrue(! move.isValid());
		move = new IllegalMove();
		assertNotNull(move);
		assertTrue(! move.isValid());
		assertTrue(! move.endOfGame());
		assertTrue(! move.isCheck());
	}
	
	public void testToString()
	{
		Piece p1 = new PieceFactory().createPiece(PieceGlobals.BLACK, PieceGlobals.PAWN);
		SquareInterface s1 = new Square(SquareGlobals.a, 0);
		SquareInterface s2 = new Square(SquareGlobals.h, 7);
		AbstractMove move = new IllegalMove(p1, s1, s2);
		assertEquals("Illegal move: a1 h8", move.toString());
	}
}
