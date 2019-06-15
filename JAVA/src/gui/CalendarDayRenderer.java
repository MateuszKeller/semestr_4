package gui;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import dane.Event;

public class CalendarDayRenderer extends JList<String> implements TableCellRenderer{

	public CalendarDayRenderer() {
		setBackground(null);
		setBorder(null);
		setFocusable(false);
	}
	
	@Override
	public Component getTableCellRendererComponent(
			JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		if(value instanceof CalendarDay) {
			CalendarDay day = (CalendarDay) value;
			
			List<String> text = new ArrayList<>();
			text.add(day.getDayOfMonth());

			if(day.getEvents() != null && !day.isEmpty()) {
				setOpaque(true);
				setBackground(Color.ORANGE);
				for(Event e : day.getEvents()) {
					text.add(e.getTittle());
				}
			} else {
				setOpaque(false);
				setBackground(Color.WHITE);
			}
			setListData(text.toArray(new String[0]));
			return this;
		}else {
			throw new RuntimeException(" value must be CalendarDay Object");
		}
	}
}
