package chess.move;

import junit.framework.*;
import chess.piece.*;
import chess.board.*;
import chess.move.*;

public class PromotionMoveTest extends TestCase
{
	public PromotionMoveTest(String name)
	{
		super(name);
	}
	
	public void testConstructor()
	{
		Piece p1 = new MockPiece();
		Piece p2 = new MockPiece();
		SquareInterface s1 = new EmptySquare("MoveTest");
		SquareInterface s2 = new EmptySquare("MoveTest");
		AbstractMove move = new PromotionMove(p1, s1, s2, p2);
		assertEquals(MoveGlobals.PROMOTION, move.getType());
		assertSame(p1, move.getPiece());
		assertSame(p2, move.getTakenPiece());
		assertSame(s1, move.getStartSquare());
		assertSame(s2, move.getEndSquare());
		move = new PromotionMove(p1, s1, s2);
		assertSame(p1, move.getPiece());
		assertSame(s1, move.getStartSquare());
		assertSame(s2, move.getEndSquare());
		assertTrue(! move.endOfGame());
	}
	
	public void testGetSetPromotion()
	{
		Piece p1 = new PieceFactory().createPiece(PieceGlobals.BLACK, PieceGlobals.PAWN);
		Piece p2 = new PieceFactory().createPiece(PieceGlobals.WHITE, PieceGlobals.PAWN);
		Piece p3 = new PieceFactory().createPiece(PieceGlobals.BLACK, PieceGlobals.QUEEN);
		SquareInterface s1 = new Square(SquareGlobals.a, 0);
		SquareInterface s2 = new Square(SquareGlobals.h, 7);
		PromotionMove move = new PromotionMove(p1, s1, s2, p2);
		move.setPromotion(p3);
		assertSame(p3, move.getPromotion());
	}
	
	public void testToString()
	{
		Piece p1 = new PieceFactory().createPiece(PieceGlobals.BLACK, PieceGlobals.PAWN);
		Piece p2 = new PieceFactory().createPiece(PieceGlobals.WHITE, PieceGlobals.PAWN);
		Piece p3 = new PieceFactory().createPiece(PieceGlobals.BLACK, PieceGlobals.QUEEN);
		SquareInterface s1 = new Square(SquareGlobals.a, 0);
		SquareInterface s2 = new Square(SquareGlobals.h, 7);
		PromotionMove move = new PromotionMove(p1, s1, s2, p2);
		move.setPromotion(p3);
		assertEquals("a1xh8(q)", move.toString());
		p2 = new MockPiece();
		move = new PromotionMove(p1, s1, s2, p2);
		move.setPromotion(p3);
		assertEquals("a1 h8(q)", move.toString());
	}
}
