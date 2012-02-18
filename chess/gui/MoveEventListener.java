package chess.gui;

import chess.move.AbstractMove;

public interface MoveEventListener
{
    public abstract void acceptMove(AbstractMove move);


    public static MoveEventListener prototype =
            new MoveEventListener()
            {
                public void acceptMove(AbstractMove move)
                {
                    //System.err.println("MoveEventListener.prototype used");
                }

            };
}


