package Model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import GUI.WelcomeGUI;

public abstract class MyActionListener implements ActionListener {

	protected WindowGUI caller;
	
	public MyActionListener() {
		super();
	}
	
	public MyActionListener(WindowGUI caller) {
		this();
		this.caller=caller;
	}

}
