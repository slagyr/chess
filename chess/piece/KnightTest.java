package chess.piece;

import junit.framework.*;
import java.util.*;

import chess.piece.*;
import chess.board.*;

public class KnightTest extends TestCase
{
	PieceFactory factory;

	public KnightTest(String name)
	{
		super(name);
		factory = new PieceFactory();
	}
	
	public void testGetType()
	{
		Piece piece = factory.createPiece(PieceGlobals.KNIGHT);
		assertEquals("Knight", piece.getType());
	}
	
	public void testFindMoves_OpenBoard()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.d, 4, PieceGlobals.KNIGHT, PieceGlobals.WHITE);
		Piece piece = board.getSquare(SquareGlobals.d, 4).getOccupant();
		piece.findMoves();
		Collection moves = piece.getMoveOptions();
		assertEquals(8, moves.size());
		assertTrue(moves.contains(board.getSquare(SquareGlobals.e, 6)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.f, 5)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.f, 3)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.e, 2)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.c, 2)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.b, 3)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.b, 5)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.c, 6)));
	}
	
	public void testFindMoves_Obsticles()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.b, 2, PieceGlobals.KNIGHT, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.d, 3, PieceGlobals.BISHOP, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.c, 4, PieceGlobals.BISHOP, PieceGlobals.BLACK);
		Piece piece = board.getSquare(SquareGlobals.b, 2).getOccupant();
		piece.findMoves();
		Collection moves = piece.getMoveOptions();
		assertEquals(3, moves.size());
		assertTrue(moves.contains(board.getSquare(SquareGlobals.a, 4)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.d, 1)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.c, 4)));
	}
	
	public void testFindMoves_ReveilingCheck()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.c, 1, PieceGlobals.KING, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.d, 2, PieceGlobals.KNIGHT, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.h, 6, PieceGlobals.BISHOP, PieceGlobals.BLACK);
		Piece piece = board.getSquare(SquareGlobals.d, 2).getOccupant();
		Piece attacker = board.getSquare(SquareGlobals.h, 6).getOccupant();
		piece.addAttacker(attacker);
		piece.findMoves();
		Collection moves = piece.getMoveOptions();
		assertEquals(0, moves.size());
	}
	
	public void testGetTextType()
	{
		Piece p1 = factory.createPiece(PieceGlobals.WHITE, PieceGlobals.KNIGHT);
		Piece p2 = factory.createPiece(PieceGlobals.BLACK, PieceGlobals.KNIGHT);
		assertEquals('N', p1.getTextType());
		assertEquals('n', p2.getTextType());
	}
	
	public void testAttackedSquares()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.b, 2, PieceGlobals.KNIGHT, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.d, 3, PieceGlobals.BISHOP, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.c, 4, PieceGlobals.BISHOP, PieceGlobals.BLACK);
		Piece piece = board.getSquare(SquareGlobals.b, 2).getOccupant();
		piece.findMoves();
		Collection attacks = piece.getAttacks();
		Collection attackedPieces = piece.getAttackedPieces();
		assertEquals(4, attacks.size());
		assertTrue(attacks.contains(board.getSquare(SquareGlobals.a, 4)));
		assertTrue(attacks.contains(board.getSquare(SquareGlobals.d, 1)));
		assertTrue(attacks.contains(board.getSquare(SquareGlobals.c, 4)));
		assertTrue(attacks.contains(board.getSquare(SquareGlobals.d, 3)));
		assertTrue(attackedPieces.contains(board.getSquare(SquareGlobals.c, 4).getOccupant()));
	}
	
	public void testIsPieceBlockingCheck()
	{
		Piece knight = factory.createPiece(PieceGlobals.WHITE, PieceGlobals.KNIGHT);
		assertTrue("Shouldn't be blocking", ! knight.isPieceBlockingCheck(new MockPiece()));
	}
}