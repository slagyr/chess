package chess.move;

import junit.framework.*;
import chess.piece.*;
import chess.move.*;

public class ResignMoveTest extends TestCase
{
	public ResignMoveTest(String name)
	{
		super(name);
	}
	
	public void testConstructor()
	{
		Piece piece = new MockPiece();
		AbstractMove move = new ResignMove(piece);
		assertEquals(MoveGlobals.RESIGN, move.getType());
		assertNotNull(move);
		assertTrue(move.isValid());
		assertTrue(move.endOfGame());
		assertTrue(! move.isCheck());
	}
	
	public void testToString()
	{
		Piece p1 = new PieceFactory().createPiece(PieceGlobals.BLACK, PieceGlobals.KING);
		AbstractMove move = new ResignMove(p1);
		assertEquals("black resigns.", move.toString());
	}
}