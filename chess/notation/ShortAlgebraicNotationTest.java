package chess.notation;

import junit.framework.*;
import chess.notation.*;
import chess.board.*;
import chess.player.Player;
import chess.move.*;
import chess.piece.*;

public class ShortAlgebraicNotationTest extends TestCase
{

    Notation itsNotation;
    ChessBoard board;
    Player white;
    Player black;

    public ShortAlgebraicNotationTest(String name)
    {
        super(name);
    }

    public void setUp()
    {
        itsNotation = new ShortAlgebraicNotation();
    }

    public void tearDown()
    {
    }

    public void testGetNotationForPawn()
    {
        AbstractMove move = new Move(PieceFactory.createPiece(PieceGlobals.PAWN),
                new Square(SquareGlobals.e, 1), new Square(SquareGlobals.e,  3), new MockPiece());
        assertEquals("e4", itsNotation.getNotation(move));

        move = new Move(PieceFactory.createPiece(PieceGlobals.PAWN),
                new Square(SquareGlobals.e, 3), new Square(SquareGlobals.e,  4), new MockPiece());
        move.check();
        assertEquals("e5+", itsNotation.getNotation(move));

        move = new Move(PieceFactory.createPiece(PieceGlobals.PAWN), new Square(SquareGlobals.e, 1),
                new Square(SquareGlobals.f,  2), PieceFactory.createPiece(PieceGlobals.PAWN));
        assertEquals("exf3", itsNotation.getNotation(move));
    }

    public void testGetNotationCastles()
    {
        AbstractMove move = new KingSideCastleMove(PieceFactory.createPiece(PieceGlobals.KING),
                new Square(SquareGlobals.e, 0), new Square(SquareGlobals.g,  0));
        assertEquals("0-0", itsNotation.getNotation(move));

        move = new QueenSideCastleMove(PieceFactory.createPiece(PieceGlobals.KING),
                new Square(SquareGlobals.e, 0), new Square(SquareGlobals.c,  0));
        move.check();
        assertEquals("0-0-0+", itsNotation.getNotation(move));
    }

    public void testGetNotationForPieces()
    {
        AbstractMove move = new Move(PieceFactory.createPiece(PieceGlobals.KING),
                new Square(SquareGlobals.a, 0), new Square(SquareGlobals.b,  1));
        assertEquals("Kb2", itsNotation.getNotation(move));

        move = new Move(PieceFactory.createPiece(PieceGlobals.BISHOP),
                new Square(SquareGlobals.a, 0), new Square(SquareGlobals.b,  1));
        move.check();
        assertEquals("Bb2+", itsNotation.getNotation(move));

        move = new Move(PieceFactory.createPiece(PieceGlobals.QUEEN),
                new Square(SquareGlobals.a, 0), new Square(SquareGlobals.b,  1), new GamePiece(PieceGlobals.WHITE));
        move.setEndOfGame(MoveGlobals.MATE);
        assertEquals("Qxb2++", itsNotation.getNotation(move));
    }

    public void testGetNotationWithSameTypePiecesOnSameRank()
    {
        SquareInterface b1 = new Square(SquareGlobals.b, 0);
        SquareInterface a1 = new Square(SquareGlobals.a, 0);
        SquareInterface h1 = new Square(SquareGlobals.h, 0);
        Piece movingRook = PieceFactory.createPiece(PieceGlobals.ROOK);
        b1.addAttacker(movingRook);
        Piece stationaryRook = PieceFactory.createPiece(PieceGlobals.ROOK);
        stationaryRook.setLocation(h1);
        b1.addAttacker(stationaryRook);
        AbstractMove move = new Move(movingRook, a1, b1);
        assertEquals("Rab1", itsNotation.getNotation(move));
    }

    public void testGetNotationWithSamePieceTypeOnSameFile()
    {
        SquareInterface a3 = new Square(SquareGlobals.a, 2);
        SquareInterface a1 = new Square(SquareGlobals.a, 0);
        SquareInterface c2 = new Square(SquareGlobals.c, 1);
        Piece movingKnight = PieceFactory.createPiece(PieceGlobals.KNIGHT);
        c2.addAttacker(movingKnight);
        Piece stationaryKnight1 = PieceFactory.createPiece(PieceGlobals.KNIGHT);
        stationaryKnight1.setLocation(a3);
        c2.addAttacker(stationaryKnight1);
        AbstractMove move = new Move(movingKnight, a1, c2);
        assertEquals("N1c2", itsNotation.getNotation(move));

        SquareInterface e1 = new Square(SquareGlobals.e, 0);
        Piece stationaryKnight2 = PieceFactory.createPiece(PieceGlobals.KNIGHT);
        stationaryKnight2.setLocation(e1);
        c2.addAttacker(stationaryKnight2);
        move = new Move(movingKnight, a1, c2);
        assertEquals("Na1c2", itsNotation.getNotation(move));
    }

    public void testGetNotationForPawnPromotion()
    {
        PromotionMove move = new PromotionMove(PieceFactory.createPiece(PieceGlobals.PAWN),
                new Square(SquareGlobals.a, 6), new Square(SquareGlobals.a,  7));
        move.setPromotion(PieceFactory.createPiece(PieceGlobals.QUEEN));
        assertEquals("a8Q", itsNotation.getNotation(move));
    }

    public void testGetNotationForResign()
    {
        AbstractMove move = new ResignMove(new MockPiece());
        assertEquals("resigns", itsNotation.getNotation(move));
    }

