package chess.notation;

import junit.framework.*;
import java.io.*;
import java.util.List;

import chess.notation.*;

public class PgnFileTest extends TestCase
{
    private static final String readFilename = "pgnFile.pgn";
    private static final String writeFilename = "pgnFile2.pgn";

    private File testFile;
    private PgnFile pgnFile;

    public PgnFileTest(String name)
    {
        super(name);
    }

    public void setUp() throws Exception
    {
        testFile = createFile();
        pgnFile = new PgnFile(testFile);
    }

    public void tearDown()
    {
        File temp = new File(readFilename);
        temp.delete();
        temp = new File(writeFilename);
        temp.delete();
    }

    public void testParsingFile()
    {
        List games = pgnFile.getGameList();
        assertEquals(3, games.size());
        PgnGame game1 = (PgnGame)games.get(0);
        PgnGame game2 = (PgnGame)games.get(1);
        PgnGame game3 = (PgnGame)games.get(2);
        assertEquals("Noa, J.", game1.getWhite());
        assertEquals("Opocensky, K.", game2.getWhite());
        assertEquals("Bolbochan, J.", game3.getWhite());

        game1 = pgnFile.getGame(1);
        assertEquals("Noa, J.", game1.getWhite());
    }

    public void testWrittingFile()
    {
        PgnFile newFile = new PgnFile(writeFilename);
        List oldGames = pgnFile.getGameList();
        newFile.addGame((PgnGame)oldGames.get(0));
        List newGames = newFile.getGameList();
        assertEquals(1, newGames.size());
        newFile.addGame((PgnGame)oldGames.get(1));
        newFile.addGame((PgnGame)oldGames.get(2));
        newFile.writeFile();

        File file = new File(writeFilename);
        assertTrue("file should exist", file.exists());
        newFile = new PgnFile(writeFilename);
        newGames = newFile.getGameList();
        assertEquals(oldGames.get(0).toString(), newGames.get(0).toString());
        assertEquals(oldGames.get(1).toString(), newGames.get(1).toString());
        assertEquals(oldGames.get(2).toString(), newGames.get(2).toString());
    }

    private File createFile() throws Exception
    {
        FileWriter writer = new FileWriter(readFilename);
        writer.write(pgnContent);
        writer.close();

        return new File(readFilename);
    }

