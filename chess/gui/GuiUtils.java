package chess.gui;

import java.awt.*;

public class GuiUtils
{
	public static void addGrid(Container p, Component co, int x, int y, int w, int h, int px, int py, int fill, int anchor)
	{
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = w;
		c.gridheight = h;
		c.ipadx = px;
		c.ipady = py;
		c.fill = fill;
		c.anchor = anchor;
		p.add(co, c);
	}
}
