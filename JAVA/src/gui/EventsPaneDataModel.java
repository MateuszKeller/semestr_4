package gui;

import dane.Contact;
import dane.Event;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class EventsPaneDataModel extends AbstractTableModel {

    private List<Event> events = new ArrayList<>();
//    private List<Contact> contacts = new ArrayList<>();

//    public void refreshEvents(List<Event> evs) {
    public void setEvents(List<Event> evs) {
        events = evs;
    }
    
//    public void setContacts (List<Contact> con) {
//    	contacts = con;
//    }
    
    public Event removeEventAtRow(int row) {
    	return events.remove(row);
    }

    private static String[] columns = new String[] {
//            "Title", "Description", "Start", "End", "Delete"
            "Title", "Description", "Start", "End", ""
    };

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
//        return columnIndex == 4;
    	return false;
    }

    @Override
    public int getRowCount() {
        return events.size();
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
    public Class<?> getColumnClass(int col) {
        if(col < 4) {
            return String.class;
        } else {
//            return JButton.class;
        	return JLabel.class;
        }
    }

//    static class DeletingButton extends JButton {
//        private final Event event;
//
//        public DeletingButton(Event e) {
//            super("Delete");
//            this.event = e;
//            this.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    System.out.println("Deleting event: " + event.getTittle());
//                }
//            });
//        }
//    }
//
//    private JButton createDeletingButton() {
//        return new JButton() {
//
//        };
//    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Event ev = events.get(rowIndex);
        switch (columnIndex) {
            case 0: return ev.getTittle();
            case 1: return ev.getNote();
            case 2: return ev.getStart().toString();
            case 3: return ev.getEnd().toString();
            case 4: JLabel deleter = new JLabel("Delete");
            		deleter.setHorizontalAlignment(JLabel.CENTER);
            		deleter.setOpaque(true);
            		deleter.setBackground(Color.GRAY);
            		deleter.setForeground(Color.WHITE);
            		return deleter;
            default:
            	return null;
        }
    }
}
