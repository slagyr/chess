package chess.player;

import junit.framework.*;
import java.util.*;

import chess.player.Player;
import chess.piece.*;
import chess.board.*;
import chess.move.AbstractMove;

public class PlayerTest extends TestCase
{
	public PlayerTest(String name)
	{
		super(name);
	}
	
	public void testConstructor()
	{
		Player player = new Player(PieceGlobals.WHITE);
		assertEquals(PieceGlobals.WHITE, player.getColor());
	}
	
	public void testSetGetBoard()
	{
		Player player = new Player(PieceGlobals.WHITE);
		ChessBoard board = new ChessBoard();
		player.setBoard(board);
		assertSame(board, player.getBoard());
	}
	
	public void testLoadPieces()
	{
		Player player = new Player(PieceGlobals.WHITE);
		ChessBoard board = new ChessBoard();
		board.normalSetup();
		player.setBoard(board);
		player.loadPieces();
		Collection pieces = player.getPieces();
		assertEquals(16, pieces.size()); 
	}
	
	public void testFindAllMoves()
	{
		Player player = new Player(PieceGlobals.WHITE);
		ChessBoard board = new ChessBoard();
		board.normalSetup();
		player.setBoard(board);
		player.loadPieces();
		player.findAllMoves(false);
		int moves = 0;
		Collection pieces = player.getPieces();
		for(Iterator i = pieces.iterator(); i.hasNext(); )
		{
			Piece piece = (Piece)i.next();
			moves += piece.getMoveOptions().size();
		}
		assertTrue(moves > 0);
	}
	
	public void testHasPieceOn()
	{
		Player player = new Player(PieceGlobals.WHITE);
		ChessBoard board = new ChessBoard();
		board.normalSetup();
		player.setBoard(board);
		player.loadPieces();
		SquareInterface square1 = board.getSquare(SquareGlobals.a, 1);
		SquareInterface square2 = board.getSquare(SquareGlobals.a, 3);
		assertTrue("should have piece", player.hasPieceOn(square1));
		assertTrue("shouldn't have piece", ! player.hasPieceOn(square2));
	}
	
	public void testMovePiece()
	{
		Player player = new Player(PieceGlobals.WHITE);
		ChessBoard board = new ChessBoard();
		board.normalSetup();
		player.setBoard(board);
		player.loadPieces();
		player.findAllMoves(false);
		Piece taken = board.getSquare("e4").getOccupant();
		AbstractMove move = player.movePiece("e2", "e4");
		assertTrue("should be a good move", move.isValid());
		move = player.movePiece("a1", "a2");
		assertTrue("shouldn't be a good move", ! move.isValid());
	}
	
	public void testGetKing()
	{
		Player player = new Player(PieceGlobals.WHITE);
		ChessBoard board = new ChessBoard();
		board.normalSetup();
		player.setBoard(board);
		player.loadPieces();
		Piece king = player.getKing();
		assertEquals(PieceGlobals.WHITE, king.getColor());
		assertEquals(PieceGlobals.KING, king.getType());
	}
	
