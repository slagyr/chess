package chess;

import junit.framework.*;
import java.io.*;

import chess.game.TestableChessGame;
import chess.player.*;
import chess.piece.*;
import chess.board.*;
import chess.scoring.*;

public class FullTests extends TestCase
{
    private TestableChessGame game;
    private Player player1;
	private Player player2;

	public FullTests(String name)
	{
		super(name);
	}

    public void setUp()
    {
        game = new TestableChessGame();
        MoveMaker moveMaker = new HumanMoveMaker();
        moveMaker.setMoveListener(game);
		player1 = new Player(PieceGlobals.WHITE, moveMaker);
		player2 = new Player(PieceGlobals.BLACK, moveMaker);
    }

	public void testInvalidCastle()
	{
		ChessBoard board = new ChessBoard();
		board.normalSetup();
		playMoves(player1, player2, board, invalidCastle);
		Piece king = board.getSquare("e8").getOccupant();
		assertEquals("black King", king.toString());
	}
	
	public void testClearAttacks()
	{
		ChessBoard board = new ChessBoard();
		board.normalSetup();
		playMoves(player1, player2, board, validCastle);
		Piece king = board.getSquare("g8").getOccupant();
		assertEquals("black King", king.toString());
	}
	
	public void testClearAttacksOfTakenPieces()
	{
		ChessBoard board = new ChessBoard();
		board.normalSetup();
		playMoves(player1, player2, board, validCastle_takenThreat);
		Piece king = board.getSquare("g8").getOccupant();
		assertEquals("black King", king.toString());
	}
	
	public void testScholarsMate()
	{
		ChessBoard board = new ChessBoard();
		board.normalSetup();
		playMoves(player1, player2, board, scholarsMate);
		//Piece king = board.getSquare("g8").getOccupant();
		assertTrue(player1.isInCheckMate());
	}
	
	public void testJakovenko_Cebalo_2001()
	{
		ChessBoard board = new ChessBoard();
		board.normalSetup();
		playMoves(player1, player2, board, Jakovenko_Cebalo_2001);
		assertEquals(board.getSquare("a2"), player1.getKing().getLocation());
		assertEquals(board.getSquare("h6"), player2.getKing().getLocation());
	}
	
	public void testEnpassants()
	{
		ChessBoard board = new ChessBoard();
		board.normalSetup();
		playMoves(player1, player2, board, enpassants);
		Piece whitePawn = board.getSquare("b6").getOccupant();
		Piece blackPawn = board.getSquare("g3").getOccupant();
		assertEquals(PieceGlobals.WHITE, whitePawn.getColor());
		assertEquals(PieceGlobals.PAWN, whitePawn.getType());
		assertEquals(PieceGlobals.BLACK, blackPawn.getColor());
		assertEquals(PieceGlobals.PAWN, blackPawn.getType());
		assertEquals(1, player1.getTakenPieces().size());
		assertEquals(1, player2.getTakenPieces().size());
	}
	
	public void testJakovenko_Cebalo_2001_IllegalMoves()
	{
		ChessBoard board = new ChessBoard();
		board.normalSetup();
		playMoves(player1, player2, board, Jakovenko_Cebalo_2001_illegalMoves);
		assertEquals(board.getSquare("a2"), player1.getKing().getLocation());
		assertEquals(board.getSquare("h6"), player2.getKing().getLocation());
	}
	
	public void testPromotion()
	{
		ChessBoard board = new ChessBoard();
		board.normalSetup();
		playMoves(player1, player2, board, Promotion);
		assertEquals("white Queen", board.getSquare("b8").getOccupant().toString());
	}
	
	public void testLostTrailBug()
	{
		Scorer scorer = new CoverageScorer();
		MoveFinder moveFinder = new MoveFinder(scorer, 2);
		MoveMaker moveMaker = new ComputerMoveMaker(moveFinder, game);
		player2 = new Player(PieceGlobals.BLACK, moveMaker);
		ChessBoard board = new ChessBoard();
		board.normalSetup();
		playMoves(player1, player2, board, lostTrailBug);
	}
	
