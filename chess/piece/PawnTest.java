package chess.piece;

import junit.framework.*;
import java.util.*;

import chess.piece.*;
import chess.board.*;
import chess.move.*;
import chess.player.Player;

public class PawnTest extends TestCase
{
	PieceFactory factory;

	public PawnTest(String name)
	{
		super(name);
		factory = new PieceFactory();
	}
	
	public void testGetType()
	{
		Piece piece = factory.createPiece(PieceGlobals.PAWN);
		assertEquals("Pawn", piece.getType());
	}
	
	public void testFindMoves_White_Single()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.d, 4, PieceGlobals.PAWN, PieceGlobals.WHITE);
		Piece piece = board.getSquare(SquareGlobals.d, 4).getOccupant();
		piece.findMoves();
		Collection moves = piece.getMoveOptions();
		assertEquals(1, moves.size());
		assertTrue(moves.contains(board.getSquare(SquareGlobals.d, 5)));
	}
	
	public void testFindMoves_Blocked()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.d, 4, PieceGlobals.PAWN, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.d, 5, PieceGlobals.PAWN, PieceGlobals.BLACK);
		Piece piece = board.getSquare(SquareGlobals.d, 4).getOccupant();
		piece.findMoves();
		Collection moves = piece.getMoveOptions();
		assertEquals(0, moves.size());
	}
	
	public void testFindMoves_Black_Single()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.d, 4, PieceGlobals.PAWN, PieceGlobals.BLACK);
		Piece piece = board.getSquare(SquareGlobals.d, 4).getOccupant();
		piece.findMoves();
		Collection moves = piece.getMoveOptions();
		assertEquals(1, moves.size());
		assertTrue(moves.contains(board.getSquare(SquareGlobals.d, 3)));
	}
	
	public void testFindMoves_White_Takes()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.d, 4, PieceGlobals.PAWN, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.c, 5, PieceGlobals.PAWN, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.e, 5, PieceGlobals.PAWN, PieceGlobals.BLACK);
		Piece piece = board.getSquare(SquareGlobals.d, 4).getOccupant();
		piece.findMoves();
		Collection moves = piece.getMoveOptions();
		assertEquals(2, moves.size());
		assertTrue(moves.contains(board.getSquare(SquareGlobals.d, 5)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.e, 5)));
	}
	
	public void testFindMoves_Black_Takes()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.d, 4, PieceGlobals.PAWN, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.c, 3, PieceGlobals.PAWN, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.e, 3, PieceGlobals.PAWN, PieceGlobals.WHITE);
		Piece piece = board.getSquare(SquareGlobals.d, 4).getOccupant();
		piece.findMoves();
		Collection moves = piece.getMoveOptions();
		assertEquals(2, moves.size());
		assertTrue(moves.contains(board.getSquare(SquareGlobals.d, 3)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.e, 3)));
	}
	
	public void testFindMoves_White_Double()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.a, 2, PieceGlobals.PAWN, PieceGlobals.WHITE);
		Piece piece = board.getSquare(SquareGlobals.a, 2).getOccupant();
		piece.findMoves();
		Collection moves = piece.getMoveOptions();
		assertEquals(2, moves.size());
		assertTrue(moves.contains(board.getSquare(SquareGlobals.a, 3)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.a, 4)));
	}
	
	public void testFindMoves_Black_Double()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.a, 7, PieceGlobals.PAWN, PieceGlobals.BLACK);
		Piece piece = board.getSquare(SquareGlobals.a, 7).getOccupant();
		piece.findMoves();
		Collection moves = piece.getMoveOptions();
		assertEquals(2, moves.size());
		assertTrue(moves.contains(board.getSquare(SquareGlobals.a, 6)));
		assertTrue(moves.contains(board.getSquare(SquareGlobals.a, 5)));
	}
	
	public void testFindMoves_ReveilingCheck()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.c, 1, PieceGlobals.KING, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.d, 2, PieceGlobals.PAWN, PieceGlobals.WHITE);
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
		Piece p1 = factory.createPiece(PieceGlobals.WHITE, PieceGlobals.PAWN);
		Piece p2 = factory.createPiece(PieceGlobals.BLACK, PieceGlobals.PAWN);
		assertEquals('P', p1.getTextType());
		assertEquals('p', p2.getTextType());
	}
	
	public void testEnPassant_White()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.a, 5, PieceGlobals.PAWN, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.b, 7, PieceGlobals.PAWN, PieceGlobals.BLACK);
		SquareInterface b6 = board.getSquare(SquareGlobals.b, 6);
		SquareInterface b5 = board.getSquare(SquareGlobals.b, 5);
		Piece whitePawn = board.getSquare(SquareGlobals.a, 5).getOccupant();
		Piece blackPawn = board.getSquare(SquareGlobals.b, 7).getOccupant();
		whitePawn.findMoves();
		Collection moves = whitePawn.getMoveOptions();
		assertEquals(1, moves.size());
		assertTrue("En-passant shouldn't be enabled", ! moves.contains(b6));
		moves = blackPawn.getMoveOptions();
		moves.add(b5);
		blackPawn.makeMove(b5);
		whitePawn.findMoves();
		moves = whitePawn.getMoveOptions();
		assertEquals(2, moves.size());
		assertTrue("En-passant should be enabled", moves.contains(b6));
	}
	
	public void testEnPassant_Black()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.b, 4, PieceGlobals.PAWN, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.a, 2, PieceGlobals.PAWN, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.c, 4, PieceGlobals.PAWN, PieceGlobals.WHITE);
		SquareInterface a3 = board.getSquare(SquareGlobals.a, 3);
		SquareInterface a4 = board.getSquare(SquareGlobals.a, 4);
		Piece blackPawn = board.getSquare(SquareGlobals.b, 4).getOccupant();
		Piece whitePawn = board.getSquare(SquareGlobals.a, 2).getOccupant();
		blackPawn.findMoves();
		Collection moves = blackPawn.getMoveOptions();
		assertEquals(1, moves.size());
		assertTrue("En-passant shouldn't be enabled", ! moves.contains(a3));
		moves = whitePawn.getMoveOptions();
		moves.add(a4);
		whitePawn.makeMove(a4);
		blackPawn.findMoves();
		moves = blackPawn.getMoveOptions();
		assertEquals(2, moves.size());
		assertTrue("En-passant should be enabled", moves.contains(a3));
	}
	
	public void testMakeMove_Enpassant_White()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.a, 5, PieceGlobals.PAWN, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.b, 7, PieceGlobals.PAWN, PieceGlobals.BLACK);
		SquareInterface b6 = board.getSquare(SquareGlobals.b, 6);
		SquareInterface b5 = board.getSquare(SquareGlobals.b, 5);
		SquareInterface a5 = board.getSquare(SquareGlobals.a, 5);
		Piece whitePawn = board.getSquare(SquareGlobals.a, 5).getOccupant();
		Piece blackPawn = board.getSquare(SquareGlobals.b, 7).getOccupant();
		whitePawn.findMoves();
		Collection moves = blackPawn.getMoveOptions();
		moves.add(b5);
		blackPawn.makeMove(b5);
		whitePawn.findMoves();

        AbstractMove m = whitePawn.makeMove(b6);
		assertSame(blackPawn, m.getTakenPiece());
		assertTrue("Should be unoccupied", ! a5.isOccupied());
		assertTrue("Should be unoccupied", ! b5.isOccupied());
		assertSame(b6, whitePawn.getLocation());
		assertSame(whitePawn, b6.getOccupant());
	}
	
	public void testMakeMove_Enpassant_Black()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.a, 4, PieceGlobals.PAWN, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.b, 2, PieceGlobals.PAWN, PieceGlobals.WHITE);
		SquareInterface b3 = board.getSquare(SquareGlobals.b, 3);
		SquareInterface b4 = board.getSquare(SquareGlobals.b, 4);
		SquareInterface a4 = board.getSquare(SquareGlobals.a, 4);
		Piece whitePawn = board.getSquare(SquareGlobals.b, 2).getOccupant();
		Piece blackPawn = board.getSquare(SquareGlobals.a, 4).getOccupant();
		blackPawn.findMoves();
		Collection moves = whitePawn.getMoveOptions();
		moves.add(b4);
		whitePawn.makeMove(b4);
		blackPawn.findMoves();
		AbstractMove m = blackPawn.makeMove(b3);
		assertSame(whitePawn, m.getTakenPiece());
		assertTrue("Should be unoccupied", ! a4.isOccupied());
		assertTrue("Should be unoccupied", ! b4.isOccupied());
		assertSame(b3, blackPawn.getLocation());
		assertSame(blackPawn, b3.getOccupant());
	}
	
	public void testAttackedSquares()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.d, 4, PieceGlobals.PAWN, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.e, 5, PieceGlobals.PAWN, PieceGlobals.BLACK);
		Piece piece = board.getSquare(SquareGlobals.d, 4).getOccupant();
		piece.findMoves();
		Collection attacks = piece.getAttacks();
		Collection attackedPieces = piece.getAttackedPieces();
		assertEquals(2, attacks.size());
		assertTrue(attacks.contains(board.getSquare(SquareGlobals.e, 5)));
		assertTrue(attacks.contains(board.getSquare(SquareGlobals.c, 5)));
		assertTrue(attackedPieces.contains(board.getSquare(SquareGlobals.e, 5).getOccupant()));
	}
	
	public void testIsPieceBlockingCheck()
	{
		Piece pawn = factory.createPiece(PieceGlobals.WHITE, PieceGlobals.PAWN);
		assertTrue("Shouldn't be blocking", ! pawn.isPieceBlockingCheck(new MockPiece()));
	}
	
	public void testReverseMove_White()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.a, 5, PieceGlobals.PAWN, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.b, 7, PieceGlobals.PAWN, PieceGlobals.BLACK);
		SquareInterface b6 = board.getSquare(SquareGlobals.b, 6);
		SquareInterface b5 = board.getSquare(SquareGlobals.b, 5);
		SquareInterface a5 = board.getSquare(SquareGlobals.a, 5);
		Piece whitePawn = board.getSquare(SquareGlobals.a, 5).getOccupant();
		Piece blackPawn = board.getSquare(SquareGlobals.b, 7).getOccupant();
		whitePawn.findMoves();
		Collection moves = blackPawn.getMoveOptions();
		moves.add(b5);
		blackPawn.makeMove(b5);
		whitePawn.findMoves();
		AbstractMove m = whitePawn.makeMove(b6);
		whitePawn.reverseMove(m);
		
		assertSame(whitePawn, a5.getOccupant());
		assertSame(blackPawn, b5.getOccupant());
		assertSame(a5, whitePawn.getLocation());
		assertSame(b5, blackPawn.getLocation());
		assertEquals(1, whitePawn.getTrail().size());
	}
	
	public void testReverseMove_Black()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.a, 4, PieceGlobals.PAWN, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.b, 2, PieceGlobals.PAWN, PieceGlobals.WHITE);
		SquareInterface b3 = board.getSquare(SquareGlobals.b, 3);
		SquareInterface b4 = board.getSquare(SquareGlobals.b, 4);
		SquareInterface a4 = board.getSquare(SquareGlobals.a, 4);
		Piece whitePawn = board.getSquare(SquareGlobals.b, 2).getOccupant();
		Piece blackPawn = board.getSquare(SquareGlobals.a, 4).getOccupant();
		blackPawn.findMoves();
		Collection moves = whitePawn.getMoveOptions();
		moves.add(b4);
		whitePawn.makeMove(b4);
		blackPawn.findMoves();
		AbstractMove m = blackPawn.makeMove(b3);
		blackPawn.reverseMove(m);
		
		assertSame(blackPawn, a4.getOccupant());
		assertSame(whitePawn, b4.getOccupant());
		assertSame(a4, blackPawn.getLocation());
		assertSame(b4, whitePawn.getLocation());
		assertEquals(1, blackPawn.getTrail().size());
	}
	
	public void testPromotionMove()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.a, 2, PieceGlobals.PAWN, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.b, 7, PieceGlobals.PAWN, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.c, 4, PieceGlobals.PAWN, PieceGlobals.WHITE);
		SquareInterface a1 = board.getSquare(SquareGlobals.a, 1);
		SquareInterface b8 = board.getSquare(SquareGlobals.b, 8);
		SquareInterface c5 = board.getSquare(SquareGlobals.c, 5);
		Piece whitePawn = board.getSquare(SquareGlobals.b, 7).getOccupant();
		Piece blackPawn = board.getSquare(SquareGlobals.a, 2).getOccupant();
		Piece badPawn = board.getSquare(SquareGlobals.c, 4).getOccupant();
		
		AbstractMove move = blackPawn.makeMove(a1);
		assertEquals(MoveGlobals.PROMOTION, move.getType());
		move = whitePawn.makeMove(b8);
		assertEquals(MoveGlobals.PROMOTION, move.getType());
		move = badPawn.makeMove(c5);
		assertEquals(MoveGlobals.MOVE, move.getType());
	}
	
	public void testReversePromotionMove()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.a, 2, PieceGlobals.PAWN, PieceGlobals.BLACK);
		SquareInterface a1 = board.getSquare(SquareGlobals.a, 1);
		Piece blackPawn = board.getSquare(SquareGlobals.a, 2).getOccupant();
		
		AbstractMove move = blackPawn.makeMove(a1);
		assertEquals(MoveGlobals.PROMOTION, move.getType());
		blackPawn.reverseMove(move);
		assertSame(blackPawn, board.getSquare("a2").getOccupant());
		assertTrue(! board.getSquare("a1").getOccupant().isValid());
	}
	
	public void testCheckRevealingMove()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.b, 1, PieceGlobals.KING, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.d, 1, PieceGlobals.ROOK, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.h, 1, PieceGlobals.ROOK, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.f, 2, PieceGlobals.PAWN, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.g, 2, PieceGlobals.PAWN, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.h, 2, PieceGlobals.PAWN, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.b, 2, PieceGlobals.PAWN, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.a, 3, PieceGlobals.PAWN, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.b, 3, PieceGlobals.BISHOP, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.a, 7, PieceGlobals.BISHOP, PieceGlobals.WHITE);
		
		board.placePiece(SquareGlobals.a, 8, PieceGlobals.BISHOP, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.c, 8, PieceGlobals.ROOK, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.f, 7, PieceGlobals.PAWN, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.g, 7, PieceGlobals.PAWN, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.h, 7, PieceGlobals.PAWN, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.d, 6, PieceGlobals.BISHOP, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.e, 6, PieceGlobals.KING, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.f, 6, PieceGlobals.KNIGHT, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.d, 5, PieceGlobals.PAWN, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.e, 5, PieceGlobals.PAWN, PieceGlobals.BLACK);
		
		Player black = new Player(PieceGlobals.BLACK);
		Player white = new Player(PieceGlobals.WHITE);
		black.setBoard(board);
		white.setBoard(board);
		black.loadPieces();
		white.loadPieces();
		white.findAllMoves(false);
		black.findAllMoves(false);
		Piece pawn = board.getSquare("d5").getOccupant();
		SquareInterface badSquare = board.getSquare("d4");
		assertTrue("this move reveals check", !pawn.getMoveOptions().contains(badSquare));
	}
}