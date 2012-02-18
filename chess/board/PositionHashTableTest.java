package chess.board;

import junit.framework.*;
import chess.board.PositionHashTable;

public class PositionHashTableTest extends TestCase
{
	public PositionHashTableTest(String name)
	{
		super(name);
	}
	
	public void testBasics()
	{
		PositionHashTable table = new PositionHashTable();
		table.add("abc", 501);
		assertTrue(table.has("abc"));
		assertEquals(501, table.getScore());
	}
	
	public void testMutipleAdds()
	{
		PositionHashTable table = new PositionHashTable();
		table.add("abc", 501);
		table.add("defghi", 234);
		table.add("lkjg", 789);
		table.add("cba", 502);
		table.add("jglk", 453);
		table.add("bbb", 469);
		assertTrue(table.has("abc"));
		assertEquals(501, table.getScore());
		assertTrue(table.has("defghi"));
		assertEquals(234, table.getScore());
		assertTrue(table.has("lkjg"));
		assertEquals(789, table.getScore());
		assertTrue(table.has("cba"));
		assertEquals(502, table.getScore());
		assertTrue(table.has("jglk"));
		assertEquals(453, table.getScore());
		assertTrue(table.has("bbb"));
		assertEquals(469, table.getScore());
	
		assertTrue(! table.has("none"));
	}
	
	public void testSpeedOfFindingHash()
	{
		PositionHashTable table = new PositionHashTable();
		long timer = 0;
		String code = "rnbqkbnrpppppp p              p              N PPPPPPPP RNBQKB R";
		long time = System.currentTimeMillis();
		for(int i = 0; i < 10000; i++) // divided number by 10 for laptop.
		{
			int hash = table.findHash(code);
		}
		timer += System.currentTimeMillis() - time;
		assertTrue(timer < 300);
	}
}
