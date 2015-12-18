package model;

import gui.*;

/**
 * This class computes together with MySwingWorker the next AI turn in a different thread so that the main thread can update the GUI
 */
public class AiWorker implements Runnable {
	Player player;
	GameJTable currentGameTable;
	GameGUI callerGUI;
	Player opponent;
	int lastPlayedAtRow;
	int lastPlayedAtColumn;
	//public void doAiTurn(GameJTable currentGameTable,int lastPlayedAtRow, int lastPlayedAtColumn, GameGUI callerGUI, Player opponent) {
	

	public AiWorker(Player player, GameJTable currentGameTable,int lastPlayedAtRow, int lastPlayedAtColumn, GameGUI callerGUI, Player opponent) {
		this.player = player;
		this.currentGameTable = currentGameTable;
		this.callerGUI=callerGUI;
		this.opponent=opponent;
		this.lastPlayedAtRow=lastPlayedAtRow;
		this.lastPlayedAtColumn=lastPlayedAtColumn;
	}

	@Override
	public void run() {
		callerGUI.lockUI();
		player.doAiTurn(currentGameTable,lastPlayedAtRow, lastPlayedAtColumn, callerGUI, opponent);
		callerGUI.unlockUI();
	}

}
