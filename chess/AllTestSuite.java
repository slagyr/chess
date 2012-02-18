package chess;

import junit.framework.*;
import chess.gui.GuiChessSquareTest;
import chess.board.*;
import chess.move.*;
import chess.notation.*;
import chess.piece.*;
import chess.player.*;
import chess.scoring.*;

public class AllTestSuite
{
	public static void main(String[] args)
	{
		junit.textui.TestRunner.run(suite());
	}
	
	public static Test suite()
	{
		TestSuite suite = new TestSuite("Test Suite");
		suite.addTest(new TestSuite(ChessBoardTest.class));
		suite.addTest(new TestSuite(SquareTest.class));
		suite.addTest(new TestSuite(GamePieceTest.class));
		suite.addTest(new TestSuite(KingTest.class));
		suite.addTest(new TestSuite(QueenTest.class));
		suite.addTest(new TestSuite(RookTest.class));
		suite.addTest(new TestSuite(BishopTest.class));
		suite.addTest(new TestSuite(KnightTest.class));
		suite.addTest(new TestSuite(PawnTest.class));
		suite.addTest(new TestSuite(HumanPlayerTest.class));
		suite.addTest(new TestSuite(PlayerTest.class));
		//suite.addTest(new TestSuite(ChessGameTest.class));
		suite.addTest(new TestSuite(MoveTest.class));
		suite.addTest(new TestSuite(IllegalMoveTest.class));
		suite.addTest(new TestSuite(CastleMoveTest.class));
		suite.addTest(new TestSuite(EnpassantMoveTest.class));
		suite.addTest(new TestSuite(ResignMoveTest.class));
		suite.addTest(new TestSuite(PromotionMoveTest.class));
		suite.addTest(new TestSuite(MaterialScorerTest.class));
		suite.addTest(new TestSuite(CoverageScorerTest.class));
		suite.addTest(new TestSuite(MoveFinderTest.class));
		suite.addTest(new TestSuite(PositionHashTableTest.class));
		suite.addTest(new TestSuite(PositionTest.class));
		suite.addTest(new TestSuite(ComputerMoveMakerTest.class));
		suite.addTest(new TestSuite(GuiChessSquareTest.class));
		suite.addTest(new TestSuite(ComboScorerTest.class));
		suite.addTest(new TestSuite(PieceDevelopmentScorerTest.class));
		suite.addTest(new TestSuite(PawnScorerTest.class));
		suite.addTest(new TestSuite(ShortAlgebraicNotationTest.class));
		suite.addTest(new TestSuite(PgnGameTest.class));
		suite.addTest(new TestSuite(PgnFileTest.class));
		suite.addTest(new TestSuite(PgnGameManagerTest.class));
		suite.addTest(new TestSuite(FullTests.class));
		return suite;
  	}
}

