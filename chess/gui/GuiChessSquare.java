package chess.gui;

import chess.util.Props;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.io.*;

public class GuiChessSquare extends JPanel
{
	public static ImageIcon wPawn   = createImage(Props.get("image.white.pawn"));
	public static ImageIcon wKnight = createImage(Props.get("image.white.knight"));
	public static ImageIcon wBishop = createImage(Props.get("image.white.bishop"));
	public static ImageIcon wRook   = createImage(Props.get("image.white.rook"));
	public static ImageIcon wQueen  = createImage(Props.get("image.white.queen"));
	public static ImageIcon wKing   = createImage(Props.get("image.white.king"));
	public static ImageIcon bPawn   = createImage(Props.get("image.black.pawn"));
	public static ImageIcon bKnight = createImage(Props.get("image.black.knight"));
	public static ImageIcon bBishop = createImage(Props.get("image.black.bishop"));
	public static ImageIcon bRook   = createImage(Props.get("image.black.rook"));
	public static ImageIcon bQueen  = createImage(Props.get("image.black.queen"));
	public static ImageIcon bKing   = createImage(Props.get("image.black.king"));
	
	public static Color WHITE = new Color(241, 239, 199);
	public static Color BLACK = new Color(16, 74, 15);

	private String itsName;
	private ImageIcon itsOccupant;

	public GuiChessSquare(String name)
	{
		itsName = name;
		itsOccupant = null;
	}
	
	public String getName()
	{
		return itsName;
	}
	
	public void setOccupant(char c)
	{
		ImageIcon img = translateToPiece(c);
		itsOccupant = img;
		removeAll();
		if(img != null)
			add(new JLabel(img));
		repaint();	
	}
	
	public ImageIcon getOccupant()
	{
		return itsOccupant;
	}
	
	private static ImageIcon createImage(String filename)
	{
		File imgFile = Props.findFileInClasspath(filename);
		return new ImageIcon(imgFile.getAbsolutePath());
	}
	
	public void addBorder()
	{
		setBorder(new LineBorder(Color.red, 2));
	}
	
	public void removeBorder()
	{
		setBorder(new LineBorder(Color.black, 0));
	}
	
	public static ImageIcon translateToPiece(char c)
	{
		ImageIcon icon = null;
		if(c == 'P')
			icon = wPawn;
		else if(c == 'p')
			icon = bPawn;
		else if(c == 'N')
			icon = wKnight;	
		else if(c == 'n')
			icon = bKnight;	
		else if(c == 'B')
			icon = wBishop;	
		else if(c == 'b')
			icon = bBishop;	
		else if(c == 'R')
			icon = wRook;	
		else if(c == 'r')
			icon = bRook;	
		else if(c == 'Q')
			icon = wQueen;	
		else if(c == 'q')
			icon = bQueen;	
		else if(c == 'K')
			icon = wKing;	
		else if(c == 'k')
			icon = bKing;	
			
		return icon;		
	}
}
