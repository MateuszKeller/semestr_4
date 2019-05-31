package system;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JOptionPane;

public class Controller {
	
	private List <DisplayedDateChangeListener> listeners = new ArrayList<>();
	
	public void addListener(DisplayedDateChangeListener l) {
		listeners.add(l);
	}
	
	public void removeListener(DisplayedDateChangeListener l) {
		listeners.remove(l);
	}
	
	public void notifyListeners(LocalDate newDate) {
		for(int i = 0; i < listeners.size(); i++) {
			listeners.get(i).dateChange(newDate);
		}
	}
	
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
