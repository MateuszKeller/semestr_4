package system;

import java.awt.EventQueue;

import gui.Application;
import system.Meneger;

public class Main {

	static Meneger manager;
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


