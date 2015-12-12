/**
 * Used when an action handler handled by an ActionListener needs to access methods or attributes of its caller
 */
package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.WelcomeGUI;

public abstract class MyActionListener<CallerClass> implements ActionListener {

	protected CallerClass caller;
	
	public MyActionListener() {
		super();
	}
	
	public MyActionListener(CallerClass caller) {
		this();
		this.caller=caller;
	}

}
