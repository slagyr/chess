package chess.board;

import chess.piece.*;
import java.util.*;

public class ChessBoard
{
	ArrayList theBoard;
	ArrayList itsPositionRecord;

	public ChessBoard()
	{
		makeBoard();
		loadBoard();
		setNeighbors();
		itsPositionRecord = new ArrayList();
	}
	
	public SquareInterface getSquare(int file, int rank)
	{
		rank = rank - 1;
		return (SquareInterface)((ArrayList)theBoard.get(file)).get(rank);
	}
	
	public SquareInterface getSquare(String squareName)
	{
		SquareInterface square = new EmptySquare("ChessBoard");
		if (squareName.length() == 2)
		{
			try
			{
				int file = charToFile(squareName.charAt(0));
				int rank;
				rank = new Integer(squareName.charAt(1) + "").intValue();	
				square = getSquare(file, rank);
			}
			catch (Exception e)
			{
				System.err.println("invalid square: " + squareName);
				System.err.println(e);
			}
		}
		return square;
	}
	
	public void normalSetup()
	{
		placePiece(SquareGlobals.a, 1, PieceGlobals.ROOK, PieceGlobals.WHITE);
		placePiece(SquareGlobals.b, 1, PieceGlobals.KNIGHT, PieceGlobals.WHITE);
		placePiece(SquareGlobals.c, 1, PieceGlobals.BISHOP, PieceGlobals.WHITE);
		placePiece(SquareGlobals.d, 1, PieceGlobals.QUEEN, PieceGlobals.WHITE);
		placePiece(SquareGlobals.e, 1, PieceGlobals.KING, PieceGlobals.WHITE);
		placePiece(SquareGlobals.f, 1, PieceGlobals.BISHOP, PieceGlobals.WHITE);
		placePiece(SquareGlobals.g, 1, PieceGlobals.KNIGHT, PieceGlobals.WHITE);
		placePiece(SquareGlobals.h, 1, PieceGlobals.ROOK, PieceGlobals.WHITE);
		
		placePiece(SquareGlobals.a, 2, PieceGlobals.PAWN, PieceGlobals.WHITE);
		placePiece(SquareGlobals.b, 2, PieceGlobals.PAWN, PieceGlobals.WHITE);
		placePiece(SquareGlobals.c, 2, PieceGlobals.PAWN, PieceGlobals.WHITE);
		placePiece(SquareGlobals.d, 2, PieceGlobals.PAWN, PieceGlobals.WHITE);
		placePiece(SquareGlobals.e, 2, PieceGlobals.PAWN, PieceGlobals.WHITE);
		placePiece(SquareGlobals.f, 2, PieceGlobals.PAWN, PieceGlobals.WHITE);
		placePiece(SquareGlobals.g, 2, PieceGlobals.PAWN, PieceGlobals.WHITE);
		placePiece(SquareGlobals.h, 2, PieceGlobals.PAWN, PieceGlobals.WHITE);
		
		placePiece(SquareGlobals.a, 8, PieceGlobals.ROOK, PieceGlobals.BLACK);
		placePiece(SquareGlobals.b, 8, PieceGlobals.KNIGHT, PieceGlobals.BLACK);
		placePiece(SquareGlobals.c, 8, PieceGlobals.BISHOP, PieceGlobals.BLACK);
		placePiece(SquareGlobals.d, 8, PieceGlobals.QUEEN, PieceGlobals.BLACK);
		placePiece(SquareGlobals.e, 8, PieceGlobals.KING, PieceGlobals.BLACK);
		placePiece(SquareGlobals.f, 8, PieceGlobals.BISHOP, PieceGlobals.BLACK);
		placePiece(SquareGlobals.g, 8, PieceGlobals.KNIGHT, PieceGlobals.BLACK);
		placePiece(SquareGlobals.h, 8, PieceGlobals.ROOK, PieceGlobals.BLACK);
		
		placePiece(SquareGlobals.a, 7, PieceGlobals.PAWN, PieceGlobals.BLACK);
		placePiece(SquareGlobals.b, 7, PieceGlobals.PAWN, PieceGlobals.BLACK);
		placePiece(SquareGlobals.c, 7, PieceGlobals.PAWN, PieceGlobals.BLACK);
		placePiece(SquareGlobals.d, 7, PieceGlobals.PAWN, PieceGlobals.BLACK);
		placePiece(SquareGlobals.e, 7, PieceGlobals.PAWN, PieceGlobals.BLACK);
		placePiece(SquareGlobals.f, 7, PieceGlobals.PAWN, PieceGlobals.BLACK);
		placePiece(SquareGlobals.g, 7, PieceGlobals.PAWN, PieceGlobals.BLACK);
		placePiece(SquareGlobals.h, 7, PieceGlobals.PAWN, PieceGlobals.BLACK);
	}
	
	public void placePiece(int file, int rank, String type, String color)
	{
		SquareInterface square = getSquare(file, rank);
		Piece piece = PieceFactory.createPiece(color, type);
		square.setOccupant(piece);
		piece.setLocation(square);
	}
	
