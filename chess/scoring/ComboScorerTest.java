package chess.scoring;

import junit.framework.*;
import chess.player.Player;
import chess.board.*;
import chess.scoring.*;
import chess.util.Props;
import chess.piece.PieceGlobals;

public class ComboScorerTest extends TestCase
{
	private Player player1;
	private Player player2;
	private ChessBoard board;
	private ComboScorer scorer;

	public ComboScorerTest(String name)
	{
		super(name);
	}
	
	public void setUp()
	{
		Props.set("center.value", "5", "values.properties");
		Props.set("semiCenter.value", "3", "values.properties");
		Props.set("nonCenter.value", "1", "values.properties");
		Props.set("queen.value", "9", "values.properties");
		Props.set("rook.value", "5", "values.properties");
		Props.set("bishop.value", "3", "values.properties");
		Props.set("knight.value", "2.5", "values.properties");
		Props.set("pawn.value", "1", "values.properties");
		player1 = new Player(PieceGlobals.WHITE);
		player2 = new Player(PieceGlobals.BLACK);
		board = new ChessBoard();
		player1.setBoard(board);
		player1.setOpponent(player2);
		player2.setBoard(board);
		player2.setOpponent(player1);
 		scorer = new ComboScorer();
		scorer.addScorer(new MaterialScorer(), 2);
		scorer.addScorer(new CoverageScorer(), 1);
	}
	
	public void testStartingPosistion()
	{
		board.normalSetup();
		player1.loadPieces();
		player2.loadPieces();
		player1.findAllMoves(false);
		player2.findAllMoves(false);
		
		assertEquals(500, scorer.getScore(board, player1));
		assertEquals(500, scorer.getScore(board, player2));
	}
	
	public void testUnevenPosition()
	{
		board.placePiece(SquareGlobals.a, 1, PieceGlobals.ROOK, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.a, 2, PieceGlobals.PAWN, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.a, 8, PieceGlobals.BISHOP, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.a, 7, PieceGlobals.PAWN, PieceGlobals.BLACK);
		
		player1.loadPieces();
		player2.loadPieces();
		player1.findAllMoves(false);
		player2.findAllMoves(false);
		
		assertEquals(503, scorer.getScore(board, player1));
		assertEquals(496, scorer.getScore(board, player2));
	}
	
	public void testUnevenPosition2()
	{
		board.placePiece(SquareGlobals.a, 1, PieceGlobals.ROOK, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.a, 2, PieceGlobals.PAWN, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.a, 3, PieceGlobals.QUEEN, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.a, 4, PieceGlobals.PAWN, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.a, 5, PieceGlobals.BISHOP, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.a, 6, PieceGlobals.KNIGHT, PieceGlobals.WHITE);
		
		board.placePiece(SquareGlobals.b, 1, PieceGlobals.BISHOP, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.b, 2, PieceGlobals.PAWN, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.b, 3, PieceGlobals.ROOK, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.b, 4, PieceGlobals.ROOK, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.b, 5, PieceGlobals.PAWN, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.b, 6, PieceGlobals.PAWN, PieceGlobals.BLACK);
		
		player1.loadPieces();
		player2.loadPieces();
		player1.findAllMoves(false);
		player2.findAllMoves(false);
		
		assertEquals(452, scorer.getScore(board, player1));
		assertEquals(546, scorer.getScore(board, player2));
	}
}
