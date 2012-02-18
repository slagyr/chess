package chess.piece;

public class PieceFactory
{
	public static Piece createGamePiece()
	{
		Piece piece = new GamePiece(PieceGlobals.BLACK);
		return piece;
	}
	
	public static Piece createPiece(String color, String type)
	{
		Piece piece = new GamePiece(color);
		
		if(PieceGlobals.KING.equals(type))
			piece = new King(color);
		if(PieceGlobals.QUEEN.equals(type))
			piece = new Queen(color);
		if(PieceGlobals.ROOK.equals(type))
			piece = new Rook(color);
		if(PieceGlobals.BISHOP.equals(type))
			piece = new Bishop(color);
		if(PieceGlobals.KNIGHT.equals(type))
			piece = new Knight(color);
		if(PieceGlobals.PAWN.equals(type))
			piece = new Pawn(color);
			
		return piece;
	}
	
	public static Piece createPiece(String type)
	{
		String color = PieceGlobals.BLACK;
		Piece piece = new GamePiece(color);
		
		if(PieceGlobals.KING.equals(type))
			piece = new King(color);
		if(PieceGlobals.QUEEN.equals(type))
			piece = new Queen(color);
		if(PieceGlobals.ROOK.equals(type))
			piece = new Rook(color);
		if(PieceGlobals.BISHOP.equals(type))
			piece = new Bishop(color);
		if(PieceGlobals.KNIGHT.equals(type))
			piece = new Knight(color);
		if(PieceGlobals.PAWN.equals(type))
			piece = new Pawn(color);
			
		return piece;
	}
}
