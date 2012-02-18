package chess.board;

import junit.framework.*;
import java.util.*;

import chess.board.*;
import chess.piece.*;

public class ChessBoardTest extends TestCase
{

	ChessBoard board;

	public ChessBoardTest(String name)
	{
		super(name);
	}
	
	public void setUp()
	{
		board = new ChessBoard();
	}
	
	public void testNormalSetup()
	{
		board.normalSetup();
		checkOccupant(board, SquareGlobals.a, 1, PieceGlobals.ROOK, PieceGlobals.WHITE);
		checkOccupant(board, SquareGlobals.b, 1, PieceGlobals.KNIGHT, PieceGlobals.WHITE);
		checkOccupant(board, SquareGlobals.c, 1, PieceGlobals.BISHOP, PieceGlobals.WHITE);
		checkOccupant(board, SquareGlobals.d, 1, PieceGlobals.QUEEN, PieceGlobals.WHITE);
		checkOccupant(board, SquareGlobals.e, 1, PieceGlobals.KING, PieceGlobals.WHITE);
		checkOccupant(board, SquareGlobals.f, 1, PieceGlobals.BISHOP, PieceGlobals.WHITE);
		checkOccupant(board, SquareGlobals.g, 1, PieceGlobals.KNIGHT, PieceGlobals.WHITE);
		checkOccupant(board, SquareGlobals.h, 1, PieceGlobals.ROOK, PieceGlobals.WHITE);
		
		checkOccupant(board, SquareGlobals.a, 2, PieceGlobals.PAWN, PieceGlobals.WHITE);
		checkOccupant(board, SquareGlobals.b, 2, PieceGlobals.PAWN, PieceGlobals.WHITE);
		checkOccupant(board, SquareGlobals.c, 2, PieceGlobals.PAWN, PieceGlobals.WHITE);
		checkOccupant(board, SquareGlobals.d, 2, PieceGlobals.PAWN, PieceGlobals.WHITE);
		checkOccupant(board, SquareGlobals.e, 2, PieceGlobals.PAWN, PieceGlobals.WHITE);
		checkOccupant(board, SquareGlobals.f, 2, PieceGlobals.PAWN, PieceGlobals.WHITE);
		checkOccupant(board, SquareGlobals.g, 2, PieceGlobals.PAWN, PieceGlobals.WHITE);
		checkOccupant(board, SquareGlobals.h, 2, PieceGlobals.PAWN, PieceGlobals.WHITE);
		
		checkOccupant(board, SquareGlobals.a, 8, PieceGlobals.ROOK, PieceGlobals.BLACK);
		checkOccupant(board, SquareGlobals.b, 8, PieceGlobals.KNIGHT, PieceGlobals.BLACK);
		checkOccupant(board, SquareGlobals.c, 8, PieceGlobals.BISHOP, PieceGlobals.BLACK);
		checkOccupant(board, SquareGlobals.d, 8, PieceGlobals.QUEEN, PieceGlobals.BLACK);
		checkOccupant(board, SquareGlobals.e, 8, PieceGlobals.KING, PieceGlobals.BLACK);
		checkOccupant(board, SquareGlobals.f, 8, PieceGlobals.BISHOP, PieceGlobals.BLACK);
		checkOccupant(board, SquareGlobals.g, 8, PieceGlobals.KNIGHT, PieceGlobals.BLACK);
		checkOccupant(board, SquareGlobals.h, 8, PieceGlobals.ROOK, PieceGlobals.BLACK);
		
		checkOccupant(board, SquareGlobals.a, 7, PieceGlobals.PAWN, PieceGlobals.BLACK);
		checkOccupant(board, SquareGlobals.b, 7, PieceGlobals.PAWN, PieceGlobals.BLACK);
		checkOccupant(board, SquareGlobals.c, 7, PieceGlobals.PAWN, PieceGlobals.BLACK);
		checkOccupant(board, SquareGlobals.d, 7, PieceGlobals.PAWN, PieceGlobals.BLACK);
		checkOccupant(board, SquareGlobals.e, 7, PieceGlobals.PAWN, PieceGlobals.BLACK);
		checkOccupant(board, SquareGlobals.f, 7, PieceGlobals.PAWN, PieceGlobals.BLACK);
		checkOccupant(board, SquareGlobals.g, 7, PieceGlobals.PAWN, PieceGlobals.BLACK);
		checkOccupant(board, SquareGlobals.h, 7, PieceGlobals.PAWN, PieceGlobals.BLACK);
	}
	
