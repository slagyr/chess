package chess.simulation;

import chess.game.AbstractChessGame;
import chess.move.AbstractMove;

public abstract class SimulationGame extends AbstractChessGame
{
    protected abstract void createPlayer1();
    protected abstract void createPlayer2();
    protected abstract void preGameDetails();
    protected abstract void postGameDetails();
    protected abstract void postMoveDetails(AbstractMove move);
    protected abstract void preMoveDetails();

}
