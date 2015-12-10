/**
 * extends the GameJTable and adds a score for the AI if this game state is reached
 */
package model;

import gui.GameJTable;

public class AiGameTable extends GameJTable {
	private static final long serialVersionUID = 4608996746261815314L;

	public int scoreIfStateIsReached;
	public int playedAtRow;
	public int playedAtColumn;

	@Override
	public AiGameTable clone() {
		return (AiGameTable) super.clone();
	}

	/**
	 * negate the scoreIfStateIsReached value
	 * 
	 * @return the negated scoreIfStateIsReached.
	 */
	public int invertScoreIfStateIsReached() {
		scoreIfStateIsReached = -scoreIfStateIsReached;
		return scoreIfStateIsReached;
	}

	@Override
	public void setPlayerAt(int row, int column, Player player) {
		super.setPlayerAt(row, column, player);
		playedAtRow = row;
		playedAtColumn = column;
	}
}
