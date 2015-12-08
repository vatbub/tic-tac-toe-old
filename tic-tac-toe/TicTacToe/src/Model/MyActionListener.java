package Model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import GUI.WelcomeGUI;

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
