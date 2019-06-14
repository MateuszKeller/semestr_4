package system.events;

import java.util.List;

import dane.Contact;

public class DisplayedContactsChanged implements InternalEvent{
	
	private List<Contact> contacts;
	
	public DisplayedContactsChanged(List<Contact> contacts) {
		super();
		this.contacts = contacts;
	}
	
	public List<Contact> getContacts(){
		return contacts;
	}
	
	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

}
