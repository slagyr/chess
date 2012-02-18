package chess.piece;

import junit.framework.*;
import java.util.*;

import chess.piece.*;
import chess.board.*;

public class QueenTest extends TestCase
{
	PieceFactory factory;

	public QueenTest(String name)
	{
		super(name);
		factory = new PieceFactory();
	}
	
	public void testGetType()
	{
		Piece piece = factory.createPiece(PieceGlobals.QUEEN);
		assertEquals("Queen", piece.getType());
	}
	
	public void testFindMoves_EmptyBoard()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.d, 4, PieceGlobals.QUEEN, PieceGlobals.WHITE);
		Piece piece = board.getSquare(SquareGlobals.d, 4).getOccupant();
		piece.findMoves();
		Collection moves = piece.getMoveOptions();
		assertEquals(27, moves.size());
		assertTrue(moves.contains(board.getSquare(SquareGlobals.d, 5)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.d, 6)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.d, 7)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.d, 8)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.e, 5)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.f, 6)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.g, 7)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.h, 8)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.b, 6)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.e, 4)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.f, 4)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.g, 4)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.h, 4)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.e, 3)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.f, 2)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.g, 1)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.d, 3)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.d, 2)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.d, 1)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.c, 3)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.b, 2)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.a, 1)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.c, 4)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.b, 4)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.a, 4)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.c, 5)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.b, 6)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.a, 7)));
	}
	
	public void testFindMoves_Obsticles()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.a, 1, PieceGlobals.QUEEN, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.b, 1, PieceGlobals.BISHOP, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.a, 2, PieceGlobals.BISHOP, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.c, 3, PieceGlobals.BISHOP, PieceGlobals.BLACK);
		Piece piece = board.getSquare(SquareGlobals.a, 1).getOccupant();
		piece.findMoves();
		Collection moves = piece.getMoveOptions();
		assertEquals(3, moves.size());
		assertTrue(moves.contains(board.getSquare(SquareGlobals.a, 2)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.b, 2)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.c, 3)));
	}
	
	public void testFindMoves_ReveilingCheck()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.c, 1, PieceGlobals.KING, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.d, 2, PieceGlobals.QUEEN, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.h, 6, PieceGlobals.BISHOP, PieceGlobals.BLACK);
		Piece piece = board.getSquare(SquareGlobals.d, 2).getOccupant();
		Piece attacker = board.getSquare(SquareGlobals.h, 6).getOccupant();
		piece.addAttacker(attacker);
		piece.findMoves();
		Collection moves = piece.getMoveOptions();
		assertEquals(4, moves.size());
		assertTrue(moves.contains(board.getSquare(SquareGlobals.e, 3)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.f, 4)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.g, 5)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.h, 6)));
	}
	
	public void testGetTextType()
	{
		Piece p1 = factory.createPiece(PieceGlobals.WHITE, PieceGlobals.QUEEN);
		Piece p2 = factory.createPiece(PieceGlobals.BLACK, PieceGlobals.QUEEN);
		assertEquals('Q', p1.getTextType());
		assertEquals('q', p2.getTextType());
	}
	
	public void testAttackedSqaures()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.a, 1, PieceGlobals.QUEEN, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.b, 1, PieceGlobals.BISHOP, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.a, 2, PieceGlobals.BISHOP, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.c, 3, PieceGlobals.BISHOP, PieceGlobals.BLACK);
		Piece piece = board.getSquare(SquareGlobals.a, 1).getOccupant();
		piece.findMoves();
		Collection attacks = piece.getAttacks();
		Collection attackedPieces = piece.getAttackedPieces();
		assertEquals(4, attacks.size());
		assertTrue(attacks.contains(board.getSquare(SquareGlobals.a, 2)));
		assertTrue(attacks.contains(board.getSquare(SquareGlobals.b, 2)));
		assertTrue(attacks.contains(board.getSquare(SquareGlobals.c, 3)));
		assertTrue(attacks.contains(board.getSquare(SquareGlobals.b, 1)));
		assertTrue(attackedPieces.contains(board.getSquare(SquareGlobals.a, 2).getOccupant()));
		assertTrue(attackedPieces.contains(board.getSquare(SquareGlobals.c, 3).getOccupant()));
	}
	
	public void testIsPieceBlockingCheck()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.f, 8, PieceGlobals.KING, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.d, 6, PieceGlobals.BISHOP, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.a, 8, PieceGlobals.ROOK, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.a, 3, PieceGlobals.QUEEN, PieceGlobals.BLACK);
		Piece blocker = board.getSquare(SquareGlobals.d, 6).getOccupant();
		Piece rook = board.getSquare(SquareGlobals.a, 8).getOccupant();
		Piece attacker = board.getSquare(SquareGlobals.a, 3).getOccupant();
		assertTrue("Should be blocking", attacker.isPieceBlockingCheck(blocker));
		assertTrue("Shouldn't be blocking", ! attacker.isPieceBlockingCheck(rook));
	}
}