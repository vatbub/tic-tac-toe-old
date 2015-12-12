/**
 * Used when an action handler handled ba a WindowAdapter needs to access methods or attributes of its caller
 */

package model;

import java.awt.event.WindowAdapter;

import gui.GameGUI;

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
