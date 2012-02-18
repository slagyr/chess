package chess.player;

import chess.board.ChessBoard;
import chess.move.*;

import java.io.*;

public class HumanMoveMaker extends MoveMaker
{
	public void makeMove(ChessBoard board, Player player)
	{
		AbstractMove move;
		System.out.print("move: ");
		String resp = getInput();
		System.out.println("");
		
		if (resp.substring(0, 6).equals("resign"))
			move = new ResignMove(player.getKing());
		else
		{
			String square1 = "" + resp.charAt(0) + resp.charAt(1);
			String square2 = "" + resp.charAt(3) + resp.charAt(4);
			move = player.movePiece(square1, square2);
		}  
		
		if (move.getType().equals(MoveGlobals.PROMOTION))
			((PromotionMove)move).makePromotionPiece(true);

        itsMoveListener.acceptMove(move);
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
