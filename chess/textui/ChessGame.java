package chess.textui;

import chess.game.AbstractChessGame;
import chess.player.*;
import chess.piece.PieceGlobals;
import chess.scoring.*;
import chess.move.AbstractMove;

public class ChessGame extends AbstractChessGame
{
    public ChessGame()
    {
        super();
    }

    protected void createPlayer1()
    {
        MoveMaker moveMaker = new HumanMoveMaker();
        moveMaker.setMoveListener(this);
		itsPlayer1 = new Player(PieceGlobals.WHITE, moveMaker);
    }

    protected void createPlayer2()
    {
		Scorer scorer = new CoverageScorer();
		MoveFinder moveFinder = new MoveFinder(scorer, 2);
		MoveMaker moveMaker = new ComputerMoveMaker(moveFinder, this);
		itsPlayer2 = new Player(PieceGlobals.BLACK, moveMaker);
    }

    protected void preGameDetails()
    {
        System.out.println(itsBoard);
    }

    protected void postGameDetails()
    {
        System.out.println(move.getEndOfGame());
    }

    protected void postMoveDetails(AbstractMove move)
    {
        if(move.isCheck())
            System.out.println("check");
        System.out.println(itsBoard);
    }

    protected void preMoveDetails()
    {
    }

	public static void main(String[] args)
	{
        ChessGame game = new ChessGame();
        game.prepareGame();
		game.play();
	}
}
