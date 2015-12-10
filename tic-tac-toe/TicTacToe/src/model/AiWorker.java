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
