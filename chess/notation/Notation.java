package chess.notation;

import chess.move.AbstractMove;
import chess.board.ChessBoard;
import chess.player.Player;

public interface Notation
{
    public String getNotation(AbstractMove move);
    public AbstractMove getMove(String value, ChessBoard board, Player player);
}
