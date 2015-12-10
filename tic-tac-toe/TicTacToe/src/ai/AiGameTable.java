package ai;

import java.util.Vector;

import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import gui.GameJTable;

public class AiGameTable extends GameJTable {

	/**
	 * extends the GameJTable and adds a score for the AI if this game state is reached
	 */
	private static final long serialVersionUID = 4608996746261815314L;

	public int scoreIfStateIsReached;

}
