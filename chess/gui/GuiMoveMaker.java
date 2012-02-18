package chess.gui;

import chess.board.ChessBoard;
import chess.move.*;
import chess.player.*;

import java.io.*;
import java.awt.event.*;

public class GuiMoveMaker extends MoveMaker implements MouseListener, MouseMotionListener
{
	private DragListener itsDragListener;
    private Player movingPlayer;
    private State itsState = new IdleState();
    private State waitingForStartSquare = new WaitingForStartSquare();
//    private State waitingForEndSquareClick = new WaitingForEndSquareClick();
    private State waitingForEndSquareRelease = new WaitingForEndSquareRelease();
    private State idleState = new IdleState();

    private String lastEnteredSquare = null;
    private String selectedStartSquare = null;
    private String selectedEndSquare = null;

	public GuiMoveMaker(DragListener dragListener)
	{
		itsDragListener = dragListener;
	}

    public void gotAMove()
    {
        AbstractMove move = movingPlayer.movePiece(selectedStartSquare, selectedEndSquare);
		selectedStartSquare = null;
		selectedEndSquare = null;

		if (move.getType().equals(MoveGlobals.PROMOTION))
			((PromotionMove)move).makePromotionPiece(true);

        itsMoveListener.acceptMove(move);
    }

	public void makeMove(ChessBoard board, Player player)
	{
        movingPlayer = player;
        changeState(waitingForStartSquare);
	}
	
	public void mouseClicked(MouseEvent e)
	{
	}
	
	public void mouseEntered(MouseEvent e)
	{
		lastEnteredSquare = ((GuiChessSquare)e.getComponent()).getName();
	}
	
	public void mouseExited(MouseEvent e)
	{
	}
	
	public void mousePressed(MouseEvent e)
	{
		GuiChessSquare s = (GuiChessSquare)e.getComponent();
		int x = s.getX();
		int y = s.getY();

        itsDragListener.startDrag(e.getX() + x, e.getY() + y, s);
        itsState.pressed(s.getName());
	}
	
	public void mouseReleased(MouseEvent e)
	{
		itsDragListener.stopDrag();
        itsState.released(lastEnteredSquare);
	}
	
	public void mouseDragged(MouseEvent e)
	{
		GuiChessSquare s = (GuiChessSquare)e.getComponent();
		int x = s.getX();
		int y = s.getY();
		itsDragListener.drag(e.getX() + x, e.getY() + y);
	}
	
	public void mouseMoved(MouseEvent e)
	{
	}

    // Here begins the state machine to collect move

    private abstract class State
    {
        public abstract String name();
        public void click(String square){System.err.println("State error: clicked(). (" + itsState.name() + ")");};
        public void pressed(String square){System.err.println("State error: pressed(). (" + itsState.name() + ")");};
        public void released(String square){System.err.println("State error: released(). (" + itsState.name() + ")");};
    }

    public void changeState(State state)
    {
        itsState = state;
    }

    private class WaitingForStartSquare extends State
    {
        public String name()
        {
            return "WaitingForStartSquare";
        }

//        public void click(String square)
//        {
//            changeState(waitingForEndSquareClick);
//            selectedStartSquare = square;
//        }

        public void pressed(String square)
        {
            changeState(waitingForEndSquareRelease);
            selectedStartSquare = square;
        }

        public void released(String square){};
    }

//    private class WaitingForEndSquareClick extends State
//    {
//        public String name()
//        {
//            return "WaitingForEndSquareClick";
//        }
//
//        public void click(String square)
//        {
//            changeState(idleState);
//            selectedEndSquare = square;
//            gotAMove();
//        }
//
//        public void pressed(String square)
//        {
//            click(square);
//        }
//    }

    private class WaitingForEndSquareRelease extends State
    {
        public String name()
        {
            return "WaitingForEndSquareRelease";
        }

        public void released(String square)
        {
            changeState(idleState);
            selectedEndSquare = square;
            gotAMove();
        }
    }

    private class IdleState extends State
    {
        public String name()
        {
            return "IdleState";
        }

        public void click(String square){};
        public void pressed(String square){};
        public void released(String square){};
    }
}