	public void testGetPiecesOnBoard()
	{
		ChessBoard board = new ChessBoard();
		board.normalSetup();
		Collection pieces = board.getPiecesOnBoard();
		assertEquals(32, pieces.size());
	}
	
	public void testGetSquare()
	{
		ChessBoard board = new ChessBoard();
		SquareInterface square = board.getSquare("e4");
		assertEquals("e4", square.toString());
	}
	
	public void testToString()
	{
		ChessBoard board = new ChessBoard();
		board.normalSetup();
		String s = board.toString();
		assertEquals(normalSetUpToString, s);
	}
	
	public void testGetPositionCode()
	{
		ChessBoard board1 = new ChessBoard();
		board1.normalSetup();
		ChessBoard board2 = new ChessBoard();
		board2.normalSetup();
		assertEquals(board1.getPositionCode(), board2.getPositionCode());
	}
	
	private void checkSquare(int file, int rank, String n, String ne, String e, String se, String s, String sw, String w, String nw)
	{
		SquareInterface square = board.getSquare(file, rank);
		assertTrue("" + file + " " + rank, square.isValid());
		assertEquals(square.getNeighbor(SquareGlobals.N).toString(), n);
		assertEquals(square.getNeighbor(SquareGlobals.NE).toString(), ne);
		assertEquals(square.getNeighbor(SquareGlobals.E).toString(), e);
		assertEquals(square.getNeighbor(SquareGlobals.SE).toString(), se);
		assertEquals(square.getNeighbor(SquareGlobals.S).toString(), s);
		assertEquals(square.getNeighbor(SquareGlobals.SW).toString(), sw);
		assertEquals(square.getNeighbor(SquareGlobals.W).toString(), w);
		assertEquals(square.getNeighbor(SquareGlobals.NW).toString(), nw);
	}
	
	private void checkOccupant(ChessBoard board, int file, int rank, String type, String color)
	{
		SquareInterface square = board.getSquare(file, rank);
		Piece piece = square.getOccupant();
		assertEquals(type, piece.getType());
		assertEquals(color, piece.getColor());
		assertEquals(square, piece.getLocation());
	}
	
