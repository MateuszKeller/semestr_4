package system;
import java.io.File;
//TODO zrobiæ nowy internalEvent - dodanie eventu przez dialog, notify kalendarz i listê zdarzeñ
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import com.sun.media.sound.ModelAbstractChannelMixer;

import dane.Contact;
import dane.Event;
import system.events.DisplayedContactsChanged;
import system.events.DisplayedDateChanged;
import system.events.DisplayedEventsChanged;
import system.events.InternalEvent;
import system.events.InternalEventListener;

public class Controller {
	
	private LocalDate displayedDate;
	private Map<Class<? extends InternalEvent>, List<InternalEventListener>> listenersMap = new HashMap<>();
	private Manager manager = Controller.createManager();
	
	public Controller() {
		LocalDate now = LocalDate.now();
		this.displayedDate = LocalDate.of(now.getYear(), now.getMonth(), 1);
	}
			
	public void initialize() {
		refreshEventData();
		refreshContactData();
		refreshDisplayedEvents(0);
	}
	
			
	private void refreshEventData() {
		notifyListeners(new DisplayedDateChanged(
				displayedDate, manager.getEventsInMonth(displayedDate)));
	}
	
	private void refreshDisplayedEvents(int option) {
		LocalDate date = LocalDate.now();
		if(option == 0) {
			notifyListeners(new DisplayedEventsChanged(manager.getAllEvents()));
			} else if(option == 1) {
				notifyListeners(new DisplayedEventsChanged(manager.getEventsInDay(date)));
			} else if(option == 2) {
				notifyListeners(new DisplayedEventsChanged(manager.getEventsInWeek(date)));
			} else if(option == 3) {
				notifyListeners(new DisplayedEventsChanged(manager.getEventsInMonth(date)));
			} else if(option == 4) {
				notifyListeners(new DisplayedEventsChanged(manager.getEventsInYear(date)));
			}
	}
	
	private void refreshContactData() {
		notifyListeners(new DisplayedContactsChanged(manager.getContacts()));
	}
	
	public void registerListener(Class<? extends InternalEvent> eventType, InternalEventListener listener) {
		List<InternalEventListener> listenersForEvent = listenersMap.get(eventType); 
		if(listenersForEvent == null) {
			listenersForEvent = new ArrayList<>();
			listenersForEvent.add(listener);
			listenersMap.put(eventType, listenersForEvent);
		}
		else listenersForEvent.add(listener);
	}
	
	private void notifyListeners(InternalEvent e) {
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
	public void importEventsFromXml(File file){
        manager.importFromXML(file);
        refreshEventData();
	}
	public void showFromOutlookWindow(){
		JOptionPane.showMessageDialog(null, "coming soon", "from XML", JOptionPane.INFORMATION_MESSAGE);
	}
	public void importDataFromDatabase(String db){
		manager.importFromDatabase(db);
		refreshEventData();
		refreshContactData();
	}

	public void exportEventsToXml(File file) {
		manager.exportEventsToXml(file);
	}

	public void showToOutlookWindow(){
		showMessage("to Outlook", "coming soon");
	}

	public void exportDataToDatabase(String db){
		manager.exportToDatabase(db);
	}

	public void showAboutProgramWindow(){
		showMessage("About Program","Projekt zaliczeniowy: \nPrzedmiot: programowanie komponentowe\nAutorzy:\nMarta Bielecka\nMateusz Keller");
	}

	public void changeDisplayedDate(int selectedMonth, int selectedYear) {
		displayedDate = LocalDate.of(2018 +selectedYear, selectedMonth+1, 1);		
		List<Event> events = manager.getEventsInMonth(2018 + selectedYear, selectedMonth + 1);
		notifyListeners(new DisplayedDateChanged(displayedDate, events));		 		
	}
	
	public void changeDisplayedEvents(int option) {
		refreshDisplayedEvents(option);
	}
	
	public static Manager createManager() {
		Manager manager = new Manager();
		
		return manager;
	}
	
	public void addEvent(Event e) {
		manager.addEvent(e);
		refreshEventData();
	}
	
	public void removeOldEvents(LocalDateTime dueTime) {
		manager.removeOldEvents(dueTime);
		refreshEventData();
	}
	
	public void addContact(Contact c) {
		manager.addContact(c);
		refreshContactData();
	}
	
	public void removeContact(Contact c) {
		manager.removeContact(c);
		refreshContactData();
	}
	
	public void removeEvent(Event e) {
		manager.deleteEvent(e);
		refreshEventData();
	}	
}
