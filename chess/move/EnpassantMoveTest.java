package chess.move;

import junit.framework.*;
import chess.piece.*;
import chess.board.*;
import chess.move.*;

public class EnpassantMoveTest extends TestCase
{
	public EnpassantMoveTest(String name)
	{
		super(name);
	}
	
	public void testConstructor()
	{
		Piece p1 = new MockPiece();
		Piece p2 = new MockPiece();
		SquareInterface s1 = new EmptySquare("MoveTest");
		SquareInterface s2 = new EmptySquare("MoveTest");
		SquareInterface s3 = new EmptySquare("MoveTest");
		EnpassantMove move = new EnpassantMove(p1, s1, s2, p2, s3);
		assertEquals(MoveGlobals.ENPASSANT, move.getType());
		assertSame(p1, move.getPiece());
		assertSame(p2, move.getTakenPiece());
		assertSame(s1, move.getStartSquare());
		assertSame(s2, move.getEndSquare());
		assertSame(s3, move.getTakensSquare());
	}
	
	public void testToString()
	{
		Piece p1 = new PieceFactory().createPiece(PieceGlobals.BLACK, PieceGlobals.PAWN);
		Piece p2 = new PieceFactory().createPiece(PieceGlobals.WHITE, PieceGlobals.PAWN);
		SquareInterface s1 = new Square(SquareGlobals.a, 0);
		SquareInterface s2 = new Square(SquareGlobals.h, 7);
		SquareInterface s3 = new Square(SquareGlobals.h, 6);
		AbstractMove move = new EnpassantMove(p1, s1, s2, p2, s3);
		assertEquals("a1xh8", move.toString());
	}
}
