package chess.move;

import junit.framework.*;
import chess.piece.*;
import chess.board.*;
import chess.move.*;

public class CastleMoveTest extends TestCase
{
	public CastleMoveTest(String name)
	{
		super(name);
	}
	
	public void testConstructor()
	{
		Piece p1 = new MockPiece();
		SquareInterface s1 = new EmptySquare("MoveTest");
		SquareInterface s2 = new EmptySquare("MoveTest");
		AbstractMove move = new KingSideCastleMove(p1, s1, s2);
		assertEquals(MoveGlobals.KING_SIDE_CASTLE, move.getType());
		assertSame(p1, move.getPiece());
		assertSame(s1, move.getStartSquare());
		assertSame(s2, move.getEndSquare());
	}
	
	public void testToString()
	{
		Piece p1 = new MockPiece();
		SquareInterface s1 = new EmptySquare("MoveTest");
		SquareInterface s2 = new EmptySquare("MoveTest");
		AbstractMove move1 = new KingSideCastleMove(p1, s1, s2);
		AbstractMove move2 = new QueenSideCastleMove(p1, s1, s2);
		assertEquals(MoveGlobals.QUEEN_SIDE_CASTLE, move2.getType());
		assertEquals("0-0", move1.toString());
		assertEquals("0-0-0", move2.toString());
	}
}
