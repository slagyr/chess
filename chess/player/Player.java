package chess.player;

import chess.board.*;
import chess.gui.MoveEventListener;
import chess.piece.*;
import chess.move.*;

import java.util.*;

public class Player
{
	private ChessBoard itsBoard;
	private String itsColor;
	private HashSet itsPieces;
	private HashSet itsTakenPieces;
	private Player itsOpponent;
	private MoveMaker itsMoveMaker;
	
	public Player(String color)  // For test use only
	{
		itsColor = color;
		itsBoard = null;
		itsPieces = new HashSet();
		itsTakenPieces = new HashSet();
		itsMoveMaker = new HumanMoveMaker();
        itsMoveMaker.setMoveListener(MoveEventListener.prototype);
	}

	public Player(String color, MoveMaker moveMaker)
	{
		itsColor = color;
		itsBoard = null;
		itsPieces = new HashSet();
		itsTakenPieces = new HashSet();
		itsMoveMaker = moveMaker;
	}

	public String getColor()
	{
		return itsColor;
	}
	
	public void setBoard(ChessBoard board)
	{
		itsBoard = board;
	}
	
	public ChessBoard getBoard()
	{
		return itsBoard;
	}
	
	public void loadPieces()
	{
		if (itsBoard != null)
		{
			Collection pieces = itsBoard.getPiecesOnBoard();
			for(Iterator i = pieces.iterator(); i.hasNext(); )
			{
				Piece piece = (Piece)i.next();
				if(piece.getColor().equals(itsColor))
					itsPieces.add(piece);
			}
		}
	}
	
	public Collection getPieces()
	{
		return itsPieces;
	}
	
	public void losePiece(Piece p)
	{
		p.clearAttacks();
		itsPieces.remove(p);
	}
	
	public Collection getTakenPieces()
	{
		return itsTakenPieces;
	}
	
	public void gainTakenPiece(Piece p)
	{
		itsTakenPieces.add(p);
	}
	
	public void regainLostPiece(Piece p)
	{
		itsPieces.add(p);
	}
	
	public void findAllMoves(boolean check)
	{	
		for(Iterator i = itsPieces.iterator(); i.hasNext(); )
		{
			Piece piece = (Piece)i.next();
			if(check)
				piece.findMovesOutOfCheck(getKing());
			else	
				piece.findMoves();
		}
	}
	
	public void findAllMoves_safe()
	{	
		for(Iterator i = itsPieces.iterator(); i.hasNext(); )
		{
			Piece piece = (Piece)i.next();
			piece.findMoves_safe();
		}
	}
	
	public boolean hasPieceOn(SquareInterface square)
	{
		Piece occupant = square.getOccupant();
		return occupant.isValid() ? itsPieces.contains(occupant) : false;
	}
	
	public AbstractMove movePiece(String fromSquare, String toSquare)
	{
		AbstractMove move = new IllegalMove();
		SquareInterface square1 = itsBoard.getSquare(fromSquare);
		if (square1.isValid())
		{
			Piece piece = square1.getOccupant();
			if (piece.isValid() && piece.getColor().equals(itsColor))
			{
				SquareInterface square2 = itsBoard.getSquare(toSquare);
				move = movePiece(piece, square1, square2);
			}
		}
		return move;
	}
	
	public AbstractMove movePiece(AbstractMove move)
	{
		SquareInterface square1 = move.getStartSquare();
		SquareInterface square2 = move.getEndSquare();
		Piece piece = move.getPiece();
		return movePiece(piece, square1, square2);
	}
	
	public AbstractMove movePiece(Piece piece, SquareInterface square1, SquareInterface square2)
	{
		AbstractMove move = new IllegalMove(piece, square1, square2);
		if (piece.isValid() && piece.getLocation() == square1 && piece.canMoveTo(square2))
		{
			move = piece.makeMove(square2);
			dealWithTakenPiece(move.getTakenPiece());
			findAllMoves(false);
			if(isCheck())
				move.check();
		}
		return move;
	}
	
	public Piece getKing()
	{
		Piece king = new MockPiece();
		for(Iterator i = itsPieces.iterator(); i.hasNext(); )
		{
			Piece piece = (Piece)i.next();
			if (PieceGlobals.KING.equals(piece.getType()))
			{
				king = piece;
				break;
			}
		}
		return king;
	}
	
	public boolean isCheck()
	{
		boolean check = false;
		for(Iterator i = itsPieces.iterator(); i.hasNext(); )
		{
			Piece piece = (Piece)i.next();
			if(piece.isCheck())
			{
				check = true;
				break;
			}
		}
		return check;
	}
	
	public boolean isInCheck()
	{
		boolean returnValue = false;
		Piece king = getKing();
		if (king.isAttacked())
			returnValue = true;
		return returnValue;	
	}
	
	public boolean isInCheckMate()
	{
		return (isInCheck() && hasNoMoves());
	}
	
	public boolean isInStaleMate()
	{
		return (! isInCheck() && hasNoMoves());
	}
	
	private boolean hasNoMoves()
	{
		boolean returnValue = true;
		for(Iterator i = itsPieces.iterator(); i.hasNext(); )
		{
			Piece piece = (Piece)i.next();
			if (piece.getMoveOptions().size() != 0)
			{
				returnValue = false;
				break;
			}
		}
		return returnValue;				 
	}
	
//	public AbstractMove makeMove()
//	{
//		AbstractMove move = itsMoveMaker.makeMove(itsBoard, this);
//        dealWithPromotion(move);
//        return move;
//	}

    public void makeMove()
    {
        itsMoveMaker.makeMove(itsBoard, this);
    }

    public void dealWithPromotion(AbstractMove move)
    {
        if (move.getType().equals(MoveGlobals.PROMOTION))
        {
            PromotionMove promo = (PromotionMove)move;
            Piece promoPiece = promo.getPromotion();
            promoPiece.getTrail().clear();
            promoPiece.setLocation(promo.getPiece().getLocation());
            promo.getEndSquare().setOccupant(promoPiece);
            itsPieces.remove(promo.getPiece());
            itsPieces.add(promo.getPromotion());
            promoPiece.findMoves();
            if(isCheck())
                move.check();
        }
    }

    public void reverseMove(AbstractMove move)
	{
		move.getPiece().reverseMove(move);
		Piece taken = move.getTakenPiece();
		if (taken.isValid())
		{
			itsTakenPieces.remove(taken);
			itsOpponent.regainLostPiece(taken);
		}
		if (move.getType().equals(MoveGlobals.PROMOTION))
		{
			PromotionMove promo = (PromotionMove)move;
            promo.getPromotion().clearAttacks();
			itsPieces.remove(promo.getPromotion());
			itsPieces.add(promo.getPiece());
		}
	}
	
	public void setOpponent(Player opponent)
	{
		itsOpponent = opponent;
	}
	
	public Player getOpponent()
	{
		return itsOpponent;
	}

    public boolean onlyKingsRemain()
    {
        return (itsPieces.size() == 1 && itsOpponent.getPieces().size() == 1);
    }

	private void dealWithTakenPiece(Piece piece)
	{
		if (piece.isValid())
		{
			gainTakenPiece(piece);
			itsOpponent.losePiece(piece);
		}
	}
}