	public Collection getPiecesOnBoard()
	{
		HashSet pieces = new HashSet();
		for(Iterator file = theBoard.iterator(); file.hasNext(); )
		{
			ArrayList nextRank = (ArrayList)file.next();
			for(Iterator rank = nextRank.iterator(); rank.hasNext(); )
			{
				SquareInterface square = (SquareInterface)rank.next();
				Piece occupant = square.getOccupant();
				if(occupant.isValid())
					pieces.add(occupant);
			}
		}
		return pieces;
	}
	
	public String toString()
	{
		StringBuffer buffer = new StringBuffer("   -------------------------------\n");
		for(int rank = 8; rank > 0; rank--)
		{
			buffer.append(rank).append(" |");
			for(int file = 0; file < 8; file++)
			{
				buffer.append(' ');
				buffer.append(getSquare(file, rank).getOccupant().getTextType());
				buffer.append(" |");
			}
			buffer.append("\n   -------------------------------\n");
		}
		buffer.append("    a   b   c   d   e   f   g   h\n\n");
		return buffer.toString();
	}
	
	public String getPositionCode()
	{
		StringBuffer buf = new StringBuffer(65);
		for(int rank = 8; rank > 0; rank--)
		{
			for(int file = 0; file < 8; file++)
			{
				buf.append(getSquare(file, rank).getOccupant().getTextType());
			}
		}
		return buf.toString();
	}
	
	public void recordPosition()
	{
		itsPositionRecord.add(getPositionCode());
	}
	
	public void removeLastPosition()
	{
		int size = itsPositionRecord.size();
		if(size > 0)
			itsPositionRecord.remove(size - 1);
	}
	
	public ArrayList getPositionRecord()
	{
		return itsPositionRecord;
	}
	
	public boolean isThirdOccuranceOfPosition()
	{
		boolean retVal = false;
		String position = getPositionCode();
		if (itsPositionRecord.contains(position))
		{
			int pCount = 0;
			for(Iterator i = itsPositionRecord.iterator(); i.hasNext(); )
			{
				String p = (String)i.next();
				if(p.equals(position))
					pCount++;
				if (pCount >= 3)
				{
					retVal = true;
					break;
				}		
			}
		}
		return retVal;
	}
	
	private void makeBoard()
	{
		theBoard = new ArrayList();
		SquareInterface fakeSquare = new EmptySquare("ChessBoardInit");
		for(int i = 0; i < 8; i++)
		{
			ArrayList list = new ArrayList();
			for(int j = 0; j < 8; j++)
				list.add(fakeSquare);
			theBoard.add(list);
		}
	}
	
	private void loadBoard()
	{
		for(int file = 0; file < 8; file++)
		{
			for(int rank = 0; rank < 8; rank++)
			{
				Square s = new Square(file, rank);
				((ArrayList)theBoard.get(file)).set(rank, s);
			}
		}
	}
	
	private void setNeighbors()
	{
		for(int file = 0; file < 8; file++)
		{
			for(int rank = 1; rank < 9; rank++)
			{
				SquareInterface thisSquare = getSquare(file, rank);
				EmptySquare empty = new EmptySquare(thisSquare.toString());
				
				SquareInterface N = empty;
				if(rank + 1 != 9)
					N = getSquare(file, rank + 1);
					
				SquareInterface NE = empty;
				if(rank + 1 != 9 && file + 1 != 8)
					NE = getSquare(file + 1, rank + 1);
					
				SquareInterface E = empty;
				if(file + 1 != 8)
					E = getSquare(file + 1, rank);
					
				SquareInterface SE = empty;
				if(rank - 1 != 0 && file + 1 != 8)
					SE = getSquare(file + 1 , rank - 1);
					
				SquareInterface S = empty;
				if(rank - 1 != 0)
					S = getSquare(file, rank - 1);
					
				SquareInterface SW = empty;
				if(rank - 1 != 0 && file - 1 != -1)
					SW = getSquare(file - 1, rank - 1);
					
				SquareInterface W = empty;
				if(file - 1 != -1)
					W = getSquare(file - 1, rank);
					
				SquareInterface NW = empty;
				if(rank + 1 != 9 && file - 1 != -1)
					NW = getSquare(file - 1, rank + 1);
				
				thisSquare.setNeighbors(N, NE, E, SE, S, SW, W, NW);	
			}
		}
	}
	
	private int charToFile(char c) throws Exception
	{
		int file = -1;
		if(c == 'a')
			file = SquareGlobals.a;
		if(c == 'b')
			file = SquareGlobals.b;
		if(c == 'c')
			file = SquareGlobals.c;
		if(c == 'd')
			file = SquareGlobals.d;
		if(c == 'e')
			file = SquareGlobals.e;
		if(c == 'f')
			file = SquareGlobals.f;
		if(c == 'g')
			file = SquareGlobals.g;
		if(c == 'h')
			file = SquareGlobals.h;
		
		if(file == -1)
			throw new Exception("File char invalid");
		else
			return file;	
	}
}
