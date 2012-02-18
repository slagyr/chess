package chess.piece;

import junit.framework.*;
import java.util.*;

import chess.piece.*;
import chess.board.*;
import chess.move.AbstractMove;
import chess.player.Player;

public class KingTest extends TestCase
{
	PieceFactory factory;

	public KingTest(String name)
	{
		super(name);
		factory = new PieceFactory();
	}
	
	public void testGetType()
	{
		Piece piece = factory.createPiece(PieceGlobals.KING);
		assertEquals("King", piece.getType());
	}
	
	public void testFindMoves_Empty_AgainWall()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.a, 3, PieceGlobals.KING, PieceGlobals.WHITE);
		Piece piece = board.getSquare(SquareGlobals.a, 3).getOccupant();
		piece.findMoves();
		Collection moves = piece.getMoveOptions();
		assertEquals(5, moves.size());
		assertTrue(moves.contains(board.getSquare(SquareGlobals.a, 2)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.a, 4)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.b, 2)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.b, 3)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.b, 4)));
	}
	
	public void testFindMoves_Obsticles()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.b, 2, PieceGlobals.KING, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.c, 1, PieceGlobals.BISHOP, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.c, 2, PieceGlobals.BISHOP, PieceGlobals.BLACK);
		Piece piece = board.getSquare(SquareGlobals.b, 2).getOccupant();
		piece.findMoves();
		Collection moves = piece.getMoveOptions();
		assertEquals(7, moves.size());
		assertTrue(moves.contains(board.getSquare(SquareGlobals.a, 1)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.a, 2)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.a, 3)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.b, 1)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.b, 3)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.c, 3)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.c, 2)));
	}
	
	public void testFindMoves_AttackedSquares()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.a, 1, PieceGlobals.KING, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.c, 2, PieceGlobals.ROOK, PieceGlobals.BLACK);
		Piece attacker = board.getSquare(SquareGlobals.c, 2).getOccupant();
		board.getSquare(SquareGlobals.b, 2).addAttacker(attacker);
		board.getSquare(SquareGlobals.a, 2).addAttacker(attacker);
		Piece piece = board.getSquare(SquareGlobals.a, 1).getOccupant();
		piece.findMoves();
		Collection moves = piece.getMoveOptions();
		assertEquals(1, moves.size());
		assertTrue(moves.contains(board.getSquare(SquareGlobals.b, 1)));
	}
	
	public void testFindMoves_Castle_White()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.e, 1, PieceGlobals.KING, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.a, 1, PieceGlobals.ROOK, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.h, 1, PieceGlobals.ROOK, PieceGlobals.WHITE);
		Piece piece = board.getSquare(SquareGlobals.e, 1).getOccupant();
		piece.findMoves();
		Collection moves = piece.getMoveOptions();
		assertEquals(7, moves.size());
		assertTrue(moves.contains(board.getSquare(SquareGlobals.c, 1)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.d, 1)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.d, 2)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.e, 2)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.f, 1)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.f, 2)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.g, 1)));
	} 
	
	public void testFindMoves_BlockCastle_White()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.e, 1, PieceGlobals.KING, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.a, 1, PieceGlobals.ROOK, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.d, 1, PieceGlobals.QUEEN, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.h, 1, PieceGlobals.ROOK, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.g, 2, PieceGlobals.ROOK, PieceGlobals.BLACK);
		Piece attacker = board.getSquare(SquareGlobals.g, 2).getOccupant();
		board.getSquare(SquareGlobals.g, 1).addAttacker(attacker);
		Piece piece = board.getSquare(SquareGlobals.e, 1).getOccupant();
		piece.findMoves();
		Collection moves = piece.getMoveOptions();
		assertEquals(4, moves.size());
		assertTrue(moves.contains(board.getSquare(SquareGlobals.d, 2)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.e, 2)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.f, 2)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.f, 1)));
	} 
	
	public void testFindMoves_Castle_Black()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.e, 8, PieceGlobals.KING, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.a, 8, PieceGlobals.ROOK, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.h, 8, PieceGlobals.ROOK, PieceGlobals.BLACK);
		Piece piece = board.getSquare(SquareGlobals.e, 8).getOccupant();
		piece.findMoves();
		Collection moves = piece.getMoveOptions();
		assertEquals(7, moves.size());
		assertTrue(moves.contains(board.getSquare(SquareGlobals.c, 8)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.d, 8)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.d, 7)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.e, 7)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.f, 8)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.f, 7)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.g, 8)));
	} 
	
	public void testFindMoves_BlockedCastle_Black()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.e, 8, PieceGlobals.KING, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.a, 8, PieceGlobals.ROOK, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.d, 8, PieceGlobals.QUEEN, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.h, 8, PieceGlobals.ROOK, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.g, 7, PieceGlobals.ROOK, PieceGlobals.WHITE);
		Piece attacker = board.getSquare(SquareGlobals.g, 7).getOccupant();
		board.getSquare(SquareGlobals.g, 8).addAttacker(attacker);
		Piece piece = board.getSquare(SquareGlobals.e, 8).getOccupant();
		piece.findMoves();
		Collection moves = piece.getMoveOptions();
		assertEquals(4, moves.size());
		assertTrue(moves.contains(board.getSquare(SquareGlobals.d, 7)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.e, 7)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.f, 7)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.f, 8)));
	}
	
	// test makeMove for castle
	public void testMakeMove_White()
	{	
		ChessBoard board = new ChessBoard();
		PieceFactory factory = new PieceFactory();
		board.placePiece(SquareGlobals.e, 1, PieceGlobals.KING, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.a, 1, PieceGlobals.ROOK, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.h, 1, PieceGlobals.ROOK, PieceGlobals.WHITE);
		SquareInterface a1 = board.getSquare(SquareGlobals.a, 1);
		SquareInterface b1 = board.getSquare(SquareGlobals.b, 1);
		SquareInterface c1 = board.getSquare(SquareGlobals.c, 1);
		SquareInterface d1 = board.getSquare(SquareGlobals.d, 1);
		SquareInterface e1 = board.getSquare(SquareGlobals.e, 1);
		SquareInterface f1 = board.getSquare(SquareGlobals.f, 1);
		SquareInterface g1 = board.getSquare(SquareGlobals.g, 1);
		SquareInterface h1 = board.getSquare(SquareGlobals.h, 1);
		Piece king = e1.getOccupant();
		Piece kingsRook = h1.getOccupant();
		Piece queensRook = a1.getOccupant();
		
		Collection moves = king.getMoveOptions();
		moves.add(g1);
		AbstractMove m = king.makeMove(g1);
		assertTrue("Should be invalid", ! m.getTakenPiece().isValid());
		assertTrue("Square should be unoccupied", ! e1.isOccupied());
		assertTrue("Square should be unoccupied", ! h1.isOccupied());
		assertTrue("Square should be occupied by King",  g1.isOccupied());
		assertTrue("Square should be occupied by Rook",  f1.isOccupied());
		assertEquals(g1, king.getLocation());
		assertEquals(f1, kingsRook.getLocation());
		
		moves.clear();
		g1.setOccupant(new MockPiece());
		e1.setOccupant(king);
		king.setLocation(e1);
		moves.add(c1);
		m = king.makeMove(c1);
		assertTrue("Should be invalid", ! m.getTakenPiece().isValid());
		assertTrue("Square should be unoccupied", ! e1.isOccupied());
		assertTrue("Square should be unoccupied", ! a1.isOccupied());
		assertTrue("Square should be occupied by King",  c1.isOccupied());
		assertTrue("Square should be occupied by Rook",  d1.isOccupied());
		assertEquals(c1, king.getLocation());
		assertEquals(d1, queensRook.getLocation());
		
		// make sure regular move works
		moves.clear();
		moves.add(b1);
		m = king.makeMove(b1);
		assertTrue("Should be invalid", ! m.getTakenPiece().isValid());
		assertTrue("Square should be unoccupied", ! c1.isOccupied());
		assertTrue("Square should be occupied by King",  b1.isOccupied());
		assertEquals(b1, king.getLocation());
	}
	
	public void testMakeMove_Black()
	{	
		ChessBoard board = new ChessBoard();
		PieceFactory factory = new PieceFactory();
		board.placePiece(SquareGlobals.e, 8, PieceGlobals.KING, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.a, 8, PieceGlobals.ROOK, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.h, 8, PieceGlobals.ROOK, PieceGlobals.BLACK);
		SquareInterface a8 = board.getSquare(SquareGlobals.a, 8);
		SquareInterface b8 = board.getSquare(SquareGlobals.b, 8);
		SquareInterface c8 = board.getSquare(SquareGlobals.c, 8);
		SquareInterface d8 = board.getSquare(SquareGlobals.d, 8);
		SquareInterface e8 = board.getSquare(SquareGlobals.e, 8);
		SquareInterface f8 = board.getSquare(SquareGlobals.f, 8);
		SquareInterface g8 = board.getSquare(SquareGlobals.g, 8);
		SquareInterface h8 = board.getSquare(SquareGlobals.h, 8);
		Piece king = e8.getOccupant();
		Piece kingsRook = h8.getOccupant();
		Piece queensRook = a8.getOccupant();
		
		Collection moves = king.getMoveOptions();
		moves.add(g8);
		AbstractMove m = king.makeMove(g8);
		assertTrue("Should be invalid", ! m.getTakenPiece().isValid());
		assertTrue("Square should be unoccupied", ! e8.isOccupied());
		assertTrue("Square should be unoccupied", ! h8.isOccupied());
		assertTrue("Square should be occupied by King",  g8.isOccupied());
		assertTrue("Square should be occupied by Rook",  f8.isOccupied());
		assertEquals(g8, king.getLocation());
		assertEquals(f8, kingsRook.getLocation());
		
		moves.clear();
		g8.setOccupant(new MockPiece());
		e8.setOccupant(king);
		king.setLocation(e8);
		moves.add(c8);
		m = king.makeMove(c8);
		assertTrue("Should be invalid", ! m.getTakenPiece().isValid());
		assertTrue("Square should be unoccupied", ! e8.isOccupied());
		assertTrue("Square should be unoccupied", ! a8.isOccupied());
		assertTrue("Square should be occupied by King",  c8.isOccupied());
		assertTrue("Square should be occupied by Rook",  d8.isOccupied());
		assertEquals(c8, king.getLocation());
		assertEquals(d8, queensRook.getLocation());
		
		// make sure regular move works
		moves.clear();
		moves.add(b8);
		m = king.makeMove(b8);
		assertTrue("Should be invalid", ! m.getTakenPiece().isValid());
		assertTrue("Square should be unoccupied", ! c8.isOccupied());
		assertTrue("Square should be occupied by King",  b8.isOccupied());
		assertEquals(b8, king.getLocation());
	}
	
	public void testGetTextType()
	{
		Piece p1 = factory.createPiece(PieceGlobals.WHITE, PieceGlobals.KING);
		Piece p2 = factory.createPiece(PieceGlobals.BLACK, PieceGlobals.KING);
		assertEquals('K', p1.getTextType());
		assertEquals('k', p2.getTextType());
	}
	
	public void testAttackedSqaure()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.e, 1, PieceGlobals.KING, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.a, 1, PieceGlobals.ROOK, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.h, 1, PieceGlobals.ROOK, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.e, 2, PieceGlobals.ROOK, PieceGlobals.BLACK);
		Piece king = board.getSquare(SquareGlobals.e, 1).getOccupant();
		king.findMoves();
		Collection attacks = king.getAttacks();
		Collection attackedPieces = king.getAttackedPieces();
		assertEquals(5, attacks.size());
		assertTrue(attacks.contains(board.getSquare(SquareGlobals.d, 1)));
		assertTrue(attacks.contains(board.getSquare(SquareGlobals.d, 2)));
		assertTrue(attacks.contains(board.getSquare(SquareGlobals.e, 2)));
		assertTrue(attacks.contains(board.getSquare(SquareGlobals.f, 1)));
		assertTrue(attacks.contains(board.getSquare(SquareGlobals.f, 2)));
		assertTrue(attackedPieces.contains(board.getSquare(SquareGlobals.e, 2).getOccupant()));
	}
	
	public void testIsPieceBlockingCheck()
	{
		Piece king = factory.createPiece(PieceGlobals.WHITE, PieceGlobals.KING);
		assertTrue("Shouldn't be blocking", ! king.isPieceBlockingCheck(new MockPiece()));
	}
	
	public void testReverseMove_White()
	{
		ChessBoard board = new ChessBoard();
		PieceFactory factory = new PieceFactory();
		board.placePiece(SquareGlobals.e, 1, PieceGlobals.KING, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.a, 1, PieceGlobals.ROOK, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.h, 1, PieceGlobals.ROOK, PieceGlobals.WHITE);
		SquareInterface a1 = board.getSquare(SquareGlobals.a, 1);
		SquareInterface b1 = board.getSquare(SquareGlobals.b, 1);
		SquareInterface c1 = board.getSquare(SquareGlobals.c, 1);
		SquareInterface d1 = board.getSquare(SquareGlobals.d, 1);
		SquareInterface e1 = board.getSquare(SquareGlobals.e, 1);
		SquareInterface f1 = board.getSquare(SquareGlobals.f, 1);
		SquareInterface g1 = board.getSquare(SquareGlobals.g, 1);
		SquareInterface h1 = board.getSquare(SquareGlobals.h, 1);
		Piece king = e1.getOccupant();
		Piece kingsRook = h1.getOccupant();
		Piece queensRook = a1.getOccupant();
		
		Collection moves = king.getMoveOptions();
		moves.add(g1);
		AbstractMove move = king.makeMove(g1);
		king.reverseMove(move);
		assertSame(king, e1.getOccupant());
		assertSame(kingsRook, h1.getOccupant());
		assertSame(e1, king.getLocation());
		assertSame(h1, kingsRook.getLocation());
		assertEquals(1, king.getTrail().size());
		assertEquals(1, kingsRook.getTrail().size());
		
		moves.add(c1);
		move = king.makeMove(c1);
		king.reverseMove(move);
		assertSame(king, e1.getOccupant());
		assertSame(kingsRook, h1.getOccupant());
		assertSame(e1, king.getLocation());
		assertSame(a1, queensRook.getLocation());
		assertEquals(1, king.getTrail().size());
		assertEquals(1, queensRook.getTrail().size());
	}
	
	public void testReverseMove_Black()
	{
		ChessBoard board = new ChessBoard();
		PieceFactory factory = new PieceFactory();
		board.placePiece(SquareGlobals.e, 8, PieceGlobals.KING, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.a, 8, PieceGlobals.ROOK, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.h, 8, PieceGlobals.ROOK, PieceGlobals.WHITE);
		SquareInterface a8 = board.getSquare(SquareGlobals.a, 8);
		SquareInterface b8 = board.getSquare(SquareGlobals.b, 8);
		SquareInterface c8 = board.getSquare(SquareGlobals.c, 8);
		SquareInterface d8 = board.getSquare(SquareGlobals.d, 8);
		SquareInterface e8 = board.getSquare(SquareGlobals.e, 8);
		SquareInterface f8 = board.getSquare(SquareGlobals.f, 8);
		SquareInterface g8 = board.getSquare(SquareGlobals.g, 8);
		SquareInterface h8 = board.getSquare(SquareGlobals.h, 8);
		Piece king = e8.getOccupant();
		Piece kingsRook = h8.getOccupant();
		Piece queensRook = a8.getOccupant();
		
		Collection moves = king.getMoveOptions();
		moves.add(g8);
		AbstractMove move = king.makeMove(g8);
		king.reverseMove(move);
		assertSame(king, e8.getOccupant());
		assertSame(kingsRook, h8.getOccupant());
		assertSame(e8, king.getLocation());
		assertSame(h8, kingsRook.getLocation());
		assertEquals(1, king.getTrail().size());
		assertEquals(1, kingsRook.getTrail().size());
		
		moves.add(c8);
		move = king.makeMove(c8);
		king.reverseMove(move);
		assertSame(king, e8.getOccupant());
		assertSame(kingsRook, h8.getOccupant());
		assertSame(e8, king.getLocation());
		assertSame(a8, queensRook.getLocation());
		assertEquals(1, king.getTrail().size());
		assertEquals(1, queensRook.getTrail().size());
	}
	
	public void testFindMove_CantMoveIntoOpponentsKingsAttack()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.a, 7, PieceGlobals.KING, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.d, 1, PieceGlobals.ROOK, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.e, 4, PieceGlobals.ROOK, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.g, 7, PieceGlobals.QUEEN, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.d, 8, PieceGlobals.KNIGHT, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.c, 7, PieceGlobals.KNIGHT, PieceGlobals.WHITE);
		
		board.placePiece(SquareGlobals.c, 8, PieceGlobals.KING, PieceGlobals.BLACK);
		
		Piece blackKing = board.getSquare("c8").getOccupant();
		
		Player black = new Player(PieceGlobals.BLACK);
		Player white = new Player(PieceGlobals.WHITE);
		black.setBoard(board);
		white.setBoard(board);
		black.loadPieces();
		white.loadPieces();
		white.findAllMoves(false);
		black.findAllMoves(false);
		Collection blacksMoves = blackKing.getMoveOptions();
		assertEquals(0, blacksMoves.size());
	}
	
	public void testFindMove_CantQueenSideCastleThroughKnight()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.e, 1, PieceGlobals.KING, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.a, 1, PieceGlobals.ROOK, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.b, 1, PieceGlobals.KNIGHT, PieceGlobals.WHITE);
		
		Piece king = board.getSquare("e1").getOccupant();
		SquareInterface c1 = board.getSquare("c1");
		
		king.findMoves();
		Collection moves = king.getMoveOptions();
System.err.println(moves);
		
		assertEquals(5, moves.size());
		assertTrue(! moves.contains(c1));
	}
}
