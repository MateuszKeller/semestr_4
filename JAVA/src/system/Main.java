package system;

import java.awt.EventQueue;

import dane.AppParameters;
import gui.Application;
import system.Manager;

public class Main {

	public static void main(String[] args) {
		
		AppParameters.initialize(args);
		view();
	}
	
	static void view()
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Application window = new Application();
					window.getFrame().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}


