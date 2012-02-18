package chess.notation;

import junit.framework.*;

import java.util.GregorianCalendar;
import java.util.Calendar;

import chess.notation.*;
import chess.player.Player;
import chess.board.*;
import chess.move.*;
import chess.piece.*;

public class PgnGameManagerTest extends TestCase
{
    private PgnGameManager game;
    private Notation notation;
    private Player white;
    private Player black;
    private ChessBoard board;

    public PgnGameManagerTest(String name)
    {
        super(name);
    }

    public void setUp()
    {
        notation = new ShortAlgebraicNotation();
        game = new PgnGameManager(validGame, notation);
    }

    public void testGetMovesByIndex()
    {
        String str = notation.getNotation(game.getMove(1));
        assertEquals("d4", str);
        str = notation.getNotation(game.getMove(2));
        assertEquals("d5", str);
        str = notation.getNotation(game.getMove(10));
        assertEquals("Nc6", str);
        str = notation.getNotation(game.getMove(19));
        assertEquals("Nxd4", str);
        AbstractMove move = game.getMove(500);
        assertTrue(! move.isValid());
    }

    public void testGetMovesByColorAndIndex()
    {
        String str = notation.getNotation(game.getWhiteMove(1));
        assertEquals("d4", str);
        str = notation.getNotation(game.getBlackMove(1));
        assertEquals("d5", str);
        str = notation.getNotation(game.getWhiteMove(10));
        assertEquals("Nxd4", str);
        str = notation.getNotation(game.getBlackMove(10));
        assertEquals("Re8", str);
    }

    public void testGetNextPreviousCurrentMoves()
    {
        String str = notation.getNotation(game.getNextMove());
        assertEquals("d4", str);
        str = notation.getNotation(game.getNextMove());
        assertEquals("d5", str);
        str = notation.getNotation(game.getNextMove());
        assertEquals("c4", str);
        str = notation.getNotation(game.getCurrentMove());
        assertEquals("c4", str);
        str = notation.getNotation(game.getPreviousMove());
        assertEquals("d5", str);
        str = notation.getNotation(game.getCurrentMove());
        assertEquals("d5", str);
    }

    public void testIsValid()
    {
        assertTrue("valid game", game.isValid());
        game = new PgnGameManager(invalidGame, notation);
        assertTrue("invalid game", ! game.isValid());
        assertEquals("d5", game.getError());
        System.err.println("OK! -expected error message 'MockPiece.reverseMove(...'");
    }

    public void testGetMovesInNotation()
    {
        String[] whiteMoves = game.getMovesInNotation(PieceGlobals.WHITE);
        String[] blackMoves = game.getMovesInNotation(PieceGlobals.BLACK);
        assertEquals("d4", whiteMoves[0]);
        assertEquals("c4", whiteMoves[1]);
        assertEquals("Qe4", whiteMoves[83]);
        assertEquals("d5", blackMoves[0]);
        assertEquals("Qd4+", blackMoves[82]);
    }

    public void testUndoingBuild()
    {
        Player white = game.getWhitePlayer();
        Player black = game.getBlackPlayer();
        assertEquals(16, white.getPieces().size());
        assertEquals(16, black.getPieces().size());
    }

    public void testFullGameForwardAndBack()
    {
        PgnGameManager manager = new PgnGameManager(validGame, new ShortAlgebraicNotation());
        AbstractMove move = null;
        for(int i = 0; i < 167; i++)
            move = manager.getNextMove();
        assertEquals("e4", move.getEndSquare().toString());
        for(int i = 0; i < 166; i++)
            move = manager.getPreviousMove();
        assertEquals(0, manager.getIndex());
        assertEquals("d4", move.getEndSquare().toString());
    }

    public void testExtremities()
    {
        PgnGameManager manager = new PgnGameManager(validGame, new ShortAlgebraicNotation());
        AbstractMove move = null;
        for(int i = 0; i < 200; i++)
            move = manager.getNextMove();
        assertEquals(166, manager.getIndex());
        assertTrue(! move.isValid());
        move = manager.getPreviousMove();
        assertTrue(move.isValid());
        assertEquals(165, manager.getIndex());
        assertEquals("d4", move.getEndSquare().toString());

        for(int i = 0; i < 200; i++)
            move = manager.getPreviousMove();
        assertEquals(-1, manager.getIndex());
        assertTrue(! move.isValid());
        move = manager.getNextMove();
        assertEquals(0, manager.getIndex());
        assertEquals("d4", move.getEndSquare().toString());
    }

