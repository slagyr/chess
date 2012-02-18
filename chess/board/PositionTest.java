package chess.board;

import junit.framework.*;
import chess.board.Position;

public class PositionTest extends TestCase
{
	public PositionTest(String name)
	{
		super(name);
	}
	
	public void testConstructor()
	{
		Position p = new Position("abc", 501);
		assertEquals("abc", p.getCode());
		assertEquals(501, p.getScore());
	}
}
