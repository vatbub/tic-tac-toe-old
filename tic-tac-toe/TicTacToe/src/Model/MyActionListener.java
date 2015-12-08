package Model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import GUI.WelcomeGUI;

public abstract class MyActionListener implements ActionListener {

	protected WelcomeGUI caller;
	
	public MyActionListener() {
		super();
	}
	
	public MyActionListener(WelcomeGUI caller) {
		this();
		this.caller=caller;
	}

}
