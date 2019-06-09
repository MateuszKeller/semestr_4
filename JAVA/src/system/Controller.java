package system;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import dane.Event;
import system.events.InternalEvent;
import system.events.InternalEventListener;

public class Controller {
	
	private List <DisplayedDateChangeListener> listeners = new ArrayList<>();
	private Map<Class<? extends InternalEvent>, List<InternalEventListener>> listenersMap = new HashMap<>();
	
	public void registerListener(Class<? extends InternalEvent> eventType, InternalEventListener listener) {
		List<InternalEventListener> listenersForEvent = listenersMap.get(eventType); 
		if(listenersForEvent == null) {
			listenersForEvent = new ArrayList<>();
			listenersForEvent.add(listener);
			listenersMap.put(eventType, listenersForEvent);
		}
		else listenersForEvent.add(listener);
	}
	
	public void notifyListeners(InternalEvent e) {
		List<InternalEventListener> listenersForEvent = listenersMap.get(e.getClass()); 
		if(listenersForEvent != null) {
			for (int i = 0; i < listenersForEvent.size(); i++){
				listenersForEvent.get(i).anEventOccurred(e);
			}
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
