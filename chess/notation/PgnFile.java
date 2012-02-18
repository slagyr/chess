package chess.notation;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Iterator;

public class PgnFile
{
    private static final String gameDivider = "[Event \"";

    private String filename;
    private File file;
    private List games;

    public PgnFile(String filename)
    {
        this(new File(filename));
    }

    public PgnFile(File file)
    {
        this.file = file;
        filename = file.getName();
        games = new ArrayList();
        if(file.exists())
            parseFile();
    }

    public List getGameList()
    {
        return games;
    }

    public PgnGame getGame(int index)
    {
        return (PgnGame)games.get(index - 1);
    }

    public void addGame(PgnGame game)
    {
        games.add(game);
    }

    public void writeFile()
    {
        if(file.exists())
            file.delete();
        StringBuffer buff = new StringBuffer(1000);
        for (Iterator i = games.iterator(); i.hasNext();)
        {
            PgnGame game = (PgnGame) i.next();
            buff.append(game.toPgnFormat()).append('\n');
        }
        try
        {
            FileWriter writer = new FileWriter(filename);
            writer.write(buff.toString());
            writer.close();
        }
        catch (IOException e)
        {
            System.err.println(e);
        }
    }

    private void parseFile()
    {
        String fileContent = getFileContents();
        int beginIndex = fileContent.indexOf(gameDivider);
        int endIndex = -1;
        if(beginIndex != -1)
            endIndex = fileContent.indexOf(gameDivider, beginIndex + 1);
        while(beginIndex != -1)
        {
            if(endIndex != -1)
            {
                games.add(new PgnGame(fileContent.substring(beginIndex, endIndex)));
                beginIndex = endIndex;
                endIndex = fileContent.indexOf(gameDivider, beginIndex + 1);
            }
            else if(beginIndex != -1)
            {
                games.add(new PgnGame(fileContent.substring(beginIndex)));
                break;
            }
        }
    }

    private String getFileContents()
    {
        StringBuffer buff = new StringBuffer(1000);
        try
        {
            ArrayList      lines = new ArrayList();
            FileReader     reader = new FileReader(file);
            BufferedReader buffReader = new BufferedReader(reader);

            String line = buffReader.readLine();
            while(line != null)
            {
                buff.append(line).append('\n');
                line = buffReader.readLine();
            }
            buffReader.close();
            reader.close();
        }
        catch (IOException e)
        {
            System.err.println(e);
        }

        return buff.toString();
    }
}