	public void testConstruction()
	{
		checkSquare(SquareGlobals.a, 1, "a2", "b2", "b1", "empty(a1)", "empty(a1)", "empty(a1)", "empty(a1)", "empty(a1)");
		checkSquare(SquareGlobals.a, 2, "a3", "b3", "b2", "b1", "a1", "empty(a2)", "empty(a2)", "empty(a2)");
		checkSquare(SquareGlobals.a, 3, "a4", "b4", "b3", "b2", "a2", "empty(a3)", "empty(a3)", "empty(a3)");
		checkSquare(SquareGlobals.a, 4, "a5", "b5", "b4", "b3", "a3", "empty(a4)", "empty(a4)", "empty(a4)");
		checkSquare(SquareGlobals.a, 5, "a6", "b6", "b5", "b4", "a4", "empty(a5)", "empty(a5)", "empty(a5)");
		checkSquare(SquareGlobals.a, 6, "a7", "b7", "b6", "b5", "a5", "empty(a6)", "empty(a6)", "empty(a6)");
		checkSquare(SquareGlobals.a, 7, "a8", "b8", "b7", "b6", "a6", "empty(a7)", "empty(a7)", "empty(a7)");
		checkSquare(SquareGlobals.a, 8, "empty(a8)", "empty(a8)", "b8", "b7", "a7", "empty(a8)", "empty(a8)", "empty(a8)");
		
		checkSquare(SquareGlobals.b, 1, "b2", "c2", "c1", "empty(b1)", "empty(b1)", "empty(b1)", "a1", "a2");
		checkSquare(SquareGlobals.b, 2, "b3", "c3", "c2", "c1", "b1", "a1", "a2", "a3");
		checkSquare(SquareGlobals.b, 3, "b4", "c4", "c3", "c2", "b2", "a2", "a3", "a4");
		checkSquare(SquareGlobals.b, 4, "b5", "c5", "c4", "c3", "b3", "a3", "a4", "a5");
		checkSquare(SquareGlobals.b, 5, "b6", "c6", "c5", "c4", "b4", "a4", "a5", "a6");
		checkSquare(SquareGlobals.b, 6, "b7", "c7", "c6", "c5", "b5", "a5", "a6", "a7");
		checkSquare(SquareGlobals.b, 7, "b8", "c8", "c7", "c6", "b6", "a6", "a7", "a8");
		checkSquare(SquareGlobals.b, 8, "empty(b8)", "empty(b8)", "c8", "c7", "b7", "a7", "a8", "empty(b8)");
		
		checkSquare(SquareGlobals.c, 1, "c2", "d2", "d1", "empty(c1)", "empty(c1)", "empty(c1)", "b1", "b2");
		checkSquare(SquareGlobals.c, 2, "c3", "d3", "d2", "d1", "c1", "b1", "b2", "b3");
		checkSquare(SquareGlobals.c, 3, "c4", "d4", "d3", "d2", "c2", "b2", "b3", "b4");
		checkSquare(SquareGlobals.c, 4, "c5", "d5", "d4", "d3", "c3", "b3", "b4", "b5");
		checkSquare(SquareGlobals.c, 5, "c6", "d6", "d5", "d4", "c4", "b4", "b5", "b6");
		checkSquare(SquareGlobals.c, 6, "c7", "d7", "d6", "d5", "c5", "b5", "b6", "b7");
		checkSquare(SquareGlobals.c, 7, "c8", "d8", "d7", "d6", "c6", "b6", "b7", "b8");
		checkSquare(SquareGlobals.c, 8, "empty(c8)", "empty(c8)", "d8", "d7", "c7", "b7", "b8", "empty(c8)");
		
		checkSquare(SquareGlobals.d, 1, "d2", "e2", "e1", "empty(d1)", "empty(d1)", "empty(d1)", "c1", "c2");
		checkSquare(SquareGlobals.d, 2, "d3", "e3", "e2", "e1", "d1", "c1", "c2", "c3");
		checkSquare(SquareGlobals.d, 3, "d4", "e4", "e3", "e2", "d2", "c2", "c3", "c4");
		checkSquare(SquareGlobals.d, 4, "d5", "e5", "e4", "e3", "d3", "c3", "c4", "c5");
		checkSquare(SquareGlobals.d, 5, "d6", "e6", "e5", "e4", "d4", "c4", "c5", "c6");
		checkSquare(SquareGlobals.d, 6, "d7", "e7", "e6", "e5", "d5", "c5", "c6", "c7");
		checkSquare(SquareGlobals.d, 7, "d8", "e8", "e7", "e6", "d6", "c6", "c7", "c8");
		checkSquare(SquareGlobals.d, 8, "empty(d8)", "empty(d8)", "e8", "e7", "d7", "c7", "c8", "empty(d8)");
		
		checkSquare(SquareGlobals.e, 1, "e2", "f2", "f1", "empty(e1)", "empty(e1)", "empty(e1)", "d1", "d2");
		checkSquare(SquareGlobals.e, 2, "e3", "f3", "f2", "f1", "e1", "d1", "d2", "d3");
		checkSquare(SquareGlobals.e, 3, "e4", "f4", "f3", "f2", "e2", "d2", "d3", "d4");
		checkSquare(SquareGlobals.e, 4, "e5", "f5", "f4", "f3", "e3", "d3", "d4", "d5");
		checkSquare(SquareGlobals.e, 5, "e6", "f6", "f5", "f4", "e4", "d4", "d5", "d6");
		checkSquare(SquareGlobals.e, 6, "e7", "f7", "f6", "f5", "e5", "d5", "d6", "d7");
		checkSquare(SquareGlobals.e, 7, "e8", "f8", "f7", "f6", "e6", "d6", "d7", "d8");
		checkSquare(SquareGlobals.e, 8, "empty(e8)", "empty(e8)", "f8", "f7", "e7", "d7", "d8", "empty(e8)");
		
		checkSquare(SquareGlobals.f, 1, "f2", "g2", "g1", "empty(f1)", "empty(f1)", "empty(f1)", "e1", "e2");
		checkSquare(SquareGlobals.f, 2, "f3", "g3", "g2", "g1", "f1", "e1", "e2", "e3");
		checkSquare(SquareGlobals.f, 3, "f4", "g4", "g3", "g2", "f2", "e2", "e3", "e4");
		checkSquare(SquareGlobals.f, 4, "f5", "g5", "g4", "g3", "f3", "e3", "e4", "e5");
		checkSquare(SquareGlobals.f, 5, "f6", "g6", "g5", "g4", "f4", "e4", "e5", "e6");
		checkSquare(SquareGlobals.f, 6, "f7", "g7", "g6", "g5", "f5", "e5", "e6", "e7");
		checkSquare(SquareGlobals.f, 7, "f8", "g8", "g7", "g6", "f6", "e6", "e7", "e8");
		checkSquare(SquareGlobals.f, 8, "empty(f8)", "empty(f8)", "g8", "g7", "f7", "e7", "e8", "empty(f8)");
		
		checkSquare(SquareGlobals.g, 1, "g2", "h2", "h1", "empty(g1)", "empty(g1)", "empty(g1)", "f1", "f2");
		checkSquare(SquareGlobals.g, 2, "g3", "h3", "h2", "h1", "g1", "f1", "f2", "f3");
		checkSquare(SquareGlobals.g, 3, "g4", "h4", "h3", "h2", "g2", "f2", "f3", "f4");
		checkSquare(SquareGlobals.g, 4, "g5", "h5", "h4", "h3", "g3", "f3", "f4", "f5");
		checkSquare(SquareGlobals.g, 5, "g6", "h6", "h5", "h4", "g4", "f4", "f5", "f6");
		checkSquare(SquareGlobals.g, 6, "g7", "h7", "h6", "h5", "g5", "f5", "f6", "f7");
		checkSquare(SquareGlobals.g, 7, "g8", "h8", "h7", "h6", "g6", "f6", "f7", "f8");
		checkSquare(SquareGlobals.g, 8, "empty(g8)", "empty(g8)", "h8", "h7", "g7", "f7", "f8", "empty(g8)");
		
		checkSquare(SquareGlobals.h, 1, "h2", "empty(h1)", "empty(h1)", "empty(h1)", "empty(h1)", "empty(h1)", "g1", "g2");
		checkSquare(SquareGlobals.h, 2, "h3", "empty(h2)", "empty(h2)", "empty(h2)", "h1", "g1", "g2", "g3");
		checkSquare(SquareGlobals.h, 3, "h4", "empty(h3)", "empty(h3)", "empty(h3)", "h2", "g2", "g3", "g4");
		checkSquare(SquareGlobals.h, 4, "h5", "empty(h4)", "empty(h4)", "empty(h4)", "h3", "g3", "g4", "g5");
		checkSquare(SquareGlobals.h, 5, "h6", "empty(h5)", "empty(h5)", "empty(h5)", "h4", "g4", "g5", "g6");
		checkSquare(SquareGlobals.h, 6, "h7", "empty(h6)", "empty(h6)", "empty(h6)", "h5", "g5", "g6", "g7");
		checkSquare(SquareGlobals.h, 7, "h8", "empty(h7)", "empty(h7)", "empty(h7)", "h6", "g6", "g7", "g8");
		checkSquare(SquareGlobals.h, 8, "empty(h8)", "empty(h8)", "empty(h8)", "empty(h8)", "h7", "g7", "g8", "empty(h8)");
	}
	
