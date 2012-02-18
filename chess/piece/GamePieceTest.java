package chess.piece;

import junit.framework.*;
import java.util.*;

import chess.piece.*;
import chess.board.*;
import chess.move.AbstractMove;

public class GamePieceTest extends TestCase
{
	PieceFactory factory;
	
	public GamePieceTest(String name)
	{
		super(name);
		factory = new PieceFactory();
	}
	
	public void testIsValid()
	{
		Piece g = factory.createGamePiece();
		assertTrue(g.isValid());
	}
	
	public void testGetType()
	{
		Piece g = factory.createGamePiece();
		assertEquals("GamePiece", g.getType());
	}
	
	public void testGetColor()
	{
		Piece g = factory.createPiece(PieceGlobals.BLACK, "default");
		assertEquals("black", g.getColor());
		g = factory.createPiece(PieceGlobals.WHITE, "default");
		assertEquals("white", g.getColor());
	}
											 
	public void testGetAndSetLocation()
	{
		Piece piece = factory.createGamePiece();
		SquareInterface s = new EmptySquare("GamePieceTest");
		piece.setLocation(s);
		assertSame(s, piece.getLocation());
	}
	
	public void testTrail()
	{
		Piece piece = factory.createGamePiece();
		SquareInterface s1 = new EmptySquare("GamePieceTest");
		SquareInterface s2 = new EmptySquare("GamePieceTest");
		piece.setLocation(s1);
		piece.setLocation(s2);
		Collection trail = piece.getTrail();
		assertEquals(2, trail.size());
		assertTrue("Square1", trail.contains(s1));
	 	assertTrue("Square2", trail.contains(s2));
	}
	
	public void testCanMoveTo()
	{
		Piece piece = factory.createGamePiece();
		Collection moves = piece.getMoveOptions();
		SquareInterface s1 = new Square(SquareGlobals.a, 1);
		SquareInterface s2 = new Square(SquareGlobals.a, 2);
		moves.add(s1);
		assertTrue("should be able to move to s1", piece.canMoveTo(s1));
		assertTrue("shouldn't be able to move to s2", ! piece.canMoveTo(s2));
	}
	
	public void testMakeMove()
	{	
		ChessBoard board = new ChessBoard();
		PieceFactory factory = new PieceFactory();
		board.placePiece(SquareGlobals.a, 1, PieceGlobals.BISHOP, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.c, 3, PieceGlobals.PAWN, PieceGlobals.BLACK);
		SquareInterface s1 = board.getSquare(SquareGlobals.a, 1);
		SquareInterface s2 = board.getSquare(SquareGlobals.b, 2);
		SquareInterface s3 = board.getSquare(SquareGlobals.c, 3);
		Piece piece1 = s1.getOccupant();
		Piece piece2 = s3.getOccupant();
		
		Collection moves = piece1.getMoveOptions();
		moves.add(s2);
		AbstractMove m = piece1.makeMove(s2);
		assertTrue("Should be invalid", ! m.getTakenPiece().isValid());
		assertTrue("Square should be unoccupied", ! s1.isOccupied());
		assertTrue("Square should be occupied",  s2.isOccupied());
		assertEquals(s2, piece1.getLocation());
		
		moves.clear();
		moves.add(s3);
		m = piece1.makeMove(s3);
		assertEquals(piece2, m.getTakenPiece());
		assertTrue("Square should be unoccupied", ! s2.isOccupied());
		assertTrue("Square should be occupied",  s3.isOccupied());
		assertEquals(s3, piece1.getLocation());
	}
	
	public void testAddAttack()
	{
		SquareInterface square = new Square(SquareGlobals.a, 1);
		Piece piece = factory.createPiece(PieceGlobals.WHITE, PieceGlobals.BISHOP);
		piece.addAttack(square);
		Collection attacks = piece.getAttacks();
		Collection attackers = square.getAttackers();
		assertEquals(1, attacks.size());
		assertTrue("should have square", attacks.contains(square));
		assertEquals(1, attackers.size());
		assertTrue("should have piece", attackers.contains(piece));
	}
	
	public void testAddRemoveAttacker()
	{
		Piece p1 = factory.createGamePiece();
		Piece p2 = factory.createGamePiece();
		p1.addAttacker(p2);
		Collection attackers = p1.getAttackers();
		assertTrue("Should have attacker", attackers.contains(p2));
		p1.removeAttacker(p2);
		assertEquals(0, attackers.size());
	}
	
