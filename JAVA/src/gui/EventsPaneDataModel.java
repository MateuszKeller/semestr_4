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

    public void setEvents(List<Event> evs) {
        events = evs;
    }
    
    public Event getEventAtRow(int row) {
    	return events.get(row);
    }

    private static String[] columns = new String[] {
            "Title", "Description", "Start", "End", "", ""
    };

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
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
        	return JLabel.class;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Event ev = events.get(rowIndex);
        switch (columnIndex) {
            case 0: return ev.getTittle();
            case 1: return ev.getNote();
            case 2: return ev.getStart().toString();
            case 3: return ev.getEnd().toString();
            case 4: JLabel editor = new JLabel("Edit");
                    editor.setHorizontalAlignment(JLabel.CENTER);
                    editor.setOpaque(true);
                    editor.setBackground(Color.ORANGE);
                    editor.setForeground(Color.BLACK);
                    return editor;
            case 5: JLabel deleter = new JLabel("Delete");
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
