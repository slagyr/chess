package chess.player;

import chess.scoring.Scorer;
import chess.board.*;
import chess.move.*;
import chess.piece.Piece;

import java.util.*;

public class MoveFinder
{
	private Scorer itsScorer;
	private HashSet itsMoves;
	private int itsSearchQuality;
	private PositionHashTable itsTable;
	
	private Player itsPlayer;
	private ChessBoard itsBoard;

	public MoveFinder(Scorer scorer, int searchQuality)
	{
		itsScorer = scorer;
		itsMoves = new HashSet();
		itsSearchQuality = searchQuality;
	}
	
	public Scorer getScorer()
	{
		return itsScorer;
	}
	
	public int getSearchQuality()
	{
		return itsSearchQuality;
	}
	
	public void search(ChessBoard board, Player player)
	{
		itsTable = new PositionHashTable();
		itsMoves.clear();
		itsPlayer = player;
		itsBoard = board;
		Collection moves = getAllMoves(player);
		for(Iterator i = moves.iterator(); i.hasNext(); )
		{
			AbstractMove move = (AbstractMove)i.next();
			move.getPiece().findMoves_safe();
			move = player.movePiece(move);
			itsBoard.recordPosition();
			String positionCode = itsBoard.getPositionCode() + player.getColor();
			int score = examineMove(move, player, itsSearchQuality, positionCode);
			move.setScore(score);
			itsMoves.add(move);
			itsBoard.removeLastPosition();
			player.reverseMove(move);
		}
	}
	
	public Collection getBestMoves()
	{
		AbstractMove high = findHighestScoringMove();
		return getMovesOfValue(high.getScore());
	}
	
	public AbstractMove getBestMove()
	{
		Collection moves = getBestMoves();
		Random generator = new Random(System.currentTimeMillis());
		int random = 0;
		if(moves.size() > 0)
			random = generator.nextInt(moves.size());
		Iterator moveIterator = moves.iterator();
		for(int i = 0; i < random; i++)
			moveIterator.next();
		return (AbstractMove)moveIterator.next();
	}
	
	private int examineMove(AbstractMove theMove, Player player, int depth, String position)
	{
		Player player2 = player.getOpponent();
		player2.findAllMoves(theMove.isCheck());
		int endPositionScore = getScoreForEndPosition(player2);
		if (endPositionScore != -1)
		{
			itsTable.add(position, endPositionScore);
			return endPositionScore;
		}
		else if (depth == 1)
		{
			int score = itsScorer.getScore(itsBoard, itsPlayer);
			itsTable.add(position, score);
			return score;
		}
		else
		{
			int bestScore = 1234;
			Collection moves = getAllMoves(player2);
			for(Iterator i = moves.iterator(); i.hasNext(); )
			{
				AbstractMove move = (AbstractMove)i.next();
				move.getPiece().findMoves_safe();
				move = player2.movePiece(move);
				String newPosition = itsBoard.getPositionCode() + player2.getColor();
				int thisScore = -321;
				boolean hasIt = itsTable.has(newPosition);
				if (hasIt)
				{
					thisScore = itsTable.getScore();
				}
				else
				{
					thisScore = examineMove(move, player2, depth - 1, newPosition);
					itsTable.add(newPosition, thisScore);
				}
				bestScore = compareScores(player2, bestScore, thisScore);
				player2.reverseMove(move);
			}
			return bestScore;
		}	
	}
	
	private int compareScores(Player player, int bestScore, int score)
	{
		if(bestScore == 1234)
			return score;
		if(player == itsPlayer)
			return (bestScore > score) ? bestScore : score;
		else
			return (bestScore < score) ? bestScore : score;	
	}
	
	private int getScoreForEndPosition(Player player)
	{
		int returnValue = -1;
		if(player.isInStaleMate() || itsBoard.isThirdOccuranceOfPosition())
			returnValue = 500;
		else if (player.isInCheckMate())
		{
			if(player == itsPlayer)
				returnValue = 0;
			else
				returnValue = 1000;	
		}	
		return returnValue;
	}
	
	private Collection getAllMoves(Player player)
	{				  
		Collection moves = new HashSet();
		for(Iterator p = player.getPieces().iterator(); p.hasNext();)
		{
			Piece piece = (Piece)p.next();
			SquareInterface startSquare = piece.getLocation();
			for(Iterator s = piece.getMoveOptions().iterator(); s.hasNext();)
			{
				SquareInterface square = (SquareInterface)s.next();
				moves.add(new Move(piece, startSquare, square));
			}
		}
		return moves;
	}
	
	private AbstractMove findHighestScoringMove()
	{
		AbstractMove bestMove = new IllegalMove();
		for(Iterator i = itsMoves.iterator(); i.hasNext();)
		{
			AbstractMove move = (AbstractMove)i.next();
			if (move.getScore() > bestMove.getScore())
				bestMove = move;
		}
		return bestMove;
	}
	
	private Collection getMovesOfValue(int target)
	{
		Collection moves = new HashSet();
		for(Iterator i = itsMoves.iterator(); i.hasNext();)
		{
			AbstractMove move = (AbstractMove)i.next();
			if (move.getScore() == target)
				moves.add(move);
		}
		return moves;
	}
}
