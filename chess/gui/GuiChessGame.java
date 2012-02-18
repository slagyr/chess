package chess.gui;

import chess.piece.PieceGlobals;
import chess.player.*;
import chess.scoring.*;
import chess.notation.PgnGameManagerTest;
import chess.notation.*;
import chess.move.*;
import chess.game.AbstractChessGame;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.plaf.*;
import java.util.*;

public class GuiChessGame extends AbstractChessGame
{
	private JFrame itsFrame;
	private Container panel;
	private JPanel board;
	private GuiLabeledBoard labeledBoard;
	private AbstractMove lastMove = new IllegalMove();
	private static GuiMoveMaker moveMaker;
    private PgnGameManager gameManager;
    private GuiNotationPanel notation;

	public GuiChessGame()
	{
	}

	public static void main(String[] args)
	{
		new GuiChessGame().start(args);
	}

	private void start(String[] args)
	{
		try
		{
			itsFrame = createFrame("Chess");

			Container glass = (Container)itsFrame.getRootPane().getGlassPane();

			panel = itsFrame.getContentPane();
			panel.setLayout(new GridBagLayout());

			labeledBoard = new GuiLabeledBoard(glass, itsBoard);
            notation = new GuiNotationPanel(this);

			GuiUtils.addGrid(panel, labeledBoard, 0, 0, 1, 1, 0, 0, GridBagConstraints.BOTH, GridBagConstraints.CENTER);
			GuiUtils.addGrid(panel, notation, 2, 0, 1, 1, 20, 0, GridBagConstraints.BOTH, GridBagConstraints.CENTER);

            itsFrame.setJMenuBar(new GuiMenuBar(this));

			itsFrame.pack();
			itsFrame.setLocation(200, 100);
            itsFrame.show();

            gameManager = new PgnGameManager(new ShortAlgebraicNotation());
            notation.setGameManager(gameManager);
        }
		catch(Throwable e)
		{
			e.printStackTrace();
		}
	}

    public void newGame()
    {
        createBoard();
        labeledBoard.setBoard(itsBoard);
        moveMaker = labeledBoard.getMoveMaker();
        moveMaker.setMoveListener(this);
        prepareGame();
        loadSquares();

        Thread t = new Thread()
        {
            public void run(){play();}
        };
        t.start();
    }

    public void reviewGame()
    {
        gameManager.setGameData(PgnGameManagerTest.validGame);
	    createBoard();
        labeledBoard.setBoard(itsBoard);
        notation.loadGame();
        loadSquares();
    }

	protected JFrame createFrame(String title)
	{
		JFrame frame= new JFrame(title);
		frame.setBackground(SystemColor.control);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setIconImage(GuiChessSquare.wKnight.getImage());
		return frame;
	}

	public void makeBorders(AbstractMove move)
	{
		lastMove = labeledBoard.makeBorders(move, lastMove);
	}

	public void addBorder()
	{
        labeledBoard.addBorder();
	}

	private void loadSquares()
	{
		labeledBoard.loadSquares(itsBoard);
		itsFrame.show();
	}

    public void createBoard()
    {
        if(gameManager.existingGame())
            itsBoard = gameManager.getBoard();
        else
            super.createBoard();
    }

    protected void createPlayer1()
    {
        if(gameManager.existingGame())
            itsPlayer1 = gameManager.getWhitePlayer();
        else
        {
            Scorer scorer1 = new CoverageScorer();
            MoveFinder moveFinder1 = new MoveFinder(scorer1, 2);
            MoveMaker moveMaker1 = new ComputerMoveMaker(moveFinder1, this);
    //		itsPlayer1 = new Player(PieceGlobals.WHITE, moveMaker1);
            itsPlayer1 = new Player(PieceGlobals.WHITE, moveMaker);
        }
    }

    protected void createPlayer2()
    {
        if(gameManager.existingGame())
            itsPlayer2 = gameManager.getBlackPlayer();
        else
        {
            ComboScorer scorer2 = new ComboScorer();
            scorer2.addScorer(new CoverageScorer(), 1);
            scorer2.addScorer(new MaterialScorer(), 5);
            scorer2.addScorer(new PieceDevelopmentScorer(), 1);
            scorer2.addScorer(new PawnScorer(), 1);
            MoveFinder moveFinder2 = new MoveFinder(scorer2, 2);
            MoveMaker moveMaker2 = new ComputerMoveMaker(moveFinder2, this);
            itsPlayer2 = new Player(PieceGlobals.BLACK, moveMaker2);
    //		itsPlayer2 = new Player(PieceGlobals.BLACK, moveMaker);
        }
    }

    protected void preGameDetails()
    {
		loadSquares();
    }

    protected void postGameDetails()
    {
		addBorder();
		loadSquares();
    }

    protected void preMoveDetails()
    {
		loadSquares();
    }

    protected void postMoveDetails(AbstractMove move)
    {
        makeBorders(move);
        gameManager.addMove(move);
        notation.upDate();
    }

    public void showBoard()
    {
        loadSquares();
    }
}
