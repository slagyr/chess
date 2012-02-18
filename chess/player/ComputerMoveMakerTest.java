package chess.player;

import junit.framework.*;
import chess.gui.MoveEventListener;
import chess.move.*;
import chess.scoring.*;
import chess.player.*;
import chess.piece.PieceGlobals;
import chess.board.ChessBoard;

public class ComputerMoveMakerTest extends TestCase implements MoveEventListener
{
    private AbstractMove move = new IllegalMove();

	public ComputerMoveMakerTest(String name)
	{
		super(name);
	}

    public void acceptMove(AbstractMove m)
    {
        move = m;
    }

	public void testMakeMoveWithNormalSetup()
	{
		Scorer scorer = new CoverageScorer();
		MoveFinder moveFinder = new MoveFinder(scorer, 2);
		MoveMaker moveMaker = new ComputerMoveMaker(moveFinder, this);
		Player player1 = new Player(PieceGlobals.WHITE, moveMaker);
		Player player2 = new Player(PieceGlobals.BLACK);
		player1.setOpponent(player2);
		player2.setOpponent(player1);
		ChessBoard board = new ChessBoard();
		board.normalSetup();
		player1.setBoard(board);
		player1.loadPieces();
		player2.setBoard(board);
		player2.loadPieces();
		player1.findAllMoves(false);
		player2.findAllMoves(false);
		player1.makeMove();
		assertEquals("d2 d4", move.toString());
	}
}