	public void testCheckFromPromotion()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.a, 7, PieceGlobals.PAWN, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.a, 1, PieceGlobals.KING, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.c, 6, PieceGlobals.KING, PieceGlobals.BLACK);
		playMoves(player1, player2, board, checkFromPromotion);
		assertTrue(player2.isInCheck());
	}
	
	public void testCantMoveIntoOpponentsKingsAttack()
	{
		ChessBoard board = new ChessBoard();
		board.placePiece(SquareGlobals.a, 5, PieceGlobals.KING, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.d, 1, PieceGlobals.ROOK, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.e, 4, PieceGlobals.ROOK, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.g, 7, PieceGlobals.QUEEN, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.d, 8, PieceGlobals.KNIGHT, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.c, 7, PieceGlobals.KNIGHT, PieceGlobals.WHITE);
		
		board.placePiece(SquareGlobals.b, 8, PieceGlobals.KING, PieceGlobals.BLACK);
		playMoves(player1, player2, board, kingMoveIntoCheck);
		assertTrue("doh! king moved into check", player2.isInStaleMate());
	}

	private void playMoves(Player player1, Player player2, ChessBoard board, String moves)
	{
        ByteArrayInputStream input = new ByteArrayInputStream(moves.getBytes());
		System.setIn(input);
		game.setPlayer1(player1);
		game.setPlayer2(player2);
		game.setBoard(board);
		game.startGame();
	}
	
	private String invalidCastle =
	"b2 b3\ne7 e5\n" +
	"e2 e4\nf8 a3\n" + 
	"c1 a3\ng8 h6\n" +
	"h2 h3\ne8 e7\n" +
	"resign\n";
	
	private String validCastle =
	"b2 b3\ne7 e5\n" +
	"e2 e4\nf8 a3\n" + 
	"c1 a3\ng8 h6\n" +
	"a3 b2\ne8 g8\n" +
	"resign\n";
	
	private String validCastle_takenThreat =
	"b2 b3\ne7 e5\n" +
	"e2 e4\ng8 h6\n" + 
	"c1 a3\nf8 a3\n" +
	"h2 h3\ne8 g8\n" +
	"resign\n";
	
	private String scholarsMate =
	"g2 g4\ne7 e6\n" +
	"f2 f3\nd8 h4\n" + 
	"resign\n";
	
	private String enpassants =
	"a2 a4" + "\n" + "h7 h5" + "\n" + //1
	"a4 a5" + "\n" + "b7 b5" + "\n" + //2
	"a5 b6" + "\n" + "h5 h4" + "\n" + //3
	"g2 g4" + "\n" + "h4 g3" + "\n" + //4
	"resign" + "\n";//5
	
	private String Jakovenko_Cebalo_2001 =
	"e2 e4" + "\n" + "c7 c5" + "\n" + //1
	"g1 f3" + "\n" + "d7 d6" + "\n" + //2
	"d2 d4" + "\n" + "c5 d4" + "\n" + //3
	"f3 d4" + "\n" + "g8 f6" + "\n" + //4
	"b1 c3" + "\n" + "g7 g6" + "\n" + //5
	"c1 e3" + "\n" + "f8 g7" + "\n" + //6
	"f2 f3" + "\n" + "e8 g8" + "\n" + //7
	"f1 c4" + "\n" + "b8 c6" + "\n" + //8
	"d1 d2" + "\n" + "c8 d7" + "\n" + //9
	"c4 b3" + "\n" + "d8 a5" + "\n" + //10
	"e1 c1" + "\n" + "f8 c8" + "\n" + //11
	"h2 h4" + "\n" + "c6 e5" + "\n" + //12
	"g2 g4" + "\n" + "b7 b5" + "\n" + //13
	"h4 h5" + "\n" + "e5 c4" + "\n" + //14
	"b3 c4" + "\n" + "b5 c4" + "\n" + //15
	"e3 h6" + "\n" + "g7 h8" + "\n" + //16
	"c1 b1" + "\n" + "a5 b6" + "\n" + //17
	"h5 g6" + "\n" + "f7 g6" + "\n" + //18
	"d2 h2" + "\n" + "a8 b8" + "\n" + //19
	"h6 c1" + "\n" + "h8 g7" + "\n" + //20
	"c3 d5" + "\n" + "f6 d5" + "\n" + //21
	"e4 d5" + "\n" + "h7 h6" + "\n" + //22
	"c2 c3" + "\n" + "c8 f8" + "\n" + //23
	"h2 c2" + "\n" + "g6 g5" + "\n" + //24
	"b1 a1" + "\n" + "f8 f7" + "\n" + //25
	"f3 f4" + "\n" + "d7 g4" + "\n" + //26
	"d1 g1" + "\n" + "g7 d4" + "\n" + //27
	"g1 g4" + "\n" + "d4 g7" + "\n" + //28
	"f4 g5" + "\n" + "f7 f2" + "\n" + //29
	"c2 f2" + "\n" + "b6 f2" + "\n" + //30
	"g5 h6" + "\n" + "f2 f3" + "\n" + //31
	"g4 g7" + "\n" + "g8 h8" + "\n" + //32
	"h1 g1" + "\n" + "b8 f8" + "\n" + //33
	"h6 h7" + "\n" + "f8 f7" + "\n" + //34
	"g7 g8" + "\n" + "h8 h7" + "\n" + //35
	"g8 g3" + "\n" + "f3 h5" + "\n" + //36
	"a2 a3" + "\n" + "a7 a5" + "\n" + //37
	"a1 a2" + "\n" + "a5 a4" + "\n" + //38
	"c1 e3" + "\n" + "h5 h4" + "\n" + //39
	"g1 g2" + "\n" + "h4 h5" + "\n" + //40
	"e3 d4" + "\n" + "h5 h6" + "\n" + //41
	"g3 g8" + "\n" + "f7 f6" + "\n" + //42
	"g2 g4" + "\n" + "h6 d2" + "\n" + //43
	"d4 f6" + "\n" + "e7 f6" + "\n" + //44
	"g4 g7" + "\n" + "h7 h6" + "\n" + //45
	"g7 g2" + "\n" + "resign" + "\n"; //46
	
	private String Jakovenko_Cebalo_2001_illegalMoves =
	"e2 e4" + "\n" + "c7 c5" + "\n" + //1
	"c1 d2" + "\n" + "g1 f3" + "\n" + "d7 d6" + "\n" + //2
	"d2 d4" + "\n" + "c5 d4" + "\n" + //3
	"e4 d4" + "\n" + "f3 d4" + "\n" + "g8 f6" + "\n" + //4
	"b1 c3" + "\n" + "g7 g6" + "\n" + //5
	"c1 e3" + "\n" + "e8 g8" + "\n" + "f8 g7" + "\n" + //6
	"f2 f3" + "\n" + "e8 g8" + "\n" + //7
	"f1 c4" + "\n" + "b8 c6" + "\n" + //8
	"d1 d2" + "\n" + "c8 d7" + "\n" + //9
	"c4 b3" + "\n" + "d8 a5" + "\n" + //10
	"e1 c1" + "\n" + "f8 c8" + "\n" + //11
	"h2 h4" + "\n" + "c6 e5" + "\n" + //12
	"g2 g4" + "\n" + "b7 b5" + "\n" + //13
	"h4 h5" + "\n" + "e5 c4" + "\n" + //14
	"b3 c4" + "\n" + "b5 c4" + "\n" + //15
	"e3 h6" + "\n" + "g7 h8" + "\n" + //16
	"c1 b1" + "\n" + "g8 g7" + "\n" + "a5 b6" + "\n" + //17
	"h5 g6" + "\n" + "f7 g6" + "\n" + //18
	"d2 h2" + "\n" + "a8 b8" + "\n" + //19
	"h6 c1" + "\n" + "b8 b2" + "\n" + "h8 g7" + "\n" + //20
	"c3 d5" + "\n" + "f6 d5" + "\n" + //21
	"e4 d5" + "\n" + "h7 h6" + "\n" + //22
	"c2 c3" + "\n" + "c8 f8" + "\n" + //23
	"h2 c2" + "\n" + "g6 g5" + "\n" + //24
	"b1 a1" + "\n" + "f8 f7" + "\n" + //25
	"f3 f4" + "\n" + "d7 g4" + "\n" + //26
	"d1 g1" + "\n" + "g7 d4" + "\n" + //27
	"g1 g4" + "\n" + "d4 g7" + "\n" + //28
	"f4 g5" + "\n" + "f7 f2" + "\n" + //29
	"c2 f2" + "\n" + "b6 f2" + "\n" + //30
	"g5 h6" + "\n" + "f2 f3" + "\n" + //31
	"g4 g7" + "\n" + "g8 h8" + "\n" + //32
	"h1 g1" + "\n" + "b8 f8" + "\n" + //33
	"h6 h7" + "\n" + "f8 f7" + "\n" + //34
	"g7 g8" + "\n" + "h8 h7" + "\n" + //35
	"g8 g3" + "\n" + "f3 h5" + "\n" + //36
	"a2 a3" + "\n" + "a7 a5" + "\n" + //37
	"a1 a2" + "\n" + "a5 a4" + "\n" + //38
	"c1 e3" + "\n" + "h5 h4" + "\n" + //39
	"g1 g2" + "\n" + "h4 h5" + "\n" + //40
	"e3 d4" + "\n" + "h5 h6" + "\n" + //41
	"g3 g8" + "\n" + "f7 f6" + "\n" + //42
	"g2 g4" + "\n" + "h6 d2" + "\n" + //43
	"d4 f6" + "\n" + "e7 f6" + "\n" + //44
	"g4 g7" + "\n" + "h7 h6" + "\n" + //45
	"g7 g2" + "\n" + "resign" + "\n"; //46
	
	
	private String lostTrailBug =
	"e2 e4\n" +
	"e4 d5\n" + 
	"b1 c3\n" +
	"d1 e2\n" +
	"resign";
	
	private String Promotion =
	"c2 c4" + "\n" + "b7 b5" + "\n" + //1
	"c4 b5" + "\n" + "a7 a6" + "\n" + //2
	"b5 a6" + "\n" + "h7 h6" + "\n" + //3
	"a6 a7" + "\n" + "h6 h5" + "\n" + //4
	"a7 b8" + "\n" + "1" + "\n" + 
	"resign" + "\n";			   //5
	
	private String checkFromPromotion =
	"a7 a8" + "\n" + "1" + "\n" +
	"resign" + "\n";
	
	private String kingMoveIntoCheck =
	"a5 a6" + "\n" + "b8 c8" + "\n" +
	"a6 a7" + "\n" + "c8 b8" + "\n" +
	"resign" + "\n";
	
}