	public void testGainTakenPiece_LosePiece()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.a, 1, PieceGlobals.PAWN, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.a, 2, PieceGlobals.PAWN, PieceGlobals.BLACK);
		Player player = new Player(PieceGlobals.WHITE);
		player.setBoard(board);
		player.loadPieces();
		Piece p1 = board.getSquare("a1").getOccupant();
		Piece p2 = board.getSquare("a2").getOccupant();
		player.losePiece(p1);
		assertEquals(0, player.getPieces().size());
		player.gainTakenPiece(p1);
		assertEquals(1, player.getTakenPieces().size());
	}
	
	public void testIsCheck()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.a, 8, PieceGlobals.KING, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.c, 8, PieceGlobals.BISHOP, PieceGlobals.WHITE);
		Player player = new Player(PieceGlobals.WHITE);
		player.setBoard(board);
		player.loadPieces();
		player.findAllMoves(false);
		assertTrue("Shouldn't be check", ! player.isCheck());
		player.movePiece("c8", "b7");
		player.findAllMoves(false);
		assertTrue("Should be check", player.isCheck());
	}
	
	public void isInCheck()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.a, 8, PieceGlobals.KING, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.c, 8, PieceGlobals.BISHOP, PieceGlobals.WHITE);
		Player player = new Player(PieceGlobals.BLACK);
		Player enemy = new Player(PieceGlobals.WHITE);
		player.setBoard(board);
		enemy.setBoard(board);
		player.loadPieces();
		enemy.loadPieces();
		enemy.findAllMoves(false);
		assertTrue("Shouldn't be check", ! player.isInCheck());
		enemy.movePiece("c8", "b7");
		enemy.findAllMoves(false);
		assertTrue("Should be check", player.isInCheck());
	}
	
	public void testIsInCheckMate()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.a, 1, PieceGlobals.KING, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.h, 2, PieceGlobals.ROOK, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.g, 2, PieceGlobals.ROOK, PieceGlobals.WHITE);
		Player black = new Player(PieceGlobals.BLACK);
		Player white = new Player(PieceGlobals.WHITE);
		black.setBoard(board);
		white.setBoard(board);
		black.loadPieces();
		white.loadPieces();
		white.findAllMoves(false);
		black.findAllMoves(false);
		assertTrue(! black.isInCheckMate());
		white.movePiece("h2", "h1");
		white.findAllMoves(false);
		black.findAllMoves(true);
		assertTrue(black.isInCheckMate());
	}
	
	public void testIsInStaleMate()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.a, 1, PieceGlobals.KING, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.h, 2, PieceGlobals.ROOK, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.h, 8, PieceGlobals.ROOK, PieceGlobals.WHITE);
		Player black = new Player(PieceGlobals.BLACK);
		Player white = new Player(PieceGlobals.WHITE);
		black.setBoard(board);
		white.setBoard(board);
		black.loadPieces();
		white.loadPieces();
		white.findAllMoves(false);
		black.findAllMoves(false);
		assertTrue(! black.isInStaleMate());
		white.movePiece("h8", "b8");
		white.findAllMoves(false);
		black.findAllMoves(true);
		assertTrue(black.isInStaleMate());
	}
	
	public void testReverseMove()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.a, 8, PieceGlobals.ROOK, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.c, 8, PieceGlobals.BISHOP, PieceGlobals.WHITE);
		Piece rook = board.getSquare("a8").getOccupant();
		Piece bishop = board.getSquare("c8").getOccupant();
		Player black = new Player(PieceGlobals.BLACK);
		Player white = new Player(PieceGlobals.WHITE);
		black.setOpponent(white);
		white.setOpponent(black);
		black.setBoard(board);
		white.setBoard(board);
		black.loadPieces();
		white.loadPieces();
		black.findAllMoves(false);
		AbstractMove move = black.movePiece("a8", "c8");
		assertEquals(1, black.getTakenPieces().size());
		assertEquals(0, white.getPieces().size());
		black.reverseMove(move);
		assertSame(rook, board.getSquare("a8").getOccupant());
		assertSame(board.getSquare("a8"), rook.getLocation());
		assertSame(bishop, board.getSquare("c8").getOccupant());
		assertSame(board.getSquare("c8"), bishop.getLocation());
		assertEquals(0, black.getTakenPieces().size());
		assertEquals(1, white.getPieces().size());
	}
	
	public void testSetGetOpponent()
	{
		Player black = new Player(PieceGlobals.BLACK);
		Player white = new Player(PieceGlobals.WHITE);
		white.setOpponent(black);
		assertSame(black, white.getOpponent());
	}
	
	public void testRegainLostPiece()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.a, 1, PieceGlobals.PAWN, PieceGlobals.WHITE);
		Player player = new Player(PieceGlobals.WHITE);
		player.setBoard(board);
		player.loadPieces();
		Piece p1 = board.getSquare("a1").getOccupant();
		player.losePiece(p1);
		player.regainLostPiece(p1);
		assertEquals(1, player.getPieces().size());
	
	}
	
	public void testIsInCheckMate_bugFound()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.a, 1, PieceGlobals.ROOK, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.b, 1, PieceGlobals.KNIGHT, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.e, 1, PieceGlobals.KING, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.h, 1, PieceGlobals.ROOK, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.a, 2, PieceGlobals.PAWN, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.f, 2, PieceGlobals.PAWN, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.g, 2, PieceGlobals.PAWN, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.h, 2, PieceGlobals.PAWN, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.d, 4, PieceGlobals.PAWN, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.a, 6, PieceGlobals.BISHOP, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.c, 8, PieceGlobals.QUEEN, PieceGlobals.WHITE);
		
		board.placePiece(SquareGlobals.d, 7, PieceGlobals.KING, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.e, 7, PieceGlobals.PAWN, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.f, 7, PieceGlobals.PAWN, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.d, 6, PieceGlobals.QUEEN, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.a, 5, PieceGlobals.PAWN, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.h, 5, PieceGlobals.PAWN, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.e, 4, PieceGlobals.BISHOP, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.e, 3, PieceGlobals.BISHOP, PieceGlobals.BLACK);
		
		Player black = new Player(PieceGlobals.BLACK);
		Player white = new Player(PieceGlobals.WHITE);
		black.setBoard(board);
		white.setBoard(board);
		black.loadPieces();
		white.loadPieces();
		white.findAllMoves(false);
		black.findAllMoves(true);
		assertTrue(black.isInCheckMate());
	}
	
	public void testIsInCheckMate_AnotherBugFound()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.a, 2, PieceGlobals.PAWN, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.b, 2, PieceGlobals.PAWN, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.d, 4, PieceGlobals.PAWN, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.e, 4, PieceGlobals.PAWN, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.c, 1, PieceGlobals.ROOK, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.e, 5, PieceGlobals.BISHOP, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.b, 5, PieceGlobals.KING, PieceGlobals.WHITE);
		
		board.placePiece(SquareGlobals.c, 7, PieceGlobals.PAWN, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.e, 7, PieceGlobals.PAWN, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.f, 7, PieceGlobals.PAWN, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.g, 7, PieceGlobals.PAWN, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.a, 6, PieceGlobals.PAWN, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.h, 5, PieceGlobals.PAWN, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.h, 1, PieceGlobals.KNIGHT, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.h, 2, PieceGlobals.KNIGHT, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.f, 8, PieceGlobals.BISHOP, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.h, 8, PieceGlobals.ROOK, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.a, 8, PieceGlobals.ROOK, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.f, 3, PieceGlobals.QUEEN, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.e, 8, PieceGlobals.KING, PieceGlobals.BLACK);
