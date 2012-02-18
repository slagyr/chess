package chess.gui;

import chess.player.Player;
import chess.move.AbstractMove;
import chess.piece.PieceGlobals;
import chess.notation.PgnGameManager;
import chess.game.AbstractChessGame;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Vector;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Iterator;

public class GuiNotationPanel extends JPanel implements ActionListener, ListSelectionListener
{
    private static final int WIDTH = 175;
    private static final int HEIGHT = 400;
    private static final int ROWS = 23;
    private static final String FORWARD = "forward";
    private static final String BACK = "back";
    private static final int SCROLL_RATIO = 20;
    private static final int SCROLL_HEIGHT = HEIGHT;
    private static final int SCROLL_FUDGE = 30;
    private static final int SCROLL_ADJUST = 200;

    private JList numberList;
    private JList whiteList;
    private JList blackList;
    private JButton back;
    private JButton forward;
    private JLabel label;
    private JScrollPane scrollPane;
    private String title = "The Game";
    private PgnGameManager itsManager;
    private AbstractChessGame itsGame;
    private boolean selectionListen = true;

    public GuiNotationPanel(AbstractChessGame game)
    {
        super(new GridBagLayout());

        itsGame = game;

        label = new JLabel(title);
        scrollPane = createScrollList();

        back = new JButton("<");
        JPanel backPanel = createButtonPanel(back);
        setupButtonActions(back, BACK);
        forward = new JButton(">");
        JPanel forwardPanel = createButtonPanel(forward);
        setupButtonActions(forward, FORWARD);

        GuiUtils.addGrid(this, label, 0, 0, 2, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.CENTER);
        GuiUtils.addGrid(this, scrollPane, 0, 1, 2, 1, 0, 0, GridBagConstraints.BOTH, GridBagConstraints.CENTER);
        GuiUtils.addGrid(this, backPanel, 0, 2, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.EAST);
        GuiUtils.addGrid(this, forwardPanel, 1, 2, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
    }

    public void setGameManager(PgnGameManager game)
    {
        itsManager = game;
        loadGame();
    }

    public void loadGame()
    {
        String[] whiteListData = itsManager.getMovesInNotation(PieceGlobals.WHITE);
        String[] blackListData = itsManager.getMovesInNotation(PieceGlobals.BLACK);
        whiteList.setListData(whiteListData);
        blackList.setListData(fitToSize(whiteListData.length, blackListData));
        numberList.setListData(makeNumberList(whiteListData.length));
        makeTitle();
    }

    private void loadMoves()
    {
        String[] whiteListData = convertToStringArray(itsManager.getWhitesMoves());
        String[] blackListData = convertToStringArray(itsManager.getBlacksMoves());
        whiteList.setListData(whiteListData);
        blackList.setListData(fitToSize(whiteListData.length, blackListData));
        numberList.setListData(makeNumberList(whiteListData.length));
    }

    public void valueChanged(ListSelectionEvent event)
    {
        if(selectionListen)
        {
            selectionListen = false;
            JList list = (JList)event.getSource();
            int index = 0;
            if(list == blackList)
            {
                whiteList.clearSelection();
                index = blackList.getSelectedIndex() * 2 + 1;
            }
            else if(list == whiteList)
            {
                blackList.clearSelection();
                index = whiteList.getSelectedIndex() * 2;
            }
            else
            {
                numberList.clearSelection();
                index = itsManager.getIndex();
            }

            while(index > itsManager.getIndex())
                anotherMove(FORWARD, false);
            while(index < itsManager.getIndex())
                anotherMove(BACK, false);
            itsGame.showBoard();

            selectionListen = true;
        }
    }

    public void actionPerformed(ActionEvent event)
    {
        String command = event.getActionCommand();
        anotherMove(command, true);
    }

    public void upDate()
    {
        loadMoves();
        anotherMove(FORWARD, true);
    }

    private void anotherMove(String direction, boolean display)
    {
        AbstractMove move = (direction.equals(FORWARD)) ? itsManager.getNextMove() : itsManager.getCurrentMove_bumpBack();
        if(move.isValid())
        {
            Player player;
            if(move.getPiece().getColor().equals(PieceGlobals.WHITE))
                player = itsManager.getWhitePlayer();
            else
                player = itsManager.getBlackPlayer();
            if(direction.equals(FORWARD))
                doMove(player, move);
            else
                undoMove(player, move);
            if(display)
            {
                itsGame.showBoard();
                highlightItem(itsManager.getIndex());
            }
        }
    }

    private JPanel createButtonPanel(JButton button)
    {
        JPanel backPanel = new JPanel();
        backPanel.setPreferredSize(new Dimension(WIDTH/2, 40));
        backPanel.add(button);
        return backPanel;
    }

    private JScrollPane createScrollList()
    {
        numberList = new JList();
        numberList.setFixedCellWidth(30);
        numberList.setVisibleRowCount(ROWS);
        numberList.addListSelectionListener(this);


        whiteList = new JList();
        whiteList.addListSelectionListener(this);
        whiteList.setFixedCellWidth((WIDTH-70)/2);
        whiteList.setVisibleRowCount(ROWS);

        blackList = new JList();
        blackList.addListSelectionListener(this);
        blackList.setFixedCellWidth((WIDTH-70)/2);
        blackList.setVisibleRowCount(ROWS);

        JPanel listPanel = new JPanel();
        listPanel.add(numberList);
        listPanel.add(whiteList);
        listPanel.add(blackList);
        listPanel.setBackground(Color.white);

        JScrollPane scroll = new JScrollPane(listPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        return scroll;
    }

    private void setupButtonActions(JButton button, String command)
    {
        button.addActionListener(this);
        button.setActionCommand(command);
    }

    private Object[] fitToSize(int size, String[] array)
    {
        for(int i = 0; i < array.length; i++)
        {
            if(array[i] == null)
                array[i] = " ";
        }
        if(array.length != size)
        {
            ArrayList temp = new ArrayList();
            for(int i = 0; i < array.length; i++)
                temp.add(array[i]);
            while(temp.size() != size)
                temp.add(" ");
            return temp.toArray();
        }
        else
            return array;
    }

    private Object[] makeNumberList(int size)
    {
        java.util.List temp = new ArrayList();
        for(int i = 1; i <= size; i++)
            temp.add(i + ".");

        return temp.toArray();
    }

    private void makeTitle()
    {
        StringBuffer buff = new StringBuffer(100);
        buff.append(itsManager.getWhite()).append(" - ").append(itsManager.getBlack());
        title = buff.toString();
        label.setText(title);
    }

    private void highlightItem(int index)
    {
        selectionListen = false;
        whiteList.clearSelection();
        blackList.clearSelection();
        if(index >= 0)
        {
            int quotient = index / 2;
            int remainder = index % 2;
            if(remainder == 0)
                whiteList.setSelectedIndex(quotient);
            else
                blackList.setSelectedIndex(quotient);
            adjustScrollbar(quotient);
        }
        selectionListen = true;
    }

    private void adjustScrollbar(int index)
    {
        int currentPosition = scrollPane.getVerticalScrollBar().getValue();
        int indexPosition = index * SCROLL_RATIO;
        if(indexPosition < currentPosition + SCROLL_FUDGE ||
           indexPosition > currentPosition + SCROLL_HEIGHT - SCROLL_FUDGE)
            scrollPane.getVerticalScrollBar().setValue(indexPosition - SCROLL_ADJUST);
    }

    private void doMove(Player player, AbstractMove move)
    {
        player.findAllMoves(false);
        player.movePiece(move);
        player.dealWithPromotion(move);
        player.findAllMoves(false);
    }

    private void undoMove(Player player, AbstractMove move)
    {
        player.reverseMove(move);
        player.findAllMoves(false);
    }

    private String[] convertToStringArray(ArrayList list)
    {
        String[] array = new String[list.size()];
        for(int i = 0; i < list.size(); i++)
            array[i] = (String)list.get(i);

        return array;
    }
}
