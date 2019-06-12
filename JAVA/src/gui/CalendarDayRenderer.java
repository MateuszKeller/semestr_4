package gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import dane.Event;

public class CalendarDayRenderer extends JLabel implements TableCellRenderer{

	public CalendarDayRenderer() {
		setBackground(null);
		setBorder(null);
		setFocusable(false);
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		if(value instanceof CalendarDay) {
			CalendarDay day = (CalendarDay) value;
			
			StringBuilder text = new StringBuilder(day.getDayOfMonth());
			
			if(day.getEvents() != null && !day.isEmpty()) {
				setOpaque(true);
				setBackground(Color.ORANGE);
				for(Event e : day.getEvents()) {
					text.append(e.getTittle());
				}
			} else {
				setOpaque(false);
			}
			setText(text.toString());
			return this; 
		}else {
			throw new RuntimeException(" value must be CalendarDay Object");
		}
	}
	

}
