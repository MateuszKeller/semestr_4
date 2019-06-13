package system;

import java.awt.EventQueue;

import dane.AppParameters;
import gui.Application;
import system.Manager;

public class Main {

	static Manager manager;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AppParameters.getInstance(args[0]);
		manager = new Manager();
//		manager.testMain(args);
		
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


