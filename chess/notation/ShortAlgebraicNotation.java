package chess.notation;

import chess.move.*;
import chess.piece.*;
import chess.board.*;
import chess.player.Player;

import java.util.*;

public class ShortAlgebraicNotation implements Notation
{
    public String getNotation(AbstractMove move)
    {
        StringBuffer buff = new StringBuffer();
        if(move.getType().equals(MoveGlobals.RESIGN))
            buff.append(MoveGlobals.RESIGN);
        else if(move.getType().equals(MoveGlobals.KING_SIDE_CASTLE) || move.getType().equals(MoveGlobals.QUEEN_SIDE_CASTLE))
            buff.append(move.toString());
        else if(move.isValid())
        {
            if(move.getPiece().getType().equals(PieceGlobals.PAWN))
                buff.append(getPawnPrefix(move));
            else
                buff.append(getPiecePrefix(move));

            if(move.getTakenPiece().isValid())
                buff.append(takes());
            buff.append(move.getEndSquare().toString());
            buff.append(promotionPiece(move));
        }
        else
            buff.append(move.getType());

        if(move.endOfGame() && move.getEndOfGame().equals(MoveGlobals.MATE))
            buff.append("++");
        else if(move.isCheck())
            buff.append('+');
        return buff.toString();
    }

    public AbstractMove getMove(String givenValue, ChessBoard board, Player player)
    {
        String value = givenValue.trim();
        AbstractMove move = new IllegalMove();
        try
        {
            if(value.indexOf("resign") != -1)
                move = new ResignMove(player.getKing());
            else if(value.startsWith("0-0"))
                move = handleCastles(value, board, player);
            else
            {
                String pieceType = findPieceType(value);
                SquareInterface endSquare = findEndSquare(value, board);
                char distinguishingFile = findDistinguishingFile(value);
                char distinguishingRank = findDistinguishingRank(value);
                String promotion = findPromotionType(pieceType, value);
                move = buildMove(pieceType, endSquare, distinguishingFile, distinguishingRank, promotion, player);
            }
        }
        catch(Exception e)
        {
        }
        return move;
    }

    private AbstractMove buildMove(String type, SquareInterface endSquare, char fileDistinguisher,
                                   char rankDistinguisher, String promoType, Player player) throws Exception
    {
        AbstractMove move = new IllegalMove();
        Collection pieces = player.getPieces();
        Collection validPieces = new ArrayList();

        loadValidPieces(pieces, type, endSquare, validPieces);
        Piece movingPiece = singleOutPiece(validPieces, fileDistinguisher, rankDistinguisher);
        move = createMove(promoType, movingPiece, endSquare, player);
        return move;
    }

    private AbstractMove createMove(String promoType, Piece movingPiece, SquareInterface endSquare, Player player) throws Exception
    {
        AbstractMove move;
        if(promoType != null)
        {
            PromotionMove promoMove = new PromotionMove(movingPiece, movingPiece.getLocation(), endSquare);
            promoMove.setPromotion(PieceFactory.createPiece(player.getColor(), promoType));
            move = promoMove;
        }
        else
            move = new Move(movingPiece, movingPiece.getLocation(), endSquare);
        return move;
    }

    private Piece singleOutPiece(Collection validPieces, char fileDistinguisher, char rankDistinguisher) throws Exception
    {
        for(Iterator i = validPieces.iterator(); i.hasNext();)
        {
            Piece p = (Piece)i.next();
            if( (fileDistinguisher != '*' && p.getLocation().getPrintableFile() != fileDistinguisher) ||
                (rankDistinguisher != '*' && !(p.getLocation().getPrintableRank() + "").equals(rankDistinguisher + "")))
                i.remove();
        }
        return (Piece)validPieces.iterator().next();
    }

    private void loadValidPieces(Collection pieces, String type, SquareInterface endSquare, Collection validPieces) throws Exception
    {
        for(Iterator i = pieces.iterator(); i.hasNext();)
        {
            Piece p = (Piece)i.next();
            if(p.getType().equals(type) && p.canMoveTo(endSquare))
                validPieces.add(p);
        }
    }

    private String findPieceType(String value)
    {
        String type = PieceGlobals.PAWN;
        if(value.startsWith("K"))
            type = PieceGlobals.KING;
        else if(value.startsWith("Q"))
            type = PieceGlobals.QUEEN;
        else if(value.startsWith("R"))
            type = PieceGlobals.ROOK;
        else if(value.startsWith("B"))
            type = PieceGlobals.BISHOP;
        else if(value.startsWith("N"))
            type = PieceGlobals.KNIGHT;
        return type;
    }

    private SquareInterface findEndSquare(String value, ChessBoard board)
    {
        SquareInterface square = new EmptySquare("ShortAlgebraicNotation");
        for(int i = value.length() - 1; i >= 0; i--)
        {
            char c = value.charAt(i);
            if(isRank(c))
            {
                square = board.getSquare(value.charAt(i-1) + "" + c);
                break;
            }
        }
        return square;
    }

