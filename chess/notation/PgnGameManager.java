package chess.notation;

import chess.board.ChessBoard;
import chess.player.Player;
import chess.move.*;
import chess.piece.PieceGlobals;
import java.util.List;
import java.util.ArrayList;

public class PgnGameManager extends PgnGame
{
    private ChessBoard board;
    private Player white;
    private Player black;
    private boolean check = false;

    private boolean isValid = true;
    private Notation notation;
    private String error;
    private int moveIndex = -1;
    private List moves;
    private boolean existingGame;

    public PgnGameManager(String data, Notation notation)
    {
        super(data);
        existingGame = true;
        moves = new ArrayList();
        this.notation = notation;
        buildMoves();
    }

    public PgnGameManager(Notation notation)
    {
       super();
       existingGame = false;
       moves = new ArrayList();
       this.notation = notation;
       board = new ChessBoard();
    }

    public void setGameData(String gameData)
    {
        existingGame = true;
        super.setGameData(gameData);
        buildMoves();
    }

    public boolean existingGame()
    {
        return existingGame;
    }

    public Player getWhitePlayer()
    {
        return white;
    }

    public Player getBlackPlayer()
    {
        return black;
    }

    public ChessBoard getBoard()
    {
        return board;
    }

    public boolean isValid()
    {
        return isValid;
    }

    public String getError()
    {
        return error;
    }

    public int getIndex()
    {
        return moveIndex;
    }

    public AbstractMove getMove(int index)
    {
        AbstractMove move = new IllegalMove();
        if(moves.size() > (index - 1))
            move = (AbstractMove)moves.get(index - 1);
        return move;
    }

    public AbstractMove getWhiteMove(int index)
    {
        int i = (index * 2) - 1;
        return getMove(i);
    }

    public AbstractMove getBlackMove(int index)
    {
        int i = index * 2;
        return getMove(i);
    }

    public AbstractMove getNextMove()
    {
        moveIndex++;
        AbstractMove move = getCurrentMove();
        if(!move.isValid())
            moveIndex--;
        return move;
    }

    public AbstractMove getPreviousMove()
    {
        moveIndex--;
        AbstractMove move = getCurrentMove();
        if(moveIndex < -1)
            moveIndex++;
        return move;
    }

    public AbstractMove getCurrentMove()
    {
        if(moveIndex >= 0 && moveIndex < moves.size())
            return (AbstractMove)moves.get(moveIndex );
        else
            return new IllegalMove();
    }

    public AbstractMove getCurrentMove_bumpBack()
    {
        AbstractMove move = getCurrentMove();
        getPreviousMove();
        return move;
    }

    public String[] getMovesInNotation(String color)
    {
        int size = moves.size();
        int arraySize = size / 2;
        int extraMove = arraySize == 0 ? 0 : size % arraySize;
        arraySize += extraMove;
        int movesIndex = 0;
        if(color.equals(PieceGlobals.BLACK))
            movesIndex = 1;
        String[] returnValue = new String[arraySize];
        int arrayIndex = 0;
        for(; movesIndex < moves.size(); movesIndex += 2)
            returnValue[arrayIndex++] = notation.getNotation((AbstractMove)moves.get(movesIndex));

        return returnValue;
    }

    public void addMove(AbstractMove move)
    {
        boolean whiteMove = move.getPiece().getColor().equals(PieceGlobals.WHITE);
        String moveStr = notation.getNotation(move);
        if(whiteMove)
            addWhiteMove(moveStr);
        else
            addBlackMove(moveStr);
    }

    private void buildMoves()
    {
        white = new Player(PieceGlobals.WHITE);
        black = new Player(PieceGlobals.BLACK);
        board = new ChessBoard();
        board.normalSetup();
        white.setBoard(board);
        white.setOpponent(black);
        white.loadPieces();
        white.findAllMoves(false);
        black.setBoard(board);
        black.setOpponent(white);
        black.loadPieces();
        black.findAllMoves(false);

        String currentMove = null;
        for(int i = 0; i < whitesMoves.size(); i++)
        {
            if(! performMove((String)whitesMoves.get(i), board, white))
                break;
            if(blacksMoves.size() > i)
            {
                if(! performMove((String)blacksMoves.get(i), board, black))
                    break;
            }
        }
        reverseMoves();
    }

    private boolean performMove(String moveStr, ChessBoard board, Player player)
    {
        player.findAllMoves(check);
        AbstractMove move = notation.getMove(moveStr, board, player);
        if(! move.isValid())
        {
            error = moveStr;
            isValid = false;
        }
        AbstractMove recordedMove = player.movePiece(move);
        dealWithPromotion(move, recordedMove);
        moves.add(recordedMove);
        check = recordedMove.isCheck();
        player.dealWithPromotion(move);
        player.findAllMoves(false);
        return isValid;
    }

    private void reverseMoves()
    {
        for(int i = moves.size() - 1; i >= 0; i--)
        {
            int remainder = i % 2;
            Player player = remainder == 0 ? white : black;
            player.reverseMove((AbstractMove)moves.get(i));
        }
    }

    private void dealWithPromotion(AbstractMove move, AbstractMove recordedMove)
    {
        if(recordedMove.getType().equals(MoveGlobals.PROMOTION) && move.getType().equals(MoveGlobals.PROMOTION))
            ((PromotionMove)recordedMove).setPromotion(((PromotionMove)move).getPromotion());
    }
}
