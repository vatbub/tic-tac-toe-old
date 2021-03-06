package model;

import java.awt.event.WindowAdapter;

import gui.GameGUI;

/**
 * Used when an action handler handled by a WindowAdapter needs to access methods or attributes of its caller
 */
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
