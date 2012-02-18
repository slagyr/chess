package chess.player;

import chess.gui.MoveEventListener;
import chess.board.ChessBoard;
import chess.move.*;

public class ComputerMoveMaker extends MoveMaker
{
	private MoveFinder itsMoveFinder;

	public ComputerMoveMaker(MoveFinder finder, MoveEventListener moveListener)
	{
		itsMoveFinder = finder;
        itsMoveListener = moveListener;
	}

	public void makeMove(ChessBoard board, Player player)
	{
		itsMoveFinder.search(board, player);
		AbstractMove move = itsMoveFinder.getBestMove();
		move.getPiece().findMoves_safe();

		move = player.movePiece(move);
		
		if (move.getType().equals(MoveGlobals.PROMOTION))
			((PromotionMove)move).makePromotionPiece(false);

        itsMoveListener.acceptMove(move);
	}
}
