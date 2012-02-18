package chess.notation;

import junit.framework.*;
import java.io.*;
import java.util.List;

import chess.notation.PgnGame;

public class PgnGameTest extends TestCase
{
    private PgnGame game;

    public PgnGameTest(String name)
    {
        super(name);
    }

    public void setUp()
    {
        game = new PgnGame(gameData);
    }

    public void testDetails()
    {
        assertEquals("EM/M/A076", game.getEvent());
        assertEquals("ICCF Email", game.getSite());
        assertEquals("1999.08.15", game.getDate());
        assertEquals("1", game.getRound());
        assertEquals("Anonymous, Andy (ENG)", game.getWhite());
        assertEquals("Noname, Ned (HUN)", game.getBlack());
        assertEquals("1-0", game.getResult());
        assertEquals("A08", game.getEco());
        assertEquals("2155", game.getWhiteElo());
        assertEquals("2390", game.getBlackElo());
        assertEquals("53", game.getPlyCount());
        assertEquals("1999.08.15", game.getEventDate());
    }

    public void testParsingOfMoves()
    {
        List whitesMoves = game.getWhitesMoves();
        List blacksMoves = game.getBlacksMoves();
        assertEquals(27, whitesMoves.size());
        assertEquals(26, blacksMoves.size());
        assertTrue("white's first move", whitesMoves.get(0).equals("e4"));
        assertTrue("white castle", whitesMoves.contains("0-0-0"));
        assertTrue("black's first move", blacksMoves.get(0).equals("d6"));
    }

    public void testToString()
    {
        assertEquals("Anonymous, Andy (ENG) -vs- Noname, Ned (HUN) : 1999.08.15", game.toString());
    }

    public void testToPgnFormat()
    {
        assertEquals(gameData, game.toPgnFormat());
    }

    public void testSetGameData()
    {
        PgnGame game = new PgnGame();
        game.setGameData(gameData);
        assertEquals("Anonymous, Andy (ENG)", game.getWhite());
    }

    private static final String gameData =
        "[Event \"EM/M/A076\"]\n" +
        "[Site \"ICCF Email\"]\n" +
        "[Date \"1999.08.15\"]\n" +
        "[Round \"1\"]\n" +
        "[White \"Anonymous, Andy (ENG)\"]\n" +
        "[Black \"Noname, Ned (HUN)\"]\n" +
        "[Result \"1-0\"]\n" +
        "[ECO \"A08\"]\n" +
        "[WhiteElo \"2155\"]\n" +
        "[BlackElo \"2390\"]\n" +
        "[PlyCount \"53\"]\n" +
        "[EventDate \"1999.08.15\"]\n" +
        "\n" +
        "1. e4 d6 2. d4 g6 3. Nf3 Bg7 4. Be2 Nf6 5. Nc3 Nbd7 6. e5 Ng4 \n" +
        "7. e6 Ndf6 8. exf7+ Kf8 9. Bf4 Nh6 10. Qd2 Nxf7 11. 0-0-0 c6 12. h4 Qa5 \n" +
        "13. Ng5 b5 14. a3 Bd7 15. h5 Nxg5 16. Bxg5 h6 17. Bxf6 Bxf6 18. hxg6 Kg7 \n" +
        "19. Rh5 Kxg6 20. Bd3+ Kf7 21. Qf4 Rag8 22. Rdh1 Ke8 23. Kb1 Bg5 24. Qe4 Qc7 \n" +
        "25. d5 Rf8 26. dxc6 Bf5 27. Nxb5 1-0 \n";
}
