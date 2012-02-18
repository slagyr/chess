package chess.player;

import junit.framework.*;
import java.util.*;

import chess.player.*;
import chess.board.ChessBoard;
import chess.scoring.*;
import chess.util.Props;
import chess.piece.PieceGlobals;
import chess.move.AbstractMove;

public class MoveFinderTest extends TestCase
{
	private Player player1;
	private Player player2;
	private ChessBoard board;
	private Scorer scorer;
	private MoveFinder finder;

	public MoveFinderTest(String name)
	{
		super(name);
	}
	
	public void setUp()
	{
		Props.set("center.value", "5", "values.properties");
		Props.set("semiCenter.value", "3", "values.properties");
		Props.set("nonCenter.value", "1", "values.properties");
		player1 = new Player(PieceGlobals.WHITE);
		player2 = new Player(PieceGlobals.BLACK);
		board = new ChessBoard();
		player1.setBoard(board);
		player1.setOpponent(player2);
		player2.setBoard(board);
		player2.setOpponent(player1);
		scorer = new CoverageScorer();
	}
	
	public void testConstructor()
	{
		finder = new MoveFinder(scorer, 1);
		assertSame(scorer, finder.getScorer());
		assertEquals(1, finder.getSearchQuality());
	}
	
	public void testFirstMove()
	{
		finder = new MoveFinder(scorer, 1);
		board.normalSetup();
		player1.loadPieces();
		player2.loadPieces();
		player1.findAllMoves(false);
		player2.findAllMoves(false);
		finder.search(board, player1);
		Collection moves = finder.getBestMoves();
		assertEquals(1, moves.size());
		assertEquals("[d2 d4]", moves.toString());
	}
	
	public void testDepthOf2()
	{
		finder = new MoveFinder(scorer, 2);
		board.normalSetup();
		player1.loadPieces();
		player2.loadPieces();
		player1.findAllMoves(false);
		player2.findAllMoves(false);
		finder.search(board, player1);
		Collection moves = finder.getBestMoves();
		assertEquals(1, moves.size());
		assertEquals("[d2 d4]", moves.toString());
	}
	
	public void _testGetBestMove()
	{
		finder = new MoveFinder(scorer, 3);
		board.normalSetup();
		player1.loadPieces();
		player2.loadPieces();
		player1.findAllMoves(false);
		player2.findAllMoves(false);
		finder.search(board, player1);
		Collection moves = finder.getBestMoves();
		assertEquals(1, moves.size());
		AbstractMove move = finder.getBestMove();
		assertTrue(moves.contains(move));
	}
}
