package chess.piece;

import junit.framework.*;
import java.util.*;

import chess.piece.*;
import chess.board.*;

public class RookTest extends TestCase
{
	PieceFactory factory;

	public RookTest(String name)
	{
		super(name);
		factory = new PieceFactory();
	}
	
	public void testGetType()
	{
		Piece piece = factory.createPiece(PieceGlobals.ROOK);
		assertEquals("Rook", piece.getType());
	}
	
	public void testFindMoves_EmptyBoard()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.d, 4, PieceGlobals.ROOK, PieceGlobals.WHITE);
		Piece piece = board.getSquare(SquareGlobals.d, 4).getOccupant();
		piece.findMoves();
		Collection moves = piece.getMoveOptions();
		assertEquals(14, moves.size());
		assertTrue(moves.contains(board.getSquare(SquareGlobals.d, 5)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.d, 6)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.d, 7)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.d, 8)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.e, 4)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.f, 4)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.g, 4)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.h, 4)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.d, 3)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.d, 2)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.d, 1)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.c, 4)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.b, 4)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.a, 4)));
	}
	
	public void testFindMoves_Obsticles()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.a, 1, PieceGlobals.ROOK, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.a, 3, PieceGlobals.BISHOP, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.c, 1, PieceGlobals.BISHOP, PieceGlobals.BLACK);
		Piece piece = board.getSquare(SquareGlobals.a, 1).getOccupant();
		piece.findMoves();
		Collection moves = piece.getMoveOptions();
		assertEquals(3, moves.size());
		assertTrue(moves.contains(board.getSquare(SquareGlobals.a, 2)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.b, 1)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.c, 1)));
	}
	
	public void testFindMoves_ReveilingCheck()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.c, 1, PieceGlobals.KING, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.d, 2, PieceGlobals.ROOK, PieceGlobals.WHITE);
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
		Piece p1 = factory.createPiece(PieceGlobals.WHITE, PieceGlobals.ROOK);
		Piece p2 = factory.createPiece(PieceGlobals.BLACK, PieceGlobals.ROOK);
		assertEquals('R', p1.getTextType());
		assertEquals('r', p2.getTextType());
	}
	
	public void testAttackedSquares()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.a, 1, PieceGlobals.ROOK, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.a, 3, PieceGlobals.BISHOP, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.c, 1, PieceGlobals.BISHOP, PieceGlobals.BLACK);
		Piece piece = board.getSquare(SquareGlobals.a, 1).getOccupant();
		piece.findMoves();
		Collection attacks = piece.getAttacks();
		Collection attackedPieces = piece.getAttackedPieces();
		assertEquals(4, attacks.size());
		assertTrue(attacks.contains(board.getSquare(SquareGlobals.a, 2)));
		assertTrue(attacks.contains(board.getSquare(SquareGlobals.b, 1)));
		assertTrue(attacks.contains(board.getSquare(SquareGlobals.c, 1)));
		assertTrue(attacks.contains(board.getSquare(SquareGlobals.a, 3)));
		assertTrue(attackedPieces.contains(board.getSquare(SquareGlobals.c, 1).getOccupant()));
	}
	
	public void testIsPieceBlockingCheck()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.f, 8, PieceGlobals.KING, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.f, 6, PieceGlobals.BISHOP, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.a, 8, PieceGlobals.ROOK, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.f, 3, PieceGlobals.ROOK, PieceGlobals.BLACK);
		Piece blocker = board.getSquare(SquareGlobals.f, 6).getOccupant();
		Piece rook = board.getSquare(SquareGlobals.a, 8).getOccupant();
		Piece attacker = board.getSquare(SquareGlobals.f, 3).getOccupant();
		assertTrue("Should be blocking", attacker.isPieceBlockingCheck(blocker));
		assertTrue("Shouldn't be blocking", ! attacker.isPieceBlockingCheck(rook));
	}
}