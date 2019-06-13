package gui;

import system.events.DisplayedDateChanged;
import system.events.InternalEvent;
import system.events.InternalEventListener;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

class EventsPane extends JTable implements InternalEventListener {

    EventsPaneDataModel model = new EventsPaneDataModel();

    static class ButtonRenderer extends JButton implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return (JButton)value;
        }
    }

    public EventsPane() {
        setModel(model);
        setDefaultRenderer(JButton.class, new ButtonRenderer());
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    @Override
    public void anEventOccurred(InternalEvent e) {
        if (e instanceof DisplayedDateChanged) {
            DisplayedDateChanged ddc = (DisplayedDateChanged)e;
            model.refreshEvents(ddc.getEvents());
            this.resizeAndRepaint();
        }
    }
}
