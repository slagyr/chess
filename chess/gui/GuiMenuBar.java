package chess.gui;

import chess.game.AbstractChessGame;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GuiMenuBar extends JMenuBar implements ActionListener
{
    private JMenu gameMenu;
    private AbstractChessGame itsGame;

    public GuiMenuBar(AbstractChessGame game)
    {
        super();
        itsGame = game;
        add(createGameMenu());
    }

    public void actionPerformed(ActionEvent e)
    {
        String cmd = e.getActionCommand();
        if(cmd.equals("New"))
            itsGame.newGame();
        else if( cmd.equals("Review"))
            itsGame.reviewGame();
    }

    private JMenu createGameMenu()
    {
        gameMenu = new JMenu("Game");
        JMenuItem newGameItem = new JMenuItem("New");
        JMenuItem reviewGameItem = new JMenuItem("Review");
        newGameItem.addActionListener(this);
        reviewGameItem.addActionListener(this);
        gameMenu.add(newGameItem);
        gameMenu.add(reviewGameItem);
        return gameMenu;
    }
}
