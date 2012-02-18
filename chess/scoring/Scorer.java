package chess.scoring;

import chess.board.ChessBoard;
import chess.player.Player;

import java.util.*;

public interface Scorer
{
	public abstract int getScore(ChessBoard board, Player player);

    //used for testing
    public abstract float getValueSum();
}
 