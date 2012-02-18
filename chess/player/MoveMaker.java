package chess.player;

import chess.gui.MoveEventListener;
import chess.board.ChessBoard;

public abstract class MoveMaker
{
    protected MoveEventListener itsMoveListener;

	public abstract void makeMove(ChessBoard board, Player player);

    public void setMoveListener(MoveEventListener listener)
    {
        itsMoveListener = listener;
    }
}
