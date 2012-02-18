package chess.gui;

import junit.framework.*;
import java.awt.*;
import javax.swing.*;

import chess.gui.GuiChessSquare;

public class GuiChessSquareTest extends TestCase
{
	public GuiChessSquareTest(String name)
	{
		super(name);
	}
	
	public void testTranslateToPiece()
	{
		assertEquals(GuiChessSquare.wPawn, GuiChessSquare.translateToPiece('P'));
		assertEquals(GuiChessSquare.bPawn, GuiChessSquare.translateToPiece('p'));
		assertEquals(null, GuiChessSquare.translateToPiece(' '));
	}
	
	public void testSetOccupant()
	{
		GuiChessSquare square = new GuiChessSquare("e4");
		square.setOccupant('p');
		assertEquals(GuiChessSquare.bPawn, square.getOccupant());
	}
}
