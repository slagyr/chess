package chess.game;

import chess.game.AbstractChessGame;
import chess.player.Player;
import chess.board.ChessBoard;
import chess.move.AbstractMove;

public class TestableChessGame extends AbstractChessGame
{
    public TestableChessGame()
    {
        super();
    }

    public void setPlayer1(Player player)
    {
        itsPlayer1 = player;
    }

    public void setPlayer2(Player player)
    {
        itsPlayer2 = player;
    }

    public void setBoard(ChessBoard board)
    {
        itsBoard = board;
    }

    protected void createPlayer1()
    {
    }

    protected void createPlayer2()
    {
    }

    protected void preGameDetails()
    {
    }

    protected void postGameDetails()
    {
    }

    protected void postMoveDetails(AbstractMove move)
    {
    }

    protected void preMoveDetails()
    {
    }

    protected void setupBoard()
    {
    }

	public void startGame()
	{
        prepareGame();
		play();
	}
}
