package chess.scoring;

import junit.framework.*;
import chess.player.Player;
import chess.board.ChessBoard;
import chess.scoring.*;
import chess.util.Props;
import chess.piece.PieceGlobals;

public class PieceDevelopmentScorerTest extends TestCase
{
	private Player player1;
	private Player player2;
	private ChessBoard board;
	private Scorer scorer;

	public PieceDevelopmentScorerTest(String name)
	{
		super(name);
	}
	
	public void setUp()
	{
		Props.set("piece.development.value", "5", "values.properties");
		player1 = new Player(PieceGlobals.WHITE);
		player2 = new Player(PieceGlobals.BLACK);
		board = new ChessBoard();
		player1.setBoard(board);
		player1.setOpponent(player2);
		player2.setBoard(board);
		player2.setOpponent(player1); 
		scorer = new PieceDevelopmentScorer();
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
	
	public void testEvenPosistion()
	{
		board.normalSetup();
		player1.loadPieces();
		player2.loadPieces();
		player1.findAllMoves(false);
		player2.findAllMoves(false);
		player1.movePiece("e2", "e4");
		player2.movePiece("e7", "e5");
		player1.findAllMoves(false);
		player2.findAllMoves(false);
		player1.movePiece("d2", "d4");
		player2.movePiece("d7", "d5");
		player1.findAllMoves(false);
		player2.findAllMoves(false);
		player1.movePiece("c1", "d2");
		player2.movePiece("c8", "d7");
		player1.findAllMoves(false);
		player2.findAllMoves(false);
		player1.movePiece("f1", "e2");
		player2.movePiece("f8", "e7");
		player1.findAllMoves(false);
		player2.findAllMoves(false);
		player1.movePiece("b1", "c3");
		player2.movePiece("b8", "c6");
		player1.findAllMoves(false);
		player2.findAllMoves(false);
		player1.movePiece("g1", "f3");
		player2.movePiece("g8", "f6");
		
		assertEquals(500, scorer.getScore(board, player1));
		assertEquals(500, scorer.getScore(board, player2));
	}
	
	public void testUnevenPosistion()
	{
		board.normalSetup();
		player1.loadPieces();
		player2.loadPieces();
		player1.findAllMoves(false);
		player2.findAllMoves(false);
		player1.movePiece("e2", "e4");
		player2.movePiece("e7", "e6");
		player1.findAllMoves(false);
		player2.findAllMoves(false);
		player1.movePiece("d2", "d4");
		player2.movePiece("d7", "d6");
		player1.findAllMoves(false);
		player2.findAllMoves(false);
		player1.movePiece("c1", "d2");
		player2.movePiece("d6", "d5");
		player1.findAllMoves(false);
		player2.findAllMoves(false);
		player1.movePiece("f1", "e2");
		player2.movePiece("e6", "e5");
		player1.findAllMoves(false);
		player2.findAllMoves(false);
		player1.movePiece("b1", "c3");
		player2.movePiece("a7", "a6");
		player1.findAllMoves(false);
		player2.findAllMoves(false);
		player1.movePiece("g1", "f3");
		player2.movePiece("a6", "a5");
		
		assertEquals(954, scorer.getScore(board, player1));
		assertEquals(45, scorer.getScore(board, player2));
	}
}
