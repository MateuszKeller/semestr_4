package system;

import javax.swing.JOptionPane;

public class Controller {
	public void showMessage(String s){
		JOptionPane.showMessageDialog(null, "coming soon", s, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void showSettingsWindow(){
		JOptionPane.showMessageDialog(null, "ustawienia", "Ustawienia", JOptionPane.INFORMATION_MESSAGE);
	}
	public void showFromXMLWindow(){
		JOptionPane.showMessageDialog(null, "coming soon", "from XML", JOptionPane.INFORMATION_MESSAGE);
	}
	public void showFromOutlookWindow(){
		JOptionPane.showMessageDialog(null, "coming soon", "from XML", JOptionPane.INFORMATION_MESSAGE);
	}
	public void showFromDatabaseWindow(){
		showMessage("from Database");
	}
	public void showToXMLWindow(){
		showMessage("to XML");
	}
	public void showToOutlookWindow(){
		showMessage("to Outlook");
	}
	public void showToDatabaseWindow(){
		showMessage("to Database");
	}
	public void showAboutProgramWindow(){
		showMessage("about Program");
	}
}
