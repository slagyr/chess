package chess.gui;

import chess.move.AbstractMove;
import chess.piece.Piece;
import chess.board.ChessBoard;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class GuiLabeledBoard extends JPanel implements DragListener
{
	private static final int BOARDSIZE = 445;
	private static final int XFUDGE = 15;
	private static final int YFUDGE = 25;

	private JLabel dragLabel;
	private Container glass;
	private GuiChessSquare chosenSquare;
	private char dragType;
	private JPanel board;
	private HashMap squares = new HashMap(64);
	private static ChessBoard itsBoard;
	private static GuiMoveMaker moveMaker;

	public GuiLabeledBoard(Container glass, ChessBoard chessBoard)
	{
		super(new GridBagLayout());
		moveMaker = new GuiMoveMaker(this);
        this.glass = glass;
        itsBoard = chessBoard;
		JPanel rankLabels = createRankLabels();
		JPanel fileLabels = createFileLabels();
		JPanel gridPanel = createBoard();

		GuiUtils.addGrid(this, gridPanel, 1, 0, 1, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.CENTER);
		GuiUtils.addGrid(this, rankLabels, 0, 0, 1, 1, 5, 0, GridBagConstraints.BOTH, GridBagConstraints.CENTER);
		GuiUtils.addGrid(this, fileLabels, 0, 1, 2, 1, 5, 5, GridBagConstraints.BOTH, GridBagConstraints.CENTER);
	}

    public void setBoard(ChessBoard board)
    {
        itsBoard = board;
    }

	public void loadSquares(ChessBoard chessBoard)
	{
		Set theSquares = squares.keySet();
		for(Iterator i = theSquares.iterator(); i.hasNext(); )
		{
			String name = (String)i.next();
			Piece piece = chessBoard.getSquare(name).getOccupant();
			GuiChessSquare guiSquare = (GuiChessSquare)squares.get(name);
			guiSquare.setOccupant(piece.getTextType());
		}
	}

	private JPanel createRankLabels()
	{
		JPanel rankLabels = new JPanel(new GridBagLayout());
		GuiUtils.addGrid(rankLabels, new JLabel("8"), 0, 0, 1, 1, 0, 38, GridBagConstraints.BOTH, GridBagConstraints.SOUTH);
		GuiUtils.addGrid(rankLabels, new JLabel("7"), 0, 1, 1, 1, 0, 38, GridBagConstraints.BOTH, GridBagConstraints.SOUTH);
		GuiUtils.addGrid(rankLabels, new JLabel("6"), 0, 2, 1, 1, 0, 38, GridBagConstraints.BOTH, GridBagConstraints.SOUTH);
		GuiUtils.addGrid(rankLabels, new JLabel("5"), 0, 3, 1, 1, 0, 38, GridBagConstraints.BOTH, GridBagConstraints.SOUTH);
		GuiUtils.addGrid(rankLabels, new JLabel("4"), 0, 4, 1, 1, 0, 38, GridBagConstraints.BOTH, GridBagConstraints.SOUTH);
		GuiUtils.addGrid(rankLabels, new JLabel("3"), 0, 5, 1, 1, 0, 38, GridBagConstraints.BOTH, GridBagConstraints.SOUTH);
		GuiUtils.addGrid(rankLabels, new JLabel("2"), 0, 6, 1, 1, 0, 38, GridBagConstraints.BOTH, GridBagConstraints.SOUTH);
		GuiUtils.addGrid(rankLabels, new JLabel("1"), 0, 7, 1, 1, 0, 38, GridBagConstraints.BOTH, GridBagConstraints.SOUTH);
		return rankLabels;
	}

	private JPanel createFileLabels()
	{
		JPanel fileLabels = new JPanel(new GridBagLayout());
		GuiUtils.addGrid(fileLabels, new JLabel(" "), 0, 0, 1, 1, 5, 0, GridBagConstraints.BOTH, GridBagConstraints.CENTER);
		GuiUtils.addGrid(fileLabels, new JLabel("A"), 1, 0, 1, 1, 48, 0, GridBagConstraints.BOTH, GridBagConstraints.EAST);
		GuiUtils.addGrid(fileLabels, new JLabel("B"), 2, 0, 1, 1, 48, 0, GridBagConstraints.BOTH, GridBagConstraints.EAST);
		GuiUtils.addGrid(fileLabels, new JLabel("C"), 3, 0, 1, 1, 48, 0, GridBagConstraints.BOTH, GridBagConstraints.EAST);
		GuiUtils.addGrid(fileLabels, new JLabel("D"), 4, 0, 1, 1, 48, 0, GridBagConstraints.BOTH, GridBagConstraints.EAST);
		GuiUtils.addGrid(fileLabels, new JLabel("E"), 5, 0, 1, 1, 48, 0, GridBagConstraints.BOTH, GridBagConstraints.EAST);
		GuiUtils.addGrid(fileLabels, new JLabel("F"), 6, 0, 1, 1, 48, 0, GridBagConstraints.BOTH, GridBagConstraints.EAST);
		GuiUtils.addGrid(fileLabels, new JLabel("G"), 7, 0, 1, 1, 48, 0, GridBagConstraints.BOTH, GridBagConstraints.EAST);
		GuiUtils.addGrid(fileLabels, new JLabel("H"), 8, 0, 1, 1, 0, 0, GridBagConstraints.BOTH, GridBagConstraints.EAST);
		return fileLabels;
	}

	private JPanel createBoard()
	{
		GridLayout grid = new GridLayout(8, 8);
		JPanel gridPanel = new JPanel(grid);
		gridPanel.setPreferredSize(new Dimension(BOARDSIZE, BOARDSIZE));
		gridPanel.setBorder(new LineBorder(Color.black, 3));
		makeSquares(gridPanel);
		board = gridPanel;
		return gridPanel;
	}

	private void makeSquares(JPanel gridPanel)
	{
		squares.put("a8", makeSquare(gridPanel, "a8", true));
		squares.put("b8", makeSquare(gridPanel, "b8", false));
		squares.put("c8", makeSquare(gridPanel, "c8", true));
		squares.put("d8", makeSquare(gridPanel, "d8", false));
		squares.put("e8", makeSquare(gridPanel, "e8", true));
		squares.put("f8", makeSquare(gridPanel, "f8", false));
		squares.put("g8", makeSquare(gridPanel, "g8", true));
		squares.put("h8", makeSquare(gridPanel, "h8", false));

		squares.put("a7", makeSquare(gridPanel, "a7", false));
		squares.put("b7", makeSquare(gridPanel, "b7", true));
		squares.put("c7", makeSquare(gridPanel, "c7", false));
		squares.put("d7", makeSquare(gridPanel, "d7", true));
		squares.put("e7", makeSquare(gridPanel, "e7", false));
		squares.put("f7", makeSquare(gridPanel, "f7", true));
		squares.put("g7", makeSquare(gridPanel, "g7", false));
		squares.put("h7", makeSquare(gridPanel, "h7", true));

		squares.put("a6", makeSquare(gridPanel, "a6", true));
		squares.put("b6", makeSquare(gridPanel, "b6", false));
		squares.put("c6", makeSquare(gridPanel, "c6", true));
		squares.put("d6", makeSquare(gridPanel, "d6", false));
		squares.put("e6", makeSquare(gridPanel, "e6", true));
		squares.put("f6", makeSquare(gridPanel, "f6", false));
		squares.put("g6", makeSquare(gridPanel, "g6", true));
		squares.put("h6", makeSquare(gridPanel, "h6", false));

		squares.put("a5", makeSquare(gridPanel, "a5", false));
		squares.put("b5", makeSquare(gridPanel, "b5", true));
		squares.put("c5", makeSquare(gridPanel, "c5", false));
		squares.put("d5", makeSquare(gridPanel, "d5", true));
		squares.put("e5", makeSquare(gridPanel, "e5", false));
		squares.put("f5", makeSquare(gridPanel, "f5", true));
		squares.put("g5", makeSquare(gridPanel, "g5", false));
		squares.put("h5", makeSquare(gridPanel, "h5", true));

		squares.put("a4", makeSquare(gridPanel, "a4", true));
		squares.put("b4", makeSquare(gridPanel, "b4", false));
		squares.put("c4", makeSquare(gridPanel, "c4", true));
		squares.put("d4", makeSquare(gridPanel, "d4", false));
		squares.put("e4", makeSquare(gridPanel, "e4", true));
		squares.put("f4", makeSquare(gridPanel, "f4", false));
		squares.put("g4", makeSquare(gridPanel, "g4", true));
		squares.put("h4", makeSquare(gridPanel, "h4", false));

		squares.put("a3", makeSquare(gridPanel, "a3", false));
		squares.put("b3", makeSquare(gridPanel, "b3", true));
		squares.put("c3", makeSquare(gridPanel, "c3", false));
		squares.put("d3", makeSquare(gridPanel, "d3", true));
		squares.put("e3", makeSquare(gridPanel, "e3", false));
		squares.put("f3", makeSquare(gridPanel, "f3", true));
		squares.put("g3", makeSquare(gridPanel, "g3", false));
		squares.put("h3", makeSquare(gridPanel, "h3", true));

		squares.put("a2", makeSquare(gridPanel, "a2", true));
		squares.put("b2", makeSquare(gridPanel, "b2", false));
		squares.put("c2", makeSquare(gridPanel, "c2", true));
		squares.put("d2", makeSquare(gridPanel, "d2", false));
		squares.put("e2", makeSquare(gridPanel, "e2", true));
		squares.put("f2", makeSquare(gridPanel, "f2", false));
		squares.put("g2", makeSquare(gridPanel, "g2", true));
		squares.put("h2", makeSquare(gridPanel, "h2", false));

		squares.put("a1", makeSquare(gridPanel, "a1", false));
		squares.put("b1", makeSquare(gridPanel, "b1", true));
		squares.put("c1", makeSquare(gridPanel, "c1", false));
		squares.put("d1", makeSquare(gridPanel, "d1", true));
		squares.put("e1", makeSquare(gridPanel, "e1", false));
		squares.put("f1", makeSquare(gridPanel, "f1", true));
		squares.put("g1", makeSquare(gridPanel, "g1", false));
		squares.put("h1", makeSquare(gridPanel, "h1", true));
	}

	private Component makeSquare(JPanel gridPanel, String name, boolean white)
	{
		GuiChessSquare square = new GuiChessSquare(name);
		square.addMouseListener(moveMaker);
		square.addMouseMotionListener(moveMaker);
		if(white)
			square.setBackground(GuiChessSquare.WHITE);
		else
			square.setBackground(GuiChessSquare.BLACK);
		gridPanel.add(square);
		return square;
	}

	public AbstractMove makeBorders(AbstractMove move, AbstractMove lastMove)
	{
		String name;
		if (lastMove.isValid())
		{
			name = lastMove.getStartSquare().toString();
			((GuiChessSquare)squares.get(name)).removeBorder();
			name = lastMove.getEndSquare().toString();
			((GuiChessSquare)squares.get(name)).removeBorder();
		}
		if (move.isValid())
		{
			name = move.getStartSquare().toString();
			((GuiChessSquare)squares.get(name)).addBorder();
			name = move.getEndSquare().toString();
			((GuiChessSquare)squares.get(name)).addBorder();
		}
		return move;
	}

	public void addBorder()
	{
		board.setBorder(new LineBorder(Color.red, 3));
		board.repaint();
	}

	public void drag(int x, int y)
	{
		dragLabel.setLocation(x - XFUDGE, y - YFUDGE);
		glass.setVisible(true);
	}

	public void startDrag(int x, int y, GuiChessSquare square)
	{
		chosenSquare = square;
		dragType = itsBoard.getSquare(square.getName()).getOccupant().getTextType();
		dragLabel = new JLabel(square.getOccupant());
		square.setOccupant(' ');
		glass.add(dragLabel);
		dragLabel.setLocation(x - XFUDGE, y - YFUDGE);
	}

	public void stopDrag()
	{
		chosenSquare.setOccupant(dragType);
		glass.removeAll();
		glass.setVisible(false);
	}

    public GuiMoveMaker getMoveMaker()
    {
        return moveMaker;
    }
}
