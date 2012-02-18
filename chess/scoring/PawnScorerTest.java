package chess.scoring;

import junit.framework.*;
import chess.player.Player;
import chess.board.*;
import chess.scoring.*;
import chess.util.Props;
import chess.piece.*;

public class PawnScorerTest extends TestCase
{
	private Player player1;
	private Player player2;
	private ChessBoard board;
	private Scorer scorer;

	public PawnScorerTest(String name)
	{
		super(name);
	}
	
	public void setUp()
	{
		Props.set("doubled.pawn.value", "5", "values.properties");
		Props.set("passed.pawn.value", "5", "values.properties");
		Props.set("passed.pawn.promotion.value", "10", "values.properties");
		player1 = new Player(PieceGlobals.WHITE);
		player2 = new Player(PieceGlobals.BLACK);
		board = new ChessBoard();
		player1.setBoard(board);
		player1.setOpponent(player2);
		player2.setBoard(board);
		player2.setOpponent(player1); 
		scorer = new PawnScorer();
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
	
	public void testDoubledPawns()
	{
		board.normalSetup();
		swapPawn("e2", "d3");
		player1.loadPieces();
		player2.loadPieces();
		player1.findAllMoves(false);
		player2.findAllMoves(false);
		
		assertEquals(142, scorer.getScore(board, player1));
		assertEquals(857, scorer.getScore(board, player2));
	}
	
	public void testMultipleDoubledPawns()
	{
		board.normalSetup();
		swapPawn("e2", "d3");
		swapPawn("a2", "b5");
		swapPawn("e7", "d6");
		player1.loadPieces();
		player2.loadPieces();
		player1.findAllMoves(false);
		player2.findAllMoves(false);
		
		assertEquals(352, scorer.getScore(board, player1));
		assertEquals(647, scorer.getScore(board, player2));
	}
	
	public void testPassedPawns()
	{
		Props.set("passed.pawn.promotion.value", "0", "values.properties");
		scorer = new PawnScorer();
		board.placePiece(SquareGlobals.a, 3, PieceGlobals.PAWN, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.b, 4, PieceGlobals.PAWN, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.d, 4, PieceGlobals.PAWN, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.d, 5, PieceGlobals.PAWN, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.f, 5, PieceGlobals.PAWN, PieceGlobals.BLACK);
		player1.loadPieces();
		player2.loadPieces();
		player1.findAllMoves(false);
		player2.findAllMoves(false);
		
		assertEquals(647, scorer.getScore(board, player1));
		assertEquals(352, scorer.getScore(board, player2));
	}
	
	public void testPassedPawnsPromotionDistance()
	{
		Props.set("passed.pawn.value", "0", "values.properties");
		scorer = new PawnScorer();
		board.placePiece(SquareGlobals.a, 3, PieceGlobals.PAWN, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.f, 2, PieceGlobals.PAWN, PieceGlobals.BLACK);
		player1.loadPieces();
		player2.loadPieces();
		player1.findAllMoves(false);
		player2.findAllMoves(false);
		
		assertEquals(177, scorer.getScore(board, player1));
		assertEquals(822, scorer.getScore(board, player2));
	}
	
	private void swapPawn(String square1, String square2)
	{
		SquareInterface s1 = board.getSquare(square1);
		SquareInterface s2 = board.getSquare(square2);
		Piece pawn = s1.getOccupant();
		pawn.setLocation(s2);
		s1.setOccupant(new MockPiece());
		s2.setOccupant(pawn);
	}
}
