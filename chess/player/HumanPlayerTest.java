package chess.player;

import junit.framework.*;
import java.util.*;

import chess.player.HumanPlayer;
import chess.piece.PieceGlobals;
import chess.board.ChessBoard;

public class HumanPlayerTest extends TestCase
{
	public HumanPlayerTest(String name)
	{
		super(name);
	}
	
	public void testConstructor()
	{
		HumanPlayer player = new HumanPlayer(PieceGlobals.WHITE);
		assertEquals(PieceGlobals.WHITE, player.getColor());
	}
	
	public void testSetGetBoard()
	{
		HumanPlayer player = new HumanPlayer(PieceGlobals.WHITE);
		ChessBoard board = new ChessBoard();
		player.setBoard(board);
		assertSame(board, player.getBoard());
	}
	
	public void testLoadPieces()
	{
		HumanPlayer player = new HumanPlayer(PieceGlobals.WHITE);
		ChessBoard board = new ChessBoard();
		board.normalSetup();
		player.setBoard(board);
		player.loadPieces();
		Collection pieces = player.getPieces();
		assertEquals(16, pieces.size()); 
	}
}