	public void testAddGetRemovePosition()
	{
		ChessBoard board = new ChessBoard();
		board.normalSetup();
		board.recordPosition();
		ArrayList positions = board.getPositionRecord();
		assertEquals(1, positions.size());
		board.recordPosition();
		board.getSquare("e2").setOccupant(new MockPiece());
		board.recordPosition();
		positions = board.getPositionRecord();
		String lastPosition = board.getPositionCode();
		assertEquals(3, positions.size());
		board.removeLastPosition();
		positions = board.getPositionRecord();
		assertEquals(2, positions.size());
		assertTrue( ! positions.contains(lastPosition));
	}
	
	public void testIsThirdOccuranceOfPosition()
	{
		ChessBoard board = new ChessBoard();
		board.normalSetup();
		board.recordPosition();
		board.recordPosition();
		assertTrue( ! board.isThirdOccuranceOfPosition() );
		board.getSquare("e2").setOccupant(new MockPiece());
		board.recordPosition();
		assertTrue( ! board.isThirdOccuranceOfPosition() );
		board.getSquare("e2").setOccupant(PieceFactory.createPiece(PieceGlobals.WHITE, PieceGlobals.PAWN));
		board.recordPosition();
		assertTrue(board.isThirdOccuranceOfPosition() );
	}

	private String normalSetUpToString =
	"   -------------------------------\n" +
	"8 | r | n | b | q | k | b | n | r |\n" +
	"   -------------------------------\n" +
	"7 | p | p | p | p | p | p | p | p |\n" +
	"   -------------------------------\n" +
	"6 |   |   |   |   |   |   |   |   |\n" +
	"   -------------------------------\n" +
	"5 |   |   |   |   |   |   |   |   |\n" +
	"   -------------------------------\n" +
	"4 |   |   |   |   |   |   |   |   |\n" +
	"   -------------------------------\n" +
	"3 |   |   |   |   |   |   |   |   |\n" +
	"   -------------------------------\n" +
	"2 | P | P | P | P | P | P | P | P |\n" +
	"   -------------------------------\n" +
	"1 | R | N | B | Q | K | B | N | R |\n" +
	"   -------------------------------\n" +
	"    a   b   c   d   e   f   g   h\n\n";
}
