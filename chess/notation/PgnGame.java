package chess.notation;

import java.util.*;

public class PgnGame
{
    private String input;
    private String event;
    private String site;
    private String date;
    private String round;
    private String white;
    private String black;
    private String result;
    private String eco;
    private String whiteElo;
    private String blackElo;
    private String plyCount;
    private String eventDate;

    protected ArrayList whitesMoves;
    protected ArrayList blacksMoves;

    public PgnGame()
    {
        event = "Casual Game";
        site = "XPChess";
        date = todayToString();
        round = "N/A";
        white = "white player";
        black = "black player";
        result = "N/A";
        whiteElo = "N/A";
        blackElo = "N/A";
        plyCount = "0";
        eventDate = todayToString();

        whitesMoves = new ArrayList();
        blacksMoves = new ArrayList();
    }

    public PgnGame(String input)
    {
        this();
        this.input = input;
        parseInput();
    }

    public void setGameData(String input)
    {
        this.input = input;
        parseInput();
    }

    public String getEvent()
    {
        return event;
    }

    public String getSite()
    {
        return site;
    }

    public String getDate()
    {
        return date;
    }

    public String getRound()
    {
        return round;
    }

    public String getWhite()
    {
        return white;
    }

    public String getBlack()
    {
        return black;
    }

    public String getResult()
    {
        return result;
    }

    public String getEco()
    {
        return eco;
    }

    public String getWhiteElo()
    {
        return whiteElo;
    }

    public String getBlackElo()
    {
        return blackElo;
    }

    public String getPlyCount()
    {
        return plyCount;
    }

    public String getEventDate()
    {
        return eventDate;
    }

    public ArrayList getBlacksMoves()
    {
        return blacksMoves;
    }

    public ArrayList getWhitesMoves()
    {
        return whitesMoves;
    }

    public void setEvent(String event)
    {
        this.event = event;
    }

    public void setSite(String site)
    {
        this.site = site;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public void setRound(String round)
    {
        this.round = round;
    }

    public void setWhite(String white)
    {
        this.white = white;
    }

    public void setBlack(String black)
    {
        this.black = black;
    }

    public void setResult(String result)
    {
        this.result = result;
    }

    public void setEco(String eco)
    {
        this.eco = eco;
    }

    public void setWhiteElo(String whiteElo)
    {
        this.whiteElo = whiteElo;
    }

    public void setBlackElo(String blackElo)
    {
        this.blackElo = blackElo;
    }

    public void setPlyCount(String plyCount)
    {
        this.plyCount = plyCount;
    }

    public void setEventDate(String eventDate)
    {
        this.eventDate = eventDate;
    }

    public String toString()
    {
        return white + " -vs- " + black + " : " + date;
    }

    public void addWhiteMove(String move)
    {
        whitesMoves.add(move);
    }

    public void addBlackMove(String move)
    {
        blacksMoves.add(move);
    }

    public String toPgnFormat()
    {
        StringBuffer buff = new StringBuffer(1000);
        buff.append("[Event \"").append(event).append("\"]\n");
        buff.append("[Site \"").append(site).append("\"]\n");
        buff.append("[Date \"").append(date).append("\"]\n");
        buff.append("[Round \"").append(round).append("\"]\n");
        buff.append("[White \"").append(white).append("\"]\n");
        buff.append("[Black \"").append(black).append("\"]\n");
        buff.append("[Result \"").append(result).append("\"]\n");
        buff.append("[ECO \"").append(eco).append("\"]\n");
        buff.append("[WhiteElo \"").append(whiteElo).append("\"]\n");
        buff.append("[BlackElo \"").append(blackElo).append("\"]\n");
        buff.append("[PlyCount \"").append(whitesMoves.size() + blacksMoves.size()).append("\"]\n");
        buff.append("[EventDate \"").append(eventDate).append("\"]\n");
        for(int i = 0; i < whitesMoves.size(); i++)
        {
            if(i % 6 == 0)
                buff.append('\n');
            buff.append(i + 1).append(". ");
            buff.append(whitesMoves.get(i)).append(' ');
            if(blacksMoves.size() > i)
                buff.append(blacksMoves.get(i)).append(' ');
        }
        if(isResult(result))
            buff.append(result).append(' ');
        buff.append("\n");
        return buff.toString();
    }

    public boolean isGameFinished()
    {
        return isResult(result);
    }

    private void parseInput()
    {
        event = getDetail("Event");
        site = getDetail("Site");
        date = getDetail("Date");
        round = getDetail("Round");
        white = getDetail("White");
        black = getDetail("Black");
        result = getDetail("Result");
        eco = getDetail("ECO");
        whiteElo = getDetail("WhiteElo");
        blackElo = getDetail("BlackElo");
        plyCount = getDetail("PlyCount");
        eventDate = getDetail("EventDate");

        parseMoves();
    }

    private void parseMoves()
    {
        int movesIndex = input.indexOf("\n1.");
        StringTokenizer tokens = new StringTokenizer(input.substring(movesIndex));
        for(int i = 0; tokens.hasMoreTokens(); i++)
        {
            int mod = i % 3;
            String move = tokens.nextToken();
            if(! isResult(move))
            {
                if(mod == 1)
                    whitesMoves.add(move);
                if(mod == 2)
                    blacksMoves.add(move);
            }
            else
            {
                result = move;
                return;
            }
        }
    }

    private boolean isResult(String str)
    {
        if(str.equals("1-0") || str.equals("0-1") || str.equals("1/2-1/2"))
            return true;
        return false;
    }

    private String getDetail(String detail)
    {
        int detailIndex = input.indexOf("[" + detail);
        if(detailIndex >= 0)
        {
            int firstQuote = input.indexOf("\"", detailIndex);
            int secondQuote = input.indexOf("\"", firstQuote + 1);
            return input.substring(firstQuote + 1, secondQuote);
        }
        return "?";
    }

    private String todayToString()
    {
        GregorianCalendar today = new GregorianCalendar();
        StringBuffer buff = new StringBuffer();
        buff.append(today.get(Calendar.YEAR)).append('.');
        buff.append(today.get(Calendar.MONTH) + 1).append('.');
        buff.append(today.get(Calendar.DAY_OF_MONTH));
        return buff.toString();
    }
}
