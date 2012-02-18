package chess.player;

import chess.board.ChessBoard;
import chess.piece.Piece;

import java.util.*;

public class HumanPlayer
{
	private ChessBoard itsBoard;
	private String itsColor;
	private HashSet itsPieces;
	
	public HumanPlayer(String color)
	{
		itsColor = color;
		itsBoard = null;
		itsPieces = new HashSet();
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

}
