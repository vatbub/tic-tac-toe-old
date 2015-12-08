package Model;

import java.awt.event.WindowAdapter;

import GUI.GameGUI;

public class MyWindowAdapter extends WindowAdapter {

	protected GameGUI caller;
	
	public MyWindowAdapter() {
		super();
	}
	
	public MyWindowAdapter(GameGUI caller) {
		this();
		this.caller=caller;
	}

}
