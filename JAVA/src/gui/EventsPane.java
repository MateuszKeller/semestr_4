package gui;

import dane.Event;
import system.events.DisplayedEventsChanged;
import system.events.InternalEvent;
import system.events.InternalEventListener;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class EventsPane extends JTable implements InternalEventListener {

    private final EventRemover remover;
    private final EventReplacer editor;
    private final EventsPaneDataModel model = new EventsPaneDataModel();

    private class ButtonClicker extends MouseAdapter{
        @Override
        public void mouseClicked (MouseEvent e) {
        	if (e.getComponent() instanceof EventsPane) {
        		EventsPane evPane = (EventsPane)e.getComponent();
        		int col = evPane.getColumnModel().getColumnIndexAtX(e.getX());
        		int row = e.getY() / evPane.getRowHeight();
        		
        		if(row >= 0 && row < evPane.getRowCount() && col >= 0 && col < evPane.getColumnCount()) {
        			Object valueAt = evPane.getValueAt(row, col);
        			if (valueAt instanceof JLabel) {
        			    if (col == 5) {
                            remover.removeEvent(model.getEventAtRow(row));
                        } else {
        			        editor.replaceEvent(model.getEventAtRow(row));
                        }
        			}
        		}
        	}
        }
    }

    public EventsPane(EventRemover remover, EventReplacer editor) {
        this.remover = remover;
        this.editor = editor;
        setModel(model);
        setDefaultRenderer(JLabel.class, (table, value, isSelected, hasFocus, row, column) -> (JLabel) value);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        addMouseListener(new ButtonClicker());
        setRowHeight(30);
        for (int col = 0; col < getColumnCount(); col++) {
            if (col == 1) {
                getColumnModel().getColumn(col).setPreferredWidth(200);
            } else {
                getColumnModel().getColumn(col).setPreferredWidth(50);
            }
        }
//        TableRowSorter<EventsPaneDataModel> sorter = new TableRowSorter<>(model);
//        setRowSorter(sorter);
//        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
//        sortKeys.add(new RowSorter.SortKey(2, SortOrder.ASCENDING));
//        sorter.setSortKeys(sortKeys);
//        sorter.setSortable(4, false);
//        sorter.setSortable(5, false);
//        sorter.sort();
    }

    @Override
    public void anEventOccurred(InternalEvent e) {
        if (e instanceof DisplayedEventsChanged) {
            DisplayedEventsChanged dec = (DisplayedEventsChanged)e;
            model.setEvents(dec.getEvents());
            this.resizeAndRepaint();
        }
    }
    
    public interface EventRemover {
    	void removeEvent(dane.Event event);
    }

    public interface EventReplacer {
        void replaceEvent(Event eventAtRow);
    }
}
