package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;

import dane.Contact;

public class ContactsPaneDataModel extends AbstractTableModel {
	
	private List<Contact> contacts = new ArrayList<Contact>();
	private static String[] columns = new String[] {"Name", "Company", "Phone number", "E-mail", "Delete"};
	
	public void refreshContacts(List<Contact> cont) {
		contacts = cont;
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex == 4;
	}
	
	@Override
	public int getRowCount() {
		return contacts.size();
	}
	
	@Override
	public String getColumnName(int column) {
		return columns[column];
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public Class<?> getColumnClass(int col){
		if (col<4) {
			return String.class;
		} else {
			return JButton.class;
		}
	}
	
	static class DeletingButton extends JButton{
		private final Contact contact;
		
		public DeletingButton (Contact c) {
			super("Delete");
			this.contact = c;
			this.addActionListener (new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("Deleting contact: "+ contact.getName());
					
				}
			});
		}
	}
	
	private JButton createDeletingButton() {
		return new JButton() {
			
		};
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Contact c = contacts.get(rowIndex);
		switch (columnIndex) {
		case 0: return c.getName();
		case 1: return c.getCompany();
		case 2: return c.getPhone();
		case 3: return c.getEmail();
		case 4: return new DeletingButton(c);
		}
		return null;
	}

}
