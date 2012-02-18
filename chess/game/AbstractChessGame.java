package chess.game;

import chess.*;
import chess.piece.*;
import chess.move.*;
import chess.board.ChessBoard;
import chess.player.Player;
import chess.gui.MoveEventListener;
public abstract class AbstractChessGame implements MoveEventListener
{
    protected static Player itsPlayer1;
    protected static Player itsPlayer2;
    protected static ChessBoard itsBoard;
    protected static AbstractMove move = new IllegalMove();
    protected static Piece taken = new MockPiece();
    protected static boolean check = false;

    public AbstractChessGame()
    {
        itsPlayer1 = null;
        itsPlayer2 = null;
        itsBoard = null;
    }

    public Player getPlayer1()
    {
        return itsPlayer1;
    }

    public Player getPlayer2()
    {
        return itsPlayer2;
    }

    public void setBoard(ChessBoard board)
    {
        itsBoard = board;
    }

    public ChessBoard getBoard()
    {
        return itsBoard;
    }

    public void createBoard()
    {
        if(itsBoard == null)
            itsBoard = new ChessBoard();
    }

    protected abstract void createPlayer1();
    protected abstract void createPlayer2();
    protected abstract void preGameDetails();
    protected abstract void postGameDetails();
    protected abstract void postMoveDetails(AbstractMove move);
    protected abstract void preMoveDetails();

    protected void setupBoard()
    {
        itsBoard.normalSetup();
    }

    public void showBoard()
    {
        System.out.println(itsBoard.toString());
    }

    public void prepareGame()
    {
        createPlayer1();
        createPlayer2();
        createBoard();
        setupBoard();

        itsPlayer1.setBoard(itsBoard);
        itsPlayer1.loadPieces();
        itsPlayer2.setBoard(itsBoard);
        itsPlayer2.loadPieces();
        itsPlayer1.setOpponent(itsPlayer2);
        itsPlayer2.setOpponent(itsPlayer1);
        itsPlayer1.findAllMoves(false);
        preGameDetails();
    }

    public void newGame()
    {
    }

    public void reviewGame()
    {
    }

    public void play()
    {
        itsState = playersMove;
        whoseMove = itsPlayer1;
        getPlayersMove(whoseMove);
    }

    public void getPlayersMove(Player player)
    {
        preMoveDetails();
        player.makeMove();
    }

    public void acceptMove(AbstractMove move)
    {
        this.move = move;
        whoseMove.dealWithPromotion(move);
        itsState.playerMoved();
    }

    public void checkMove()
    {
        Player player = whoseMove;
        if (! move.isValid())
            itsState.invalidMove();
        else
        {
            check = move.isCheck();
            if (! move.endOfGame())
            {
                player.getOpponent().findAllMoves(check);
                if (player.getOpponent().isInCheck())
                {
                    if (player.getOpponent().isInCheckMate())
                        move.setEndOfGame("Check Mate!");
                }
                else
                {
                    if (player.getOpponent().isInStaleMate())
                        move.setEndOfGame("Stale Mate!");
                }
            }
            itsBoard.recordPosition();
            if(itsBoard.isThirdOccuranceOfPosition() || player.onlyKingsRemain())
                move.setEndOfGame("Draw");
            if(move.endOfGame())
                itsState.gameOver();
            else
                itsState.validMove();
        }
    }

    private State playersMove = new PlayersMoveState();
    private State checkingMove = new CheckingMoveState();
    private State itsState;
    private Player whoseMove;

    private void changeState(State state)
    {
        itsState = state;
    }

    private abstract class State
    {
        public abstract String name();
        public void playerMoved(){System.err.println("State error: playerMoved()");}
        public void validMove(){System.err.println("State error: validMove()");}
        public void invalidMove(){System.err.println("State error: invalidMove()");}
        public void gameOver(){System.err.println("State error: gameOver()");}
    }

    private class PlayersMoveState extends State
    {
        public String name()
        {
            return "PlayersMoveState";
        }

        public void playerMoved()
        {
            changeState(checkingMove);
            checkMove();
        }
    }

    private class CheckingMoveState extends State
    {
        public String name()
        {
            return "CheckingMoveState";
        }

        public void validMove()
        {
            postMoveDetails(move);
            changeState(playersMove);
            whoseMove = whoseMove == itsPlayer1 ? itsPlayer2 : itsPlayer1;
            getPlayersMove(whoseMove);
        }

        public void invalidMove()
        {
            changeState(playersMove);
            getPlayersMove(whoseMove);
        }

        public void gameOver()
        {
            postGameDetails();
        }
    }

}
