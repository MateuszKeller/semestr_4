package system;

import java.awt.EventQueue;

import gui.Application;
import system.Manager;

public class Main {

	static Manager manager;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
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


