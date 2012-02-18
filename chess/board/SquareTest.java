package chess.board;

import junit.framework.*;
import java.util.*;

import chess.board.*;
import chess.piece.*;

public class SquareTest extends TestCase
{
	public SquareTest(String name)
	{
		super(name);
	}
	
	public void testConstructor()
	{
		Square s = new Square(SquareGlobals.a, 1);
		assertEquals(0, s.getFile());
		assertEquals(1, s.getRank());
	}
	
	public void testGetPrintables()
	{
		Square s = new Square(SquareGlobals.a, 1);
		assertEquals('a', s.getPrintableFile());
		assertEquals(2, s.getPrintableRank());
		assertEquals("a2", s.toString());
	}
	
	public void testOccupant()
	{
		Square s = new Square(SquareGlobals.a, 1);
		Piece p = new MockPiece();
		s.setOccupant(p);
		assertTrue("has real piece", !s.isOccupied());
		assertSame(p, s.getOccupant());
		assertSame(p, s.removeOccupant());
		assertNull(s.getOccupant());
		Piece p2 = new MockPiece();
		s.setOccupant(p);
		Piece old = s.swapOccupant(p2);
		assertSame(old, p);
		assertSame(p2, s.getOccupant());
		p = new PieceFactory().createGamePiece();
		s.setOccupant(p);
		assertTrue("missing peice", s.isOccupied());
	}
	
	public void testNeighbors()
	{
		Square s = new Square(SquareGlobals.a, 1);
		Square s2 = new Square(SquareGlobals.b, 1); // East Neighbor
		EmptySquare es = new EmptySquare("a1");
		s.setNeighbors(es, es, s2, es, es, es, es, es);
		assertSame("N", es, s.getNeighbor(SquareGlobals.N));
		assertSame("NE", es, s.getNeighbor(SquareGlobals.NE));
		assertSame("E", s2, s.getNeighbor(SquareGlobals.E));
		assertSame("SE", es, s.getNeighbor(SquareGlobals.SE));
		assertSame("S", es, s.getNeighbor(SquareGlobals.S));
		assertSame("SW", es, s.getNeighbor(SquareGlobals.SW));
		assertSame("W", es, s.getNeighbor(SquareGlobals.W));
		assertSame("NW", es, s.getNeighbor(SquareGlobals.NW));
	}
	
	public void testAttackerFunctions()
	{
		Square s = new Square(SquareGlobals.d, 4);
		assertTrue("unknown attacker", s.getAttackers().size() == 0);
		assertTrue("isAttackedBy(white) should be false", ! s.isAttackedBy(PieceGlobals.WHITE));
		assertTrue("isAttackedBy(black) should be false", ! s.isAttackedBy(PieceGlobals.BLACK));
		PieceFactory factory = new PieceFactory();
		Piece p1 = factory.createPiece(PieceGlobals.WHITE, "default");
		Piece p2 = factory.createPiece(PieceGlobals.BLACK, "default");
		Piece p3 = factory.createPiece(PieceGlobals.BLACK, "default");
		s.setOccupant(p3);
		s.addAttacker(p1);
		s.addAttacker(p2);
		Collection attackers = s.getAttackers();
		assertEquals(2, attackers.size());
		assertTrue("p1, not found", attackers.contains(p1));
		assertTrue("p2, not found", attackers.contains(p2));
		assertTrue("isAttackedBy(white) should be true", s.isAttackedBy(PieceGlobals.WHITE));
		assertTrue("isAttackedBy(black) should be true", s.isAttackedBy(PieceGlobals.BLACK));
		assertEquals(2, p3.getAttackers().size());
		s.removeAttacker(p1);
		attackers = s.getAttackers();
		assertEquals(1, attackers.size());
		assertTrue("p1 was not removed", ! attackers.contains(p1));
		assertTrue("isAttackedBy(white) should be false",  ! s.isAttackedBy(PieceGlobals.WHITE));
		assertTrue("isAttackedBy(black) should be true", s.isAttackedBy(PieceGlobals.BLACK));
		assertEquals(1, p3.getAttackers().size());
	}
}
