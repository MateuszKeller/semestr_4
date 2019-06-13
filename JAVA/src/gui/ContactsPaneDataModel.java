package gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import dane.Contact;

public class ContactsPaneDataModel extends AbstractTableModel {
	
	private List<Contact> contacts = new ArrayList<Contact>();
	
	public void refreshContacts(List<Contact> cont) {
		contacts = cont;
	}
	
	private static String[] columns = new String[] {"name", "company", "phone number", "e-mail"};

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

}
