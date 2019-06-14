package system;

import java.awt.EventQueue;

import dane.AppParameters;
import gui.Application;
import system.Manager;

public class Main {

	static Manager manager;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AppParameters.initialize(args);
		manager = new Manager();
		//manager.testMain(args);
		
		manager.importFromDatabase("Kontakty");
		manager.importFromDatabase("Wydarzenia");
//		manager.exportEventsToXml("backup.xml");
		
//		System.out.println(manager.getKontakty());
		System.out.println(manager.getEventy());
		
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