    public void testGetMoveResigns()
    {
        setUpBoard();
        AbstractMove move = itsNotation.getMove("resign", board, white);
        assertEquals(MoveGlobals.RESIGN, move.getType());
        assertEquals(white.getKing(), move.getPiece());

        setUpBoard();
        move = itsNotation.getMove("resigns", board, white);
        assertEquals(MoveGlobals.RESIGN, move.getType());
    }

    public void testGetMoveCastles()
    {
        setUpBoard();
        AbstractMove move = itsNotation.getMove("0-0\t", board, white);
        assertEquals(MoveGlobals.KING_SIDE_CASTLE,  move.getType());
        assertEquals(board.getSquare("e1"), move.getStartSquare());
        assertEquals(board.getSquare("g1"), move.getEndSquare());

        setUpBoard();
        move = itsNotation.getMove("0-0-0+\n", board, white);
        assertEquals(MoveGlobals.QUEEN_SIDE_CASTLE,  move.getType());
        assertEquals(board.getSquare("e1"), move.getStartSquare());
        assertEquals(board.getSquare("c1"), move.getEndSquare());
    }

    public void testGetMoveFullDistinguisher()
    {
        setUpBoard();
        SquareInterface a4 = board.getSquare("a4");
        SquareInterface c3 = board.getSquare("c3");
        Piece movedKnight = a4.getOccupant();
        AbstractMove move = itsNotation.getMove("Na4c3", board, white);
        assertSame(movedKnight, move.getPiece());
        assertEquals(a4, move.getStartSquare());
        assertEquals(c3, move.getEndSquare());
    }

    public void testGetMoveEnPassant()
    {
        setUpBoard();
        SquareInterface b7 = board.getSquare("b7");
        SquareInterface b5 = board.getSquare("b5");
        SquareInterface a5 = board.getSquare("a5");
        SquareInterface b6 = board.getSquare("b6");
        Piece whitePawn = a5.getOccupant();
        Piece blackPawn = b7.getOccupant();
        blackPawn.makeMove(b5);
        whitePawn.findMoves();
        AbstractMove move = itsNotation.getMove("axb6", board, white);
        assertSame(whitePawn, move.getPiece());
        assertEquals(a5, move.getStartSquare());
        assertEquals(b6, move.getEndSquare());
    }

    public void testGetMovePromotion()
    {
        setUpBoard();
        SquareInterface a7 = board.getSquare("a7");
        SquareInterface a8 = board.getSquare("a8");
        Piece pawn = a7.getOccupant();
        AbstractMove move = itsNotation.getMove("a8Q", board, white);
        assertEquals(MoveGlobals.PROMOTION,  move.getType());
        PromotionMove promo = (PromotionMove)move;
        assertSame(pawn, promo.getPiece());
        assertEquals(a7, promo.getStartSquare());
        assertEquals(a8, promo.getEndSquare());
        assertEquals(PieceGlobals.QUEEN, promo.getPromotion().getType());
    }

    public void testGetMovePawn()
    {
        setUpBoard();
        AbstractMove move = itsNotation.getMove("a6", board, white);
        assertEquals(MoveGlobals.MOVE, move.getType());
    }

    public void testGetMovePartialDistinguisher()
    {
        setUpBoard();
        SquareInterface c7 = board.getSquare("c7");
        SquareInterface h7 = board.getSquare("h7");
        SquareInterface h1 = board.getSquare("h1");
        Piece cRook = c7.getOccupant();
        Piece hRook = h1.getOccupant();
        AbstractMove move = itsNotation.getMove("Rch7", board, white);
        assertSame(cRook, move.getPiece());
        assertEquals(c7, move.getStartSquare());
        assertEquals(h7, move.getEndSquare());
        move = itsNotation.getMove("Rhh7", board, white);
        assertSame(hRook, move.getPiece());
        assertEquals(h1, move.getStartSquare());
        assertEquals(h7, move.getEndSquare());
    }

    public void testGetMoveInvalidMove()
    {
        setUpBoard();
        AbstractMove move = itsNotation.getMove("23.a34", board, white);
        assertTrue(! move.isValid());
    }

    private void setUpBoard()
    {
		board = new ChessBoard();
		board.placePiece(SquareGlobals.a, 1, PieceGlobals.ROOK, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.e, 1, PieceGlobals.KING, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.h, 1, PieceGlobals.ROOK, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.a, 2, PieceGlobals.KNIGHT, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.d, 3, PieceGlobals.PAWN, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.a, 4, PieceGlobals.KNIGHT, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.c, 4, PieceGlobals.PAWN, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.e, 4, PieceGlobals.KNIGHT, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.c, 5, PieceGlobals.PAWN, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.a, 5, PieceGlobals.PAWN, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.a, 7, PieceGlobals.PAWN, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.b, 7, PieceGlobals.PAWN, PieceGlobals.BLACK);
		board.placePiece(SquareGlobals.c, 7, PieceGlobals.ROOK, PieceGlobals.WHITE);
		board.placePiece(SquareGlobals.f, 8, PieceGlobals.KING, PieceGlobals.BLACK);
        white = new Player(PieceGlobals.WHITE);
        black = new Player(PieceGlobals.BLACK);
        white.setBoard(board);
        white.loadPieces();
        white.findAllMoves(false);
        black.setBoard(board);
        black.loadPieces();
        black.findAllMoves(false);
    }
}
