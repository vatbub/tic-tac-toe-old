/**
 * This class computes together with MySwingWorker the next AI turn in a different thread so that the main thread can update the GUI
 */

package model;

import gui.*;

public class AiWorker implements Runnable {
	Player player;
	GameJTable currentGameTable;
	GameGUI callerGUI;
	Player opponent;

	public AiWorker(Player player, GameJTable currentGameTable, GameGUI callerGUI, Player opponent) {
		this.player = player;
		this.currentGameTable = currentGameTable;
		this.callerGUI=callerGUI;
		this.opponent=opponent;
	}

	@Override
	public void run() {
		callerGUI.lockUI();
		player.doAiTurn(currentGameTable, callerGUI, opponent);
		callerGUI.unlockUI();
	}

}
