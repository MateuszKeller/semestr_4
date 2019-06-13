package system;
//TODO zrobiæ nowy internalEvent - dodanie eventu przez dialog, notify kalendarz i listê zdarzeñ
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import dane.Contact;
import dane.Event;
import system.events.DisplayedDateChanged;
import system.events.InternalEvent;
import system.events.InternalEventListener;

public class Controller {
	
	private List <DisplayedDateChangeListener> listeners = new ArrayList<>();
	private Map<Class<? extends InternalEvent>, List<InternalEventListener>> listenersMap = new HashMap<>();
	private Manager manager = Controller.createManager();
	
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
	
	public void showMessage(String title, String content){
		JOptionPane.showMessageDialog(null, content, title, JOptionPane.INFORMATION_MESSAGE);
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
		showMessage("from Database", "coming soon");
	}
	public void showToXMLWindow(){
		showMessage("to XML", "coming soon");
	}
	public void showToOutlookWindow(){
		showMessage("to Outlook", "coming soon");
	}
	public void showToDatabaseWindow(){
		showMessage("to Database", "coming soon");
	}
	public void showAboutProgramWindow(){
		showMessage("About Program","Projekt zaliczeniowy: \nPrzedmiot: programowanie komponentowe\nAutorzy:\nMarta Bielecka\nMateusz Keller");
	}

	public void changeDisplayedDate(int selectedMonth, int selectedYear) {
		LocalDate now = LocalDate.now();
		LocalDate newDate = LocalDate.of(2018 +selectedYear, selectedMonth+1, 1);
		ArrayList<Event> events = manager.getEventsInMonth(2018 + selectedYear, selectedMonth + 1);
		
		notifyListeners(new DisplayedDateChanged(newDate, events));
		
	}
	
	public static Manager createManager() {
		Manager manager = new Manager();
		manager.addEvent("example event 1", LocalDateTime.of(2019, 6, 20 , 8, 0), LocalDateTime.of(2019, 6, 21, 9, 0), "ex 1", "Polibuda", null, null);
		manager.addEvent("example event 2", LocalDateTime.of(2019, 6, 21, 8, 0), LocalDateTime.of(2019, 6, 21, 12, 0), "ex 2", "domek", null, null);
		manager.addEvent("example event 3", LocalDateTime.of(2019, 6, 21, 11, 0), LocalDateTime.of(2019, 7, 10, 9, 0), "ex 3", "Ciechocinek", null, null);
		
		return manager;
	}
	
	public void addEvent(Event e) {
		manager.addEvent(e);
	}
	
	public void addContact(Contact c) {
		manager.addContact(c);
	}
	
	
}
