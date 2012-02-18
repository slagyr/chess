package chess.board;

public class Position
{
	private String itsCode;
	private int itsScore;

	public Position(String code, int score)
	{
		itsCode = code;
		itsScore = score;
	}
	
	public String getCode()
	{
		return itsCode;
	}
	
	public int getScore()
	{
		return itsScore;
	}
	
	public String toString()
	{
		return itsScore + "";
	}
}
