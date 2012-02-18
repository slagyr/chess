package chess.move;

import chess.piece.*;
import chess.board.SquareInterface;

public class PromotionMove extends Move
{
	private Piece itsPromotion;

	public PromotionMove(Piece piece, SquareInterface start, SquareInterface end, Piece taken)
	{
		super(piece, start, end, taken);
		itsType = MoveGlobals.PROMOTION;
		itsPromotion = new MockPiece();
	}
	
	public PromotionMove(Piece piece, SquareInterface start, SquareInterface end)
	{
		super(piece, start, end);
		itsType = MoveGlobals.PROMOTION;
		itsPromotion = new MockPiece();
	}
	
	public Piece getPromotion()
	{
		return itsPromotion;
	}
	
	public void setPromotion(Piece piece)
	{
		itsPromotion = piece;
	}
	
	public String toString()
	{
		StringBuffer buffer = new StringBuffer(super.toString());
		buffer.append("(").append(itsPromotion.getTextType()).append(")");
		return buffer.toString();
	}
	
	public void makePromotionPiece(boolean pickPiece)
	{
		if(!pickPiece)
			itsPromotion = PieceFactory.createPiece(itsPiece.getColor(), PieceGlobals.QUEEN);
		else
		{
			System.out.println("Choose promotion piece:");
			System.out.println("1: Queen");
			System.out.println("2: Rook");
			System.out.println("3: Bishop");
			System.out.println("4: Knight");
			while (!itsPromotion.isValid())
			{
				String resp = getInput();
				char choice = resp.charAt(0);
				if(choice == '1')
					itsPromotion = PieceFactory.createPiece(itsPiece.getColor(), PieceGlobals.QUEEN);
				else if(choice == '2')
					itsPromotion = PieceFactory.createPiece(itsPiece.getColor(), PieceGlobals.ROOK);
				else if(choice == '3')
					itsPromotion = PieceFactory.createPiece(itsPiece.getColor(), PieceGlobals.BISHOP);
				else if(choice == '4')
					itsPromotion = PieceFactory.createPiece(itsPiece.getColor(), PieceGlobals.KNIGHT);
			}
			
		}		
	}
	
	private String getInput()
	{
		byte[] input = new byte[100];
		try
		{
			for(int i = 0; i < 100; i++)
			{
				System.in.read(input, i, 1);
				if(input[i] == '\n')
					break;
			}
		}
		catch (Exception e)
		{
			System.err.println(e);
			return "";
		}
		return new String(input);
	}
}
