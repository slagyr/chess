package chess.move;

import junit.framework.*;
import chess.piece.*;
import chess.board.*;
import chess.move.*;

public class MoveTest extends TestCase
{
	public MoveTest(String name)
	{
		super(name);
	}
	
	public void testConstructor()
	{
		Piece p1 = new MockPiece();
		Piece p2 = new MockPiece();
		SquareInterface s1 = new EmptySquare("MoveTest");
		SquareInterface s2 = new EmptySquare("MoveTest");
		AbstractMove move = new Move(p1, s1, s2, p2);
		assertEquals(MoveGlobals.MOVE, move.getType());
		assertSame(p1, move.getPiece());
		assertSame(p2, move.getTakenPiece());
		assertSame(s1, move.getStartSquare());
		assertSame(s2, move.getEndSquare());
		move = new Move(p1, s1, s2);
		assertSame(p1, move.getPiece());
		assertSame(s1, move.getStartSquare());
		assertSame(s2, move.getEndSquare());
		assertTrue(! move.endOfGame());
	}
	
	public void testCheck()
	{
		Piece p1 = new PieceFactory().createPiece(PieceGlobals.BLACK, PieceGlobals.PAWN);
		Piece p2 = new PieceFactory().createPiece(PieceGlobals.WHITE, PieceGlobals.PAWN);
		SquareInterface s1 = new Square(SquareGlobals.a, 0);
		SquareInterface s2 = new Square(SquareGlobals.h, 7);
		AbstractMove move = new Move(p1, s1, s2, p2);
		assertTrue(! move.isCheck());
		move.check();
		assertTrue(move.isCheck());
		assertEquals("a1xh8+", move.toString());
	}
	
	public void testToString()
	{
		Piece p1 = new PieceFactory().createPiece(PieceGlobals.BLACK, PieceGlobals.PAWN);
		Piece p2 = new PieceFactory().createPiece(PieceGlobals.WHITE, PieceGlobals.PAWN);
		SquareInterface s1 = new Square(SquareGlobals.a, 0);
		SquareInterface s2 = new Square(SquareGlobals.h, 7);
		AbstractMove move = new Move(p1, s1, s2, p2);
		assertEquals("a1xh8", move.toString());
		p2 = new MockPiece();
		move = new Move(p1, s1, s2, p2);
		assertEquals("a1 h8", move.toString());
	}
	
	public void testGetSetScore()
	{
		Piece p1 = new PieceFactory().createPiece(PieceGlobals.BLACK, PieceGlobals.PAWN);
		Piece p2 = new PieceFactory().createPiece(PieceGlobals.WHITE, PieceGlobals.PAWN);
		SquareInterface s1 = new Square(SquareGlobals.a, 0);
		SquareInterface s2 = new Square(SquareGlobals.h, 7);
		AbstractMove move = new Move(p1, s1, s2, p2);
		assertEquals(-1, move.getScore());
		move.setScore(550);
		assertEquals(550, move.getScore());
	}
}
