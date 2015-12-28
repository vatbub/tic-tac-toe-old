package model;

import javax.swing.SwingWorker;

import gui.*;

/**
 * This class computes together with AiWorker the next AI turn in a different thread so that the main thread can update the GUI
 */
public class MySwingWorker extends SwingWorker<Void,Void> {

	Player player;
	GameJTable currentGameTable;
	GameGUI callerGUI;
	Player opponent;
	
	public MySwingWorker(Player player, GameJTable currentGameTable, GameGUI callerGUI, Player opponent){
		this.player=player;
		this.currentGameTable=currentGameTable;
		this.callerGUI=callerGUI;
		this.opponent=opponent;
	}
	
	@Override
    public Void doInBackground() {
        //Thread t = new Thread(new AiWorker(player, currentGameTable, callerGUI, opponent));
		//public AiWorker(Player player, GameJTable currentGameTable,int lastPlayedAtRow, int lastPlayedAtColumn, GameGUI callerGUI, Player opponent) {
		Thread t = new Thread(new AiWorker(player, currentGameTable, callerGUI, opponent));
        t.start();
		return null;
    }

    @Override
    public void done() {
		
    }

}