    private char findDistinguishingFile(String value)
    {
        char distinguisher = '*';
        boolean destFileFound = false;
        for(int i = value.length() - 1; i >= 0; i--)
        {
            char c = value.charAt(i);
            if(isFile(c))
            {
                if(destFileFound)
                {
                    distinguisher = c;
                    break;
                }
                destFileFound = true;
            }
        }
        return distinguisher;
    }

    private char findDistinguishingRank(String value)
    {
        char distinguisher = '*';
        boolean destRankFound = false;
        for(int i = value.length() - 1; i >= 0; i--)
        {
            char c = value.charAt(i);
            if(isRank(c))
            {
                if(destRankFound)
                {
                    distinguisher = c;
                    break;
                }
                destRankFound = true;
            }
        }
        return distinguisher;
    }

    private String findPromotionType(String pieceType, String value)
    {
        String promotionType = null;
        if(pieceType.equals(PieceGlobals.PAWN))
        {
            int i = 0;
            for(; i < value.length(); i++)
            {
                if(isPiece(value.charAt(i)))
                {
                    promotionType = findPieceType(value.substring(i));
                    break;
                }
            }
        }
        return promotionType;
    }

    private boolean isRank(char c)
    {
        if(c >= '1' && c <= '8')
            return true;
        return false;
    }

    private boolean isFile(char c)
    {
        if(c >= 'a' && c <= 'h')
            return true;
        return false;
    }

    private boolean isPiece(char c)
    {
        if(c == 'K' || c == 'Q' || c == 'R' || c == 'B' || c == 'N')
            return true;
        return false;
    }

    private AbstractMove handleCastles(String value, ChessBoard board, Player player)
    {
        AbstractMove move = new IllegalMove();
        Piece king = player.getKing();
        SquareInterface square = player.getColor().equals(PieceGlobals.WHITE) ?
            board.getSquare("e1") : board.getSquare("e8");
        if(value.startsWith("0-0-0"))
        {
            SquareInterface endSquare = square.getNeighbor(SquareGlobals.W).getNeighbor(SquareGlobals.W);
            move = new QueenSideCastleMove(king, square, endSquare);
        }
        else
        {
            SquareInterface endSquare = square.getNeighbor(SquareGlobals.E).getNeighbor(SquareGlobals.E);
            move = new KingSideCastleMove(king, square, endSquare);
        }
        return move;
    }

    private String getPawnPrefix(AbstractMove move)
    {
        String prefix = "";
        if(move.getTakenPiece().isValid())
            prefix += move.getStartSquare().getPrintableFile();
        return prefix;
    }

    private String getPiecePrefix(AbstractMove move)
    {
        StringBuffer prefix = new StringBuffer();
        prefix.append(getPieceChar(move.getPiece().getType()));
        prefix.append(distinguishPiece(move));
        return prefix.toString();
    }

    private char takes()
    {
        return 'x';
    }

    private char getPieceChar(String type)
    {
        char c = '?';
        if(type.equals(PieceGlobals.KING))
            c = 'K';
        else if(type.equals(PieceGlobals.QUEEN))
            c = 'Q';
        else if(type.equals(PieceGlobals.ROOK))
            c = 'R';
        else if(type.equals(PieceGlobals.BISHOP))
            c = 'B';
        else if(type.equals(PieceGlobals.KNIGHT))
            c = 'N';
        return c;
    }

    private String distinguishPiece(AbstractMove move)
    {
        String distinguisher = "";
        Piece piece = move.getPiece();
        List challengers = findChallengers(piece, move.getEndSquare());
        if(challengers.size() > 0)
        {
            boolean needRank = false;
            boolean needFile = false;
            SquareInterface location = move.getStartSquare();
            for (Iterator i = challengers.iterator(); i.hasNext();)
            {
                Piece p = (Piece)i.next();
                if(p.getLocation().getRank() == location.getRank())
                    needFile = true;
                if(p.getLocation().getFile() == location.getFile())
                    needRank = true;
            }
            if(needFile)
                distinguisher += location.getPrintableFile();
            if(needRank)
                distinguisher += location.getPrintableRank();
        }
        return distinguisher;
    }

    private List findChallengers(Piece piece, SquareInterface square)
    {
        List challengers = new ArrayList();
        for (Iterator i = square.getAttackers().iterator(); i.hasNext();)
        {
            Piece otherPiece = (Piece)i.next();
            if(otherPiece.getType().equals(piece.getType()) && !otherPiece.equals(piece))
                challengers.add(otherPiece);
        }
        return challengers;
    }

    private String promotionPiece(AbstractMove move)
    {
        String s = "";
        if(move.getType().equals(MoveGlobals.PROMOTION))
        {
            PromotionMove promo = (PromotionMove)move;
            s += getPieceChar(promo.getPromotion().getType());
        }
        return s;
    }
}
