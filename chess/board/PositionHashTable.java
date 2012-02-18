package chess.board;

import java.util.*;

public class PositionHashTable
{
	private ArrayList[] itsPositions;
	private Position itsMarker;

	public PositionHashTable()
	{
		itsPositions = new ArrayList[256];
		initializePositions();
		itsMarker = null;
	}
	
	public void add(String code, int score)
	{
		int hash = findHash(code);
		if (itsPositions[hash] == null)
			itsPositions[hash] = new ArrayList();
		putInBucket(code, score, itsPositions[hash]);	
	}
	
	public boolean has(String code)
	{	
		itsMarker = null;
		int hash = findHash(code);
		ArrayList bucket = itsPositions[hash];
		if(bucket != null)
			findInBucket(bucket, code);
		if(itsMarker == null)
			return false;
		else
			return true;	
	}
	
	public int getScore()
	{
		return itsMarker.getScore();
	}
	
	public int findHash(String code)
	{
		int size = code.length();
		byte first = 0;
		byte second = 0;
		byte third = 0;
		byte fourth = 0;
		byte fifth = 0;
		byte sixth = 0;
		byte seventh = 0;
		byte eigth = 0;
		for(int i = 0; i < size; i += 8)
			first += code.charAt(i);
		for(int i = 1; i < size; i += 8)
			second += code.charAt(i);
		for(int i = 2; i < size; i += 8)
			third += code.charAt(i);
		for(int i = 3; i < size; i += 8)
			fourth += code.charAt(i);
		for(int i = 4; i < size; i += 8)
			fifth += code.charAt(i);
		for(int i = 5; i < size; i += 8)
			sixth += code.charAt(i);
		for(int i = 6; i < size; i += 8)
			seventh += code.charAt(i);
		for(int i = 7; i < size; i += 8)
			eigth += code.charAt(i);
		int hash = (byte)(first + (2 * second) + (3 * third) + (4 * fourth) + (5 * fifth)
						  + (6 * sixth) + (7 * seventh) + (8 * eigth));
		return ( (hash < 0) ? ((hash * -1) + 127) : hash);
		
	}
	
	private void initializePositions()
	{
		for(int i = 0; i < 256; i++)
		{
			itsPositions[i] = null;
		}
	}
	
	private void putInBucket(String code, int score, ArrayList bucket)
	{
		boolean placed = false;
		int last = bucket.size() == 0 ? 0 : bucket.size() - 1;
		int first = 0;
		int middle = 0;
		while (! placed)
		{
			middle = (last + first)/2;
			if (middle == first || middle == last)
			{
				if(bucket.size() != 0 && code.compareTo(((Position)bucket.get(last)).getCode()) > 0)
					bucket.add(last + 1, new Position(code, score));
				else if(bucket.size() != 0 && code.compareTo(((Position)bucket.get(first)).getCode()) > 0)
					bucket.add(first + 1, new Position(code, score));
				else
					bucket.add(first, new Position(code, score));
				placed = true;
			}
			else if (code.compareTo(((Position)bucket.get(middle)).getCode()) < 0)
				last = middle;
			else
				first = middle;
		}
		
	}
	
	private void findInBucket(ArrayList bucket, String code)
	{
		boolean searching = true;
		int last = bucket.size() == 0 ? 0 : bucket.size() - 1;
		int first = 0;
		int middle = 0;
		while (searching)
		{
			middle = (last + first)/2;
			Position suspect = (Position)bucket.get(middle);
			if (middle == first || middle == last)
			{
				if( code.equals(suspect.getCode()) )
					itsMarker = suspect;
				else
				{
					suspect = (Position)bucket.get(last);
					if( code.equals(suspect.getCode()) )
						itsMarker = suspect;
				}		
				searching = false;	
			}
			else if (code.compareTo((suspect).getCode()) < 0)
				last = middle;
			else
				first = middle;
		}
	}
	
	public String toString()
	{
		StringBuffer buf = new StringBuffer(1000);
		for(int i = 0; i < 256; i++)
		{
			buf.append(i).append(' ').append(itsPositions[i]);
			buf.append('\n');
		}
		return buf.toString();
	}
}
