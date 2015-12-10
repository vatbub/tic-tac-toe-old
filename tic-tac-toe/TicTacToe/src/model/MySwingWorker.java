package model;

import java.awt.List;

import javax.swing.SwingWorker;

import gui.*;

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
        Thread t = new Thread(new AiWorker(player, currentGameTable, callerGUI, opponent));  
        t.start();
		return null;
    }

    @Override
    public void done() {
		
    }

}