    private static final String pgnContent =
        "[Event \"?\"]\n" +
        "[Site \"DSB-02.Kongress ;HCL 12\"]\n" +
        "[Date \"1881.??.??\"]\n" +
        "[Round \"4\"]\n" +
        "[White \"Noa, J.\"]\n" +
        "[Black \"Riemann, F.\"]\n" +
        "[Result \"0-1\"]\n" +
        "[ECO \"C50\"]\n" +
        "\n" +
        "1. e4 e5 2. Nf3 Nc6 3. Bc4 Bc5 4. d3 d6 5. Nc3 h6 6. h3 Nf6 7. Bd2 Ne7\n" +
        "8. Ne2 c6 9. Bb3 Qb6 10. Rf1 a5 11. Nc3 Qc7 12. a4 g5 13. g4 Be6 14.\n" +
        "Ba2 Ng6 15. Qe2 Nf4 16. Bxf4 gxf4 17. Bxe6 fxe6 18. O-O-O O-O-O 19. d4\n" +
        "exd4 20. Nxd4 Bxd4 21. Rxd4 e5 22. Rd3 Nd7 23. Rd2 Nc5 24. f3 Rhe8 25.\n" +
        "Rfd1 Ne6 26. Qf2 Kb8 27. Kb1 Nd4 28. h4 Re7 29. Rd3 Ne6 30. Kc1 Nc5\n" +
        "31. R3d2 Ne6 32. b3 Red7 33. Kb2 Nc5 34. Rg1 Na6 35. g5 hxg5 36. Rxg5\n" +
        "Nb4 37. Qg1 d5 38. exd5 Nxd5 39. Nxd5 cxd5 40. Re2 d4 41. Qe1 Rc8 42.\n" +
        "Rgxe5 d3 43. cxd3 Rxd3 44. Rb5 Rc3 45. h5 Rxf3 46. Rxa5 Rc3 47. Rb5 Rc1\n" +
        "48. Qf2 Qg7+ 49. Ka3 Ra1+ 50. Ra2 Qe7+ 51. Rb4 Rxa2+ 52. Qxa2 f3 53.\n" +
        "Qd2 Rf8 54. Qh2+ Ka8 55. h6 f2 56. h7 f1=Q 57. h8=Q Rxh8 58. Qxh8+ Ka7\n" +
        "59. Qd4+ Kb8 60. Qh8+ Kc7 61. Qc3+ Kb8 62. Qh8+ Qff8 63. Qc3 Qc5 0-1\n" +
        "\n" +
        "[Event \"?\"]\n" +
        "[Site \"Buenos Aires ol f-A ;HCL 37\"]\n" +
        "[Date \"1939.??.??\"]\n" +
        "[Round \"1\"]\n" +
        "[White \"Opocensky, K.\"]\n" +
        "[Black \"Da Silva Rocha, A.\"]\n" +
        "[Result \"1-0\"]\n" +
        "[ECO \"E04\"]\n" +
        "\n" +
        "1. d4 Nf6 2. c4 e6 3. Nf3 d5 4. g3 dxc4 5. Bg2 Nbd7 6. Nbd2 Nb6 7. Nxc4\n" +
        "Nxc4 8. Qa4+ Bd7 9. Qxc4 Bc6 10. O-O Be7 11. Bf4 O-O 12. a3 a5 13. Rac1\n" +
        "Bd6 14. Be5 Nd7 15. Qc2 Bxe5 16. dxe5 Qe7 17. Rfd1 Rfd8 18. Qc3 a4 19.\n" +
        "Ne1 Bxg2 20. Kxg2 Rac8 21. Nf3 Nb6 22. Rxd8+ Qxd8 23. Qb4 h6 24. Rc2\n" +
        "Qd7 25. h3 Rd8 26. Qc5 Qd5 27. Qb4 Rd7 28. e4 Qd1 29. Rd2 Rxd2 30. Nxd2\n" +
        "Qc2 31. Qd4 Kh7 32. g4 c5 33. Qe3 Qxb2 34. Nf3 c4 35. g5 c3 36. gxh6\n" +
        "gxh6 37. Qf4 Kg8 38. Qxh6 Nd7 39. Qg5+ Kh7 40. Qh5+ Kg8 41. Ng5 Nxe5\n" +
        "42. Qh7+ Kf8 43. Qh8+ Ke7 44. Qxe5 Qb3 45. Qc7+ Kf6 46. f4 e5 47. Qxe5+\n" +
        "Kg6 48. Qf5+ Kg7 49. Qh7+ Kf8 50. Qh8+ Ke7 51. Qe5+ Kd7 52. Qd4+ Ke7\n" +
        "53. Qc5+ Ke8 54. e5 Qc2+ 55. Kg3 Qd3+ 56. Kh4 Kd7 57. f5 c2 58. e6+\n" +
        "fxe6 59. fxe6+ Ke8 60. Qc8+ Ke7 61. Qc7+ Kf6 62. Qf7+ Ke5 63. e7 c1=Q\n" +
        "64. e8=Q+ Kd4 65. Qxa4+ Kc3 66. Qab3+ Kd2 67. Qf2+ 1-0\n" +
        "\n" +
        "[Event \"?\"]\n" +
        "[Site \"Mar del Plata ;HCL 30\"]\n" +
        "[Date \"1942.??.??\"]\n" +
        "[Round \"6\"]\n" +
        "[White \"Bolbochan, J.\"]\n" +
        "[Black \"Michel, P.\"]\n" +
        "[Result \"1/2-1/2\"]\n" +
        "[ECO \"A52\"]\n" +
        "\n" +
        "1. d4 Nf6 2. c4 e5 3. dxe5 Ng4 4. Nf3 Bc5 5. e3 Nc6 6. Be2 O-O 7. O-O\n" +
        "Ngxe5 8. Nxe5 Nxe5 9. Nc3 Re8 10. a3 a5 11. b3 Ra6 12. Nd5 Ng6 13. Bb2\n" +
        "Nh4 14. g3 Ng6 15. b4 axb4 16. axb4 Rxa1 17. Qxa1 Bf8 18. Bd4 Ne7 19.\n" +
        "Bf6 c6 20. Nxe7+ Bxe7 21. Bxg7 Bxb4 22. Bh8 Bf8 23. Rd1 Qg5 24. Bf6 Qh6\n" +
        "25. Rd4 d6 26. Rh4 Qg6 27. Bh5 Qd3 28. Bg4 Bf5 29. Bxf5 Qxf5 30. Qd4 h5\n" +
        "31. g4 Qg6 32. Rxh5 Bg7 33. Bxg7 Qxg7 34. Qf4 Re5 35. Rxe5 dxe5 36. Qf5\n" +
        "b5 37. Qc8+ Kh7 38. Qf5+ Kg8 39. Qc8+ Kh7 40. cxb5 cxb5 41. h3 Qg6 42.\n" +
        "Qb7 Qb1+ 43. Kg2 Kg7 44. Qd5 b4 45. Qxe5+ Kh7 46. Qe7 Kg7 47. h4 b3\n" +
        "48. h5 b2 49. Qe5+ Kf8 50. h6 Qg6 51. Qb8+ Ke7 52. Qb4+ Kd7 53. h7 b1=Q\n" +
        "54. Qd4+ Ke7 55. Qc5+ Kd7 56. Qd5+ Ke7 57. Qc5+ Kd7 58. Qd4+ Ke7 59.\n" +
        "h8=Q Qbe4+ 60. Qxe4+ Qxe4+ 61. Kg3 Qb1 62. Qe5+ Kf8 63. Qd6+ Kg8 64.\n" +
        "Qd8+ Kg7 65. Qd4+ Kg8 66. Kg2 Qb8 67. Qd5 Qc8 68. Qg5+ Kf8 69. e4 Qb7\n" +
        "70. Qd5 Qc8 71. Qf5 Qc6 72. Qe5 Qd7 73. Qc5+ Kg8 74. Kg3 Qd3+ 75. Qe3\n" +
        "Qb1 76. f3 Qd1 77. e5 Kg7 78. f4 Qd7 79. f5 Qc7 80. Kh3 Qc6 81. Kg3 Qc7\n" +
        "82. Kh4 Qc6 83. Kh3 Qb7 84. e6 fxe6 85. fxe6 Qe7 86. Qd4+ Kg6 87. Qe4+\n" +
        "Kg7 88. Qe5+ Kg6 89. Kg2 Qb4 90. Kf3 Qa3+ 91. Qe3 Qf8+ 92. Ke2 Qb4 93.\n" +
        "Qd3+ Kg5 94. Qf5+ Kh6 95. g5+ Kh5 96. Kf3 Qc3+ 97. Kg2 Qd2+ 98. Kg3\n" +
        "Qe1+ 99. Kg2 Qd2+ 100. Kf3 Qc3+ 101. Ke2 Qb2+ 102. Kd1 Qd4+ 103. Kc2\n" +
        "Qc4+ 104. Kd2 Qb4+ 105. Ke2 1/2-1/2";

}