	public void testGetDirectionToSquare()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.d, 4, PieceGlobals.QUEEN, PieceGlobals.WHITE);
		Piece queen = board.getSquare(SquareGlobals.d, 4).getOccupant();
		assertEquals("N", SquareGlobals.N, queen.getDirectionToSquare(board.getSquare(SquareGlobals.d, 8)));
		assertEquals("NE", SquareGlobals.NE, queen.getDirectionToSquare(board.getSquare(SquareGlobals.h, 8)));
		assertEquals("E", SquareGlobals.E, queen.getDirectionToSquare(board.getSquare(SquareGlobals.h, 4)));
		assertEquals("SE", SquareGlobals.SE, queen.getDirectionToSquare(board.getSquare(SquareGlobals.g, 1)));
		assertEquals("S", SquareGlobals.S, queen.getDirectionToSquare(board.getSquare(SquareGlobals.d, 1)));
		assertEquals("SW", SquareGlobals.SW, queen.getDirectionToSquare(board.getSquare(SquareGlobals.a, 1)));
		assertEquals("W", SquareGlobals.W, queen.getDirectionToSquare(board.getSquare(SquareGlobals.a, 4)));
		assertEquals("NW", SquareGlobals.NW, queen.getDirectionToSquare(board.getSquare(SquareGlobals.a, 7)));
		assertEquals("-1", -1, queen.getDirectionToSquare(board.getSquare(SquareGlobals.e, 6)));
	}
	
	public void testWillMoveUnblockCheck()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.f, 8, PieceGlobals.KING, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.d, 6, PieceGlobals.BISHOP, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.a, 8, PieceGlobals.ROOK, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.a, 3, PieceGlobals.BISHOP, PieceGlobals.BLACK);
		Piece blocker = board.getSquare(SquareGlobals.d, 6).getOccupant();
		Piece rook = board.getSquare(SquareGlobals.a, 8).getOccupant();
		Piece attacker = board.getSquare(SquareGlobals.a, 3).getOccupant();
		assertTrue("Should still block", ! attacker.willMoveUnblockCheck(blocker, board.getSquare("c5")));
		assertTrue("Should unbloack",  attacker.willMoveUnblockCheck(blocker, board.getSquare("c7")));
		assertTrue("never blocks",  ! attacker.willMoveUnblockCheck(rook, board.getSquare("a7")));
	}
	
	public void testClearAttacks()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.a, 1, PieceGlobals.ROOK, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.h, 1, PieceGlobals.ROOK, PieceGlobals.BLACK);
		SquareInterface a8 = board.getSquare(SquareGlobals.a, 8);
		SquareInterface h1 = board.getSquare(SquareGlobals.h, 1);
		Piece attacker = board.getSquare(SquareGlobals.a, 1).getOccupant();
		Piece atackee = board.getSquare(SquareGlobals.h, 1).getOccupant();
		attacker.findMoves();
		assertTrue("Should be attacked", a8.getAttackers().contains(attacker));
		assertTrue("Should be attacked", h1.getAttackers().contains(attacker));
		assertTrue("Should be attacked", atackee.getAttackers().contains(attacker));
		attacker.clearAttacks();
		assertTrue("Shouldn't be attacked", ! a8.getAttackers().contains(attacker));
		assertTrue("Shouldn't be attacked", ! h1.getAttackers().contains(attacker));
		assertTrue("Shouldn't be attacked", ! atackee.getAttackers().contains(attacker));
	}
	
	public void testIsCheck()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.a, 1, PieceGlobals.ROOK, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.h, 1, PieceGlobals.KING, PieceGlobals.BLACK);
		Piece attacker = board.getSquare(SquareGlobals.a, 1).getOccupant();
		attacker.findMoves();
		assertTrue("Should be check", attacker.isCheck());
		attacker.makeMove(board.getSquare("a2"));
		attacker.findMoves();
		assertTrue("Shouldn't be check", !attacker.isCheck());
	}
	
	public void testFindMovesOutOfCheck()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.d, 1, PieceGlobals.KING, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.h, 2, PieceGlobals.ROOK, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.c, 2, PieceGlobals.PAWN, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.b, 1, PieceGlobals.ROOK, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.d, 8, PieceGlobals.ROOK, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.a, 4, PieceGlobals.BISHOP, PieceGlobals.BLACK);
		Piece king = board.getSquare("d1").getOccupant();
		Piece whiteRook = board.getSquare("h2").getOccupant();
		Piece whiteRook2 = board.getSquare("b1").getOccupant();
		Piece whitePawn = board.getSquare("c2").getOccupant();
		Piece blackRook = board.getSquare("d8").getOccupant();
		Piece blackBishop = board.getSquare("a4").getOccupant();
		blackRook.findMoves();
		blackBishop.findMoves();
		whiteRook2.findMoves();
		king.findMovesOutOfCheck(king);
		whiteRook.findMovesOutOfCheck(king);
		whitePawn.findMovesOutOfCheck(king);
		Collection moves = king.getMoveOptions();
		assertEquals(3, moves.size());
		assertTrue(moves.contains(board.getSquare("c1")));
		assertTrue(moves.contains(board.getSquare("e2")));
		assertTrue(moves.contains(board.getSquare("e1")));
		moves = whiteRook.getMoveOptions();
		assertEquals(1, moves.size());
		assertTrue(moves.contains(board.getSquare("d2")));
		moves = whitePawn.getMoveOptions();
		assertEquals(0, moves.size());
	}
	
	public void testReverseMove()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.d, 1, PieceGlobals.KING, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.d, 2, PieceGlobals.PAWN, PieceGlobals.BLACK);
		SquareInterface d1 = board.getSquare("d1");
		SquareInterface d2 = board.getSquare("d2");
		Piece piece = board.getSquare("d1").getOccupant();
		piece.findMoves();
		AbstractMove move = piece.makeMove(d2);
		Piece taken = move.getTakenPiece();
		piece.reverseMove(move);
		assertSame(piece, d1.getOccupant());
		assertSame(taken, d2.getOccupant());
		assertSame(d1, piece.getLocation());
		assertSame(d2, taken.getLocation());
		assertEquals(1, piece.getTrail().size());
	}
	
	public void testStepBack()
	{
		Piece piece = factory.createGamePiece();
		SquareInterface s1 = new Square(SquareGlobals.a, 1);
		SquareInterface s2 = new Square(SquareGlobals.a, 2);
		piece.setLocation(s1);
		piece.setLocation(s2);
		Collection trail = piece.getTrail();
		assertEquals(2, trail.size());
		piece.stepBack();
		assertSame(piece.getLocation(), s1);
		assertEquals(1, trail.size());
		assertTrue(trail.contains(s1));
	}
}
