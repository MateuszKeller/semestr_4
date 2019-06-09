package gui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import dane.Event;

public class CalendarDataModel extends AbstractTableModel {
	
	private LocalDate dateToDisplay = LocalDate.now(); 
	private List<Event> eventsToDisplay = new ArrayList<Event>();

	public void setDataToDisplay(LocalDate dateToDisplay, List<Event> events) {
		this.dateToDisplay = dateToDisplay;
		this.eventsToDisplay = events;
	}

	private static final long serialVersionUID = -7100146141891377597L;
	
	private static String[] weekdays = new String[]{
			"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
			};
	
	@Override
	public Class<?> getColumnClass(int arg0) {
		return String.class;
	}

	@Override
	public int getColumnCount() {
		return 7;
	}

	@Override
	public String getColumnName(int i) {
		return weekdays[i];
	}

	@Override
	public int getRowCount() {
		return 6;
	}

	@Override
	public Object getValueAt(int row, int col) {
		int firstDay = dateToDisplay.minusDays(dateToDisplay.getDayOfMonth()).getDayOfWeek().getValue()-1;
		int cellNumber = row*7 + col;
		int daysInMonth = dateToDisplay.lengthOfMonth();
		String empty = "-";
		int currentCell = cellNumber - firstDay;
		if(currentCell < 1 || currentCell > daysInMonth)
			return empty;
		else
		return String.valueOf(currentCell);
	}

}