System.err.println(board);
		
		Player black = new Player(PieceGlobals.BLACK);
		Player white = new Player(PieceGlobals.WHITE);
		black.setBoard(board);
		white.setBoard(board);
		black.loadPieces();
		white.loadPieces();
		black.findAllMoves(false);
		white.findAllMoves(true);
		
		assertTrue(!white.isInCheckMate());
	}

    public void testOnlyKingsRemain()
    {
		ChessBoard board = new ChessBoard();
        Player white = new Player(PieceGlobals.WHITE);
        Player black = new Player(PieceGlobals.BLACK);
        black.setOpponent(white);
        white.setOpponent(black);
        white.getPieces().add(PieceFactory.createPiece(PieceGlobals.WHITE, PieceGlobals.KING));
        black.getPieces().add(PieceFactory.createPiece(PieceGlobals.BLACK, PieceGlobals.KING));
        Piece pawn = PieceFactory.createPiece(PieceGlobals.BLACK, PieceGlobals.PAWN);
        black.getPieces().add(pawn);
        assertTrue("not only kings(white)", ! white.onlyKingsRemain());
        assertTrue("not only kings(black)", ! black.onlyKingsRemain());
        black.losePiece(pawn);
        assertTrue("only kings(white)", white.onlyKingsRemain());
        assertTrue("only kings(black)", black.onlyKingsRemain());


    }
}