    public void testSetGameData()
    {
        PgnGameManager game = new PgnGameManager(new ShortAlgebraicNotation());
        assertTrue(!game.existingGame());
        game.setGameData(validGame);
        assertTrue(game.existingGame());
    }

    public void testAddMove()
    {
        PgnGameManager game = new PgnGameManager(new ShortAlgebraicNotation());
        Piece p = PieceFactory.createPiece(PieceGlobals.BLACK, PieceGlobals.BISHOP);
        Square a1 = new Square(SquareGlobals.a, 1);
        Square b2 = new Square(SquareGlobals.b, 2);
        Move move = new Move(p, a1, b2);
        game.addMove(move);
        assertEquals(0, game.getWhitesMoves().size());
        assertEquals(1, game.getBlacksMoves().size());
    }


    public static final String validGame =
        "[Event \"?\"]\n" +
        "[Site \"San Francisco USA,No\"]\n" +
        "[Date \"1997.??.??\"]\n" +
        "[Round \"3\"]\n" +
        "[White \"Lobo, R.\"]\n" +
        "[Black \"Cunningham, R.\"]\n" +
        "[Result \"1-0\"]\n" +
        "[ECO \"D34\"]\n" +
        "\n" +
        "1. d4 d5 2. c4 e6 3. Nc3 c5 4. cxd5 exd5 5. Nf3 Nc6 6. g3 Nf6 7. Bg2\n" +
        "Be7 8. 0-0 0-0 9. Bg5 cxd4 10. Nxd4 Re8 11. Rc1 h6 12. Be3 Bf8 13. Nxc6 \n" +
        "bxc6 14. Na4 Bd7 15. Bc5 Qa5 16. Bxf8 Kxf8 17. e3 Bf5 18. a3 Qb5 19.\n" +
        "Qd4 Nd7 20. Rfd1 Kg8 21. g4 Be6 22. h3 Rac8 23. Nc5 Qb6 24. Rd2 Nxc5 \n" +
        "25. Rxc5 f5 26. b4 fxg4 27. hxg4 Rc7 28. Rdc2 a6 29. Rxd5 Qb8 30. Rd6\n" +
        "c5 31. Rxc5 Rxc5 32. bxc5 Qc8 33. Rc6 Qb8 34. Rxe6 Rxe6 35. Bd5 Kf7\n" +
        "36. Qc4 Qe5 37. c6 Kf6 38. Bxe6 Qxe6 39. Qf4+ Ke7 40. c7 Kd7 41. Qd4+\n" +
        "Kxc7 42. Qxg7+ Kc6 43. Qd4 Kb5 44. Kh2 Qe7 45. Kg2 Qxa3 46. Qd7+ Ka5\n" +
        "47. Qc7+ Kb5 48. Qe5+ Kb6 49. Qe6+ Kb5 50. Qxh6 a5 51. Qg5+ Qc5 52. Qd8 \n" +
        "Qc6+ 53. f3 a4 54. Qd3+ Kb4 55. g5 a3 56. Qd2+ Kb3 57. Qd3+ Kb2 58. g6\n" +
        "a2 59. g7 Qe6 60. Qh7 Qxe3 61. g8=Q Qe2+ 62. Kg3 Qe5+ 63. f4 Qc3+ 64.\n" +
        "Kg4 a1=Q 65. Qb8+ Kc1 66. Qh1+ Kc2 67. Qg2+ Kc1 68. Qf1+ Kc2 69. Qe2+\n" +
        "Kc1 70. Qbe5 Qc8+ 71. Kg3 Qg8+ 72. Kf2 Qa7+ 73. Q5e3+ Qxe3+ 74. Qxe3+\n" +
        "Kb1 75. Kf3 Qf7 76. Qd3+ Ka1 77. f5 Qh5+ 78. Ke3 Qg5+ 79. Ke4 Qe7+ 80.\n" +
        "Kf4 Qf6 81. Qa3+ Kb1 82. Qb3+ Ka1 83. Qe6 Qd4+ 84. Qe4 1-0";

    private static final String invalidGame =
        "[Event \"?\"]\n" +
        "[Site \"San Francisco USA,No\"]\n" +
        "[Date \"1997.??.??\"]\n" +
        "[Round \"3\"]\n" +
        "[White \"Lobo, R.\"]\n" +
        "[Black \"Cunningham, R.\"]\n" +
        "[Result \"1-0\"]\n" +
        "[ECO \"D34\"]\n" +
        "\n" +
        "1.d4 d5 2.c4 e6 3.Nc3 c5 4.cxd5 exd5 5.Nf3 Nc6 6.g3 Nf6 7.Bg2\n";
}
