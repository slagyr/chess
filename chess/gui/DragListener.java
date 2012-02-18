package chess.gui;

import chess.*;
public interface DragListener
{
	public abstract void drag(int x, int y);
	public abstract void startDrag(int x, int y, GuiChessSquare square);
	public abstract void stopDrag();
}
