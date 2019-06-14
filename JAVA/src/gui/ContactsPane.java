package gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import dane.Contact;
import system.events.DisplayedContactsChanged;
import system.events.DisplayedDateChanged;
import system.events.InternalEvent;
import system.events.InternalEventListener;

public class ContactsPane extends JTable implements InternalEventListener{

	private final ContactsRemover remover;
	private final ContactsPaneDataModel model = new ContactsPaneDataModel();
	
	private class ButtonClicker extends MouseAdapter{
		@Override
		public void mouseClicked (MouseEvent e) {
			if(e.getComponent() instanceof ContactsPane) {
				System.out.println("mouse");
				ContactsPane pane = (ContactsPane) e.getComponent();
				int col = pane.getColumnModel().getColumnIndexAtX(e.getX());
				int row = e.getY()/pane.getRowHeight();
				
				if(row >= 0 && row < pane.getRowCount() && col >= 0 && col<pane.getColumnCount()) {
					Object valueAt = pane.getValueAt(row, col);
					if(valueAt instanceof JLabel) {
						remover.removeContact(model.removeContactAtRow(row));
					}
				}
			}
		}
	}
	
	public ContactsPane(ContactsRemover remover) {
		this.remover = remover;
		setModel(model);
		setDefaultRenderer(JLabel.class, (table, value, isSelected, hasFocus, row, column) -> (JLabel)value);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		addMouseListener(new ButtonClicker());
		setRowHeight(30);
		for(int col = 0; col < getColumnCount(); col++) {
			if(col == 1) {
				getColumnModel().getColumn(col).setPreferredWidth(200);
			}else {
				getColumnModel().getColumn(col).setPreferredWidth(50);
			}
		}
	}
	
	
	@Override
	public void anEventOccurred(InternalEvent e) {
		if (e instanceof DisplayedContactsChanged) {
            DisplayedContactsChanged ddc = (DisplayedContactsChanged)e;
            model.setContacts(ddc.getContacts());
            this.resizeAndRepaint();
        }	
	}

	public interface ContactsRemover{
		void removeContact(Contact con);
	}
	
}
